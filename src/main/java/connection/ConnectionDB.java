package connection;

import java.sql.Connection;
import java.util.function.Function;

public interface ConnectionDB {
    public Connection getConnect();
    public <T> T getFromDB(FunctionSQL<Connection, T> function);
}
