package db.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDB {
    public Connection getConnect();
    public <T> T getFromDB(FunctionSQL<Connection, T> function) throws SQLException;
}
