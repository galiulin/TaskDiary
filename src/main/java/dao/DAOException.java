package dao;

import java.sql.SQLException;

public class DAOException extends SQLException {

    String message;
    public DAOException(String message, SQLException ex) {
        super(ex);
        this.message = message;
    }
}
