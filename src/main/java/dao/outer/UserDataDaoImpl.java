package dao.outer;



import connection.ConnectionDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.outer.UserData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class UserDataDaoImpl implements UserDataDao {
    @Autowired
    private ConnectionDB connectionDB;

    @Override
    public int reg(int id_personal, String login
            , String password, String date_reg) throws SQLException {
        Connection connection = connectionDB.getConnect();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO " +
                        "user_data VALUES (DEFAULT , ?, ?, ?, ?);"
        );
        statement.setInt(1, id_personal);
        statement.setString(2, login);
        statement.setString(3, password);
        statement.setString(4, date_reg);
        statement.execute();

        Connection connection2 = connectionDB.getConnect();
        PreparedStatement statement1 = connection2.prepareStatement("SELECT id as id FROM " +
                "user_data WHERE login =  ? AND password = ?");
        statement1.setString(1, login);
        statement1.setString(2, password);

        ResultSet set = statement1.executeQuery();
        if (set.next()) {
            int id = set.getInt("id");
            connection.close();
            connection2.close();
            return id;
        }
        connection.close();
        connection2.close();
        return -1;
    }

    @Override
    public int auth(String login, String password) throws SQLException {
        Connection connection = connectionDB.getConnect();
        PreparedStatement statement = connection.prepareStatement("SELECT id " +
                "FROM user_data " +
                "WHERE login = ? AND password = ?");
        statement.setString(1, login);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int res = resultSet.getInt("id");
            connection.close();
            return res;
        }
        connection.close();
        return 0;
    }

    @Override
    public int getIdByName(String username) throws SQLException {
        Connection connection = connectionDB.getConnect();
        PreparedStatement statement = connection.prepareStatement("SELECT id " +
                "FROM user_data " +
                "WHERE login = ?");
        statement.setString(1, username);;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int res = resultSet.getInt("id");
            connection.close();
            return res;
        }
        connection.close();
        return 0;
    }
    @Override
    public UserData getLoginAndPass(String username) throws SQLException {
        Connection connection = connectionDB.getConnect();
        PreparedStatement statement = connection.prepareStatement("SELECT login, password " +
                "FROM user_data " +
                "WHERE login = ?");
        statement.setString(1, username);;
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            UserData userData = new UserData();
            userData.setLogin(resultSet.getString("login"));
            userData.setPassword(resultSet.getString("password"));
            connection.close();
            return userData;
        }
        connection.close();
        return null;
    }

}
