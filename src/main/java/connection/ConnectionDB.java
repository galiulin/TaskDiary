package connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

public interface ConnectionDB {
    public Connection getConnect();
    public <T> T getFromDB(FunctionSQL<Connection, T> function) throws SQLException;
}
