package connection;

import dao.UserDAO;
import dao.UserDAOImpl;
import org.apache.log4j.Logger;
import pojo.User;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public class ConnectionDBImpl implements ConnectionDB {
    private static final ConnectionDB INSTANCE = new ConnectionDBImpl();
    private static final Integer PORT = 5432;
    private static final String DB = "taskdairy";
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:%d/%s";
    private static String user = "admin";
    private static String pass = "admin";

    private static Logger logger = Logger.getLogger(ConnectionDBImpl.class);

    private ConnectionDBImpl() {
    }

    public static ConnectionDB getInstance(){
        return INSTANCE;
    }

    private static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    String.format(CONNECTION_URL, PORT, DB),
                    user,
                    pass);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public Connection getConnect(){
        return getConnection();
    }

    public <T> T getFromDB(FunctionSQL<Connection, T> function) throws SQLException {
        T obj = null;
        try (Connection connection = getConnection()){
            obj = function.apply(connection);
        } catch (SQLException e) {
//            e.printStackTrace();
            throw e;
        }
        return obj;
    }
}
