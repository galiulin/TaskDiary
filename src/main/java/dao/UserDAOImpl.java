package dao;

import connection.*;
import pojo.Role;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private ConnectionDB conDB;

    public UserDAOImpl(ConnectionDB connectionDB) {
        this.conDB = connectionDB;
    }

    /**
     * получение пользователя по логину
     */
    @Override
    public User getUserByLogin(String login) throws SQLException {
        User result = null;
        Connection connection = conDB.getConnect();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT id, login, first_name, last_name, password, role FROM user_t WHERE user_t.login = ?;");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            result = constructingUserFromField(resultSet);
        }
        return result;
    }

    /***/
    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> result = new ArrayList<>();
        Connection connection = conDB.getConnect();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_t");
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            result.add(constructingUserFromField(set));
        }
        return result;
    }

    @Override
    public int insertNewUser(User user) throws SQLException {
        Connection connection = conDB.getConnect();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO user_t (login, first_name, last_name, password, role) " +
                        "VALUES (?, ?, ?, ?, ?) RETURNING id;");
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getRole().name());
        int result = -1;
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            result = set.getInt("id");
        }
        user.setId(result);
        return result;
    }

    /**
     * метод не для публичного доступа
     */
    private void deleteUser(User user) throws SQLException {
        Connection connection = conDB.getConnect();
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM user_t WHERE login = ?;"
        );
        statement.setString(1, user.getLogin());
        statement.execute();
    }

    @Override
    public void updateUserFields(User user) throws SQLException {
        Connection connection = conDB.getConnect();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE user_t SET first_name = ?, last_name = ? WHERE login = ?"
        );
        statement.setString(3, user.getLogin());
        statement.setString(1, user.getFirstName());
        statement.setString(2, user.getLastName());
        statement.executeUpdate();
    }

    @Override
    public void updateUserPassword(User user, String password) throws SQLException {
        Connection connection = conDB.getConnect();
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE user_t SET password = ? WHERE login = ?"
        );
        statement.setString(1, password);
        statement.setString(2, user.getLogin());
        statement.executeUpdate();
    }

    /**
     * вспомогательный метод для конструирования объекта пользователь из полей ResultSet
     */
    private User constructingUserFromField(ResultSet set) throws SQLException {
        int id = set.getInt("id");
        String loginDB = set.getString("login");
        String first_name = set.getString("first_name");
        String last_name = set.getString("last_name");
        String password = null;
        try {
            password = set.getString("password");
        } catch (SQLException ex) {
            /*не все запросы содержат поле с паролем*/
        }
        Role role = Role.valueOf(set.getString("role"));
        return new User(id, loginDB, first_name, last_name, password, role);
    }
}
