package connection;

import dao.UserDAO;
import dao.UserDAOImpl;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import pojo.User;

import java.sql.*;
import java.util.List;
import java.util.function.Function;

public class ConnectionDBImpl implements ConnectionDB {
    private static final ConnectionDB INSTANCE = new ConnectionDBImpl();
    private static final int PORT = 5432;
    private static final String DB = "taskdairy";
    private static final String CONNECTION_URL = "jdbc:postgresql://localhost:%d/%s";
    private static final String user = "admin";
    private static final String pass = "admin";
    private static Logger logger = Logger.getLogger(ConnectionDBImpl.class);
    private final DataSource dataSource;

    private ConnectionDBImpl() {
        PoolProperties p = new PoolProperties();
        p.setUrl(String.format(CONNECTION_URL, PORT, DB));
        p.setDriverClassName("org.postgresql.Driver");
        p.setUsername(user);
        p.setPassword(pass);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" +
                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        dataSource = new DataSource();
        dataSource.setPoolProperties(p);
    }

    public static ConnectionDB getInstance() {
        return INSTANCE;
    }

    public Connection getConnect() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return connection;
    }

    public <T> T getFromDB(FunctionSQL<Connection, T> function) throws SQLException {
        T obj = null;
        try (Connection connection = getConnect()) {
            obj = function.apply(connection);
        } catch (SQLException e) {
//            e.printStackTrace();
            throw e;
        }
        return obj;
    }
}
