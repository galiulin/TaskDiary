package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public class ConnectionDBImpl implements ConnectionDB {
    private static final ConnectionDBImpl INSTANCE = new ConnectionDBImpl();
    private static final Integer PORT = 5432;
    private static final String DB = "taskdairy";
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:%d/%s";
    private static String user = "admin";
    private static String pass = "admin";


    private ConnectionDBImpl() {
    }

    public static ConnectionDBImpl getInstance(){
        return INSTANCE;
    }

    private static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    String.format(CONNECTION_URL, PORT, DB),
                    user,
                    pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public Connection getConnect(){
        return getConnection();
    }

    public <T> T getFromDB(Function<Connection, T> function) {
        T obj = null;
        try (Connection connection = getConnection()){
            obj = function.apply(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
