import connection.*;
import org.junit.jupiter.api.*;
import pojo.Condition;
import pojo.Role;

import java.lang.reflect.*;
import java.sql.*;
import java.util.Arrays;
import java.util.function.Function;

class ConnectionDBTest {
    private static ConnectionDB oldConnectionDB;
    private static ConnectionDB newConnectionDBProxy;
    private static ConnectionDB newConnectionDB;

    //    @Test
    public void setup() throws NoSuchFieldException, SQLException {
        if (oldConnectionDB == null) {
            oldConnectionDB = ConnectionDBImpl.getInstance();
        }

        newConnectionDB = new ConnectionDB() {
            @Override
            public Connection getConnect() {
                Connection connection = null;
                try {
                    Class.forName("org.h2.Driver");
                    connection = DriverManager.getConnection(
                            "jdbc:h2:~/test_db/test_db;MODE=PostgreSQL",
                            "sa",
                            "");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return connection;
            }

            @Override
            public <T> T getFromDB(Function<Connection, T> function) {
                return function.apply(getConnect());
            }
        };
        newConnectionDBProxy = (ConnectionDB) Proxy.newProxyInstance(
                oldConnectionDB.getClass().getClassLoader(),
                oldConnectionDB.getClass().getInterfaces(),
                (proxy, method, args) -> {
//                    Arrays.stream(Thread.currentThread().getStackTrace()).forEach(System.out::println);
                    return method.invoke(newConnectionDB, args);
                });

        replaceConnectionDB(oldConnectionDB, newConnectionDBProxy);

        createDB();
    }

    private void replaceConnectionDB(ConnectionDB oldConnectionDB, ConnectionDB newConnectionDB) throws NoSuchFieldException {
        Field field = oldConnectionDB.getClass().getDeclaredField("INSTANCE");
        setFinalStatic(field, newConnectionDB);
    }

    private void createDB() throws SQLException {
        Connection connection = ConnectionDBImpl.getInstance().getConnect();
        Statement statement = connection.createStatement();

        dropTableComment(statement);
        dropTableTask(statement);
        dropTableUser(statement);
        dropTableRole(statement);
        dropTableCondition(statement);

        createTableRole(statement);
        createTableCondition(statement);
        createTableUsers(statement);
        createTableTask(statement);
        createTableComment(statement);
    }

    private void dropTableComment(Statement statement) throws SQLException {
        statement.execute("DROP TABLE IF EXISTS public.comment;");
    }

    /**
     * создает таблицу с комментариями
     */
    private int[] createTableComment(Statement statement) throws SQLException {
        statement.addBatch(
                "CREATE TABLE IF NOT EXISTS comment( " +
                        "id SERIAL NOT NULL " +
                        "CONSTRAINT comment_pkey " +
                        "PRIMARY KEY, " +
                        "task_id INTEGER NOT NULL " +
                        "CONSTRAINT comment_task_id_fk " +
                        "REFERENCES task, " +
                        "user_id INTEGER NOT NULL " +
                        "CONSTRAINT comment_user_t_id_fk " +
                        "REFERENCES user_t, " +
                        "date TIMESTAMP NOT NULL, " +
                        "comment TEXT NOT NULL); "
        );

        statement.addBatch(
                "CREATE UNIQUE INDEX IF NOT EXISTS comment_id_uindex " +
                        " ON comment (id);"
        );
        return statement.executeBatch();
    }

    private void dropTableTask(Statement statement) throws SQLException {
        statement.execute("DROP TABLE IF EXISTS public.task;");
    }

    private int[] createTableTask(Statement statement) throws SQLException {
        statement.addBatch("CREATE TABLE IF NOT EXISTS " +
                "task ( " +
                "id SERIAL NOT NULL " +
                "CONSTRAINT task_pkey " +
                "PRIMARY KEY, " +
                "condition VARCHAR(15) NOT NULL " +
                "CONSTRAINT task_condition_condition_fk " +
                "REFERENCES condition, " +
                "description TEXT, " +
                "date_add TIMESTAMP NOT NULL, " +
                "dead_line TIMESTAMP NOT NULL, " +
                "user_id INTEGER " +
                "CONSTRAINT task_user_t_id_fk " +
                "REFERENCES user_t);");

        statement.addBatch(
                "CREATE UNIQUE INDEX IF NOT EXISTS " +
                        "task_id_uindex " +
                        "ON task (id);");

        return statement.executeBatch();
    }

    private boolean dropTableUser(Statement statement) throws SQLException {
        return statement.execute("DROP TABLE IF EXISTS public.user_t;");
    }

    /**
     * создаем таблицу пользователей
     */
    private int[] createTableUsers(Statement statement) throws SQLException {

        statement.addBatch(
                "CREATE TABLE IF NOT EXISTS user_t " +
                        "(id SERIAL NOT NULL " +
                        "CONSTRAINT user_t_pkey " +
                        "PRIMARY KEY, " +
                        "login VARCHAR(40) NOT NULL, " +
                        "first_name VARCHAR(40), " +
                        "password VARCHAR(200) NOT NULL, " +
                        "role VARCHAR(15) NOT NULL " +
                        "CONSTRAINT user_t_role_t_role_fk " +
                        "REFERENCES role_t); ");

        statement.addBatch("CREATE UNIQUE INDEX IF NOT EXISTS user_t_id_uindex ON user_t (id);");
        statement.addBatch("CREATE UNIQUE INDEX IF NOT EXISTS user_t_login_uindex ON user_t (login);");
        return statement.executeBatch();
    }

    /**
     * удаляем таблицу с ролями
     */
    private boolean dropTableRole(Statement statement) throws SQLException {
        return statement.execute(
                "DROP TABLE IF EXISTS public.role_t;"
        );
    }

    /**
     * создает таблицу с состояниями задач
     */
    private int[] createTableCondition(Statement statement) throws SQLException {
        statement.addBatch(
                "CREATE TABLE IF NOT EXISTS condition " +
                        "(condition VARCHAR(15) NOT NULL " +
                        "CONSTRAINT condition_pkey " +
                        "PRIMARY KEY);"
        );

        statement.addBatch(
                "CREATE UNIQUE INDEX IF NOT EXISTS " +
                        "condition_condition_uindex " +
                        "ON condition (condition);"
        );

        for (Condition condition : Condition.values()) {
            statement.addBatch(
                    "INSERT INTO condition (condition) " +
                            "VALUES (\'" + condition.name() + "\');"
            );
        }
        return statement.executeBatch();
    }

    /**
     * удаляем таблицу состояний задач
     */
    private boolean dropTableCondition(Statement statement) throws SQLException {
        return statement.execute(
                "DROP TABLE IF EXISTS public.condition;"
        );
    }

    /**
     * создает таблицу с ролями
     */
    private int[] createTableRole(Statement statement) throws SQLException {
        statement.addBatch(
                "CREATE TABLE IF NOT EXISTS role_t " +
                        "(role VARCHAR(15) NOT NULL " +
                        "CONSTRAINT role_t_pkey PRIMARY KEY );"
        );

        statement.addBatch(
                "CREATE UNIQUE INDEX IF NOT EXISTS " +
                        "role_t_role_uindex " +
                        "ON role_t (role);"
        );

        //заполняем таблицу ролями
        for (Role role : Role.values()) {
            statement.addBatch(
                    "INSERT INTO role_t (role) " +
                            "VALUES (\'" + role.name() + "\');"
            );
        }
        return statement.executeBatch();
    }

    /**
     * Получаем доступ к приватному final полю и устанавливаем собственный объект
     */
    public static void setFinalStatic(Field field, Object newValue) {
        try {
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, newValue);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * выключаем: дропаем все таблицы и восстанавливаем исходный объект
     */
    public void shutdown() {
        ConnectionDB connectionDB = ConnectionDBImpl.getInstance();
        try (Connection connection = connectionDB.getConnect()) {

            Statement statement = connection.createStatement();
            dropTableComment(statement);
            dropTableTask(statement);
            dropTableUser(statement);
            dropTableRole(statement);
            dropTableCondition(statement);
            replaceConnectionDB(oldConnectionDB, oldConnectionDB);
            newConnectionDBProxy = null;
            newConnectionDB = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
}