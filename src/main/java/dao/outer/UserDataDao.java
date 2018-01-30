package dao.outer;

import org.springframework.stereotype.Component;
import pojo.outer.UserData;

import java.sql.SQLException;

public interface UserDataDao {
    public int reg(int id_personal, String login, String passord, String data_reg) throws SQLException ;
    public int auth(String login, String id_client) throws SQLException ;


    int getIdByName(String username) throws SQLException;

    UserData getLoginAndPass(String username) throws SQLException;
}
