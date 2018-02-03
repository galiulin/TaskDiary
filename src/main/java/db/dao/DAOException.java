package db.dao;

import java.sql.SQLException;

public class DAOException extends SQLException {

    private String message;

    public DAOException(String message, SQLException ex) {
        super(ex);
        this.message = message;
    }
}
