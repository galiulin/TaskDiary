import org.junit.jupiter.api.Disabled;
import connection.*;
import org.junit.jupiter.api.Test;
import pojo.Role;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.function.Function;

class ConnectionDBTest {

    @Test
    public void setup() throws NoSuchFieldException, SQLException {
        ConnectionDB oldConnectionDB = ConnectionDBImpl.getInstance();

        Field field = oldConnectionDB.getClass().getDeclaredField("INSTANCE");

        ConnectionDB newConnectionDB = new ConnectionDB() {
            @Override
            public Connection getConnect() {
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdbtaskdairy",
                            "admin",
                            "admin");
                    System.out.println("получен подставной connection");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return connection;
            }

            @Override
            public <T> T getFromDB(Function<Connection, T> function) {
                return function.apply(getConnect());
            }
        };

        Connection connection = newConnectionDB.getConnect();
        Statement statement = connection.createStatement();

        //создаем таблицу с ролями
        statement.addBatch("CREATE TABLE IF NOT EXISTS role_t " +
                "(role varchar(15) not null CONSTRAINT role_t_pkey PRIMARY KEY );");

        statement.addBatch("CREATE UNIQUE INDEX IF NOT EXISTS role_t_role_uindex " +
                "ON role_t (role);");

        //заполняем таблицу ролями
        for (Role role : Role.values()){
            statement.addBatch("INSERT INTO role_t (role) VALUES (\'" + role.name() +"\');");
        }

        statement.addBatch("");
        statement.executeBatch();

        setFinalStatic(field, newConnectionDB);
        System.out.println("конец тестового метода");
    }

    /**
     * Получаем доступ к приватному final полю и устанавливаем собственный объект
     * */
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

    @Test
    @Disabled("the test does not correspond to the canons")
    void createTable() {

        ConnectionDB connectionDB = ConnectionDBImpl.getInstance();
        connectionDB.getFromDB((connection -> {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("SELECT * FROM task");
                while (set.next()){
                    System.out.println(set.getInt("id"));
                    System.out.println(set.getString("condition"));
                    System.out.println(set.getString("description"));
                    System.out.println(set.getLong("date_add"));
                    Timestamp timestamp = Timestamp.valueOf(set.getString("date_add"));
                    System.out.println(timestamp);
                    System.out.println(set.getInt("user_id"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }));
    }
}