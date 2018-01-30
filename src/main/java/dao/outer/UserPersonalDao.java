package dao.outer;

import java.sql.SQLException;

public interface UserPersonalDao {
    int reg(String first_name, String last_name, String second_name, String birthday, String sex, String proffesion) throws SQLException;
}
