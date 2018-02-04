package db.dao;

import db.connection.*;
import db.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import db.pojo.Role;
import db.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private ConnectionDB conDB;

    /**
     * получение {@link User} по логину
     */
    @Override
    public User getUserByLogin(String login) throws DAOException {
        try {
            return conDB.getFromDB(connection -> {
                User result = null;
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, login, first_name, last_name, password, role FROM user_t WHERE user_t.login = ?;");
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    result = constructingUserFromField(resultSet);
                }
                return result;
            });
        } catch (SQLException ex) {
            throw new DAOException("getUserByLogin()", ex);
        }
    }

    /**
     * получение всех {@link User} из базы данных
     */
    @Override
    public List<User> getAllUsers() throws DAOException {
        try {
            return conDB.getFromDB(connection -> {
                List<User> result = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_t");
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    result.add(constructingUserFromField(set));
                }
                return result;
            });
        } catch (SQLException ex) {
            throw new DAOException("getAllUsers()", ex);
        }
    }

    /**
     * вставка нового пользователя
     */
    @Override
    public int insertNewUser(User user) throws DAOException {
        try {
            return conDB.getFromDB(connection -> {
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
            });
        } catch (SQLException ex) {
            throw new DAOException("Не удалось добавить нового пользователя " + user, ex);
        }
    }

    /**
     * метод не для публичного доступа удаление пользователя
     */
    private Boolean deleteUser(User user) throws DAOException {
        try {
            return conDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user_t WHERE login = ?;"
                );
                statement.setString(1, user.getLogin());
                return statement.execute();
            });
        } catch (SQLException ex) {
            throw new DAOException("Не удалось удалить пользователя ", ex);
        }
    }

    /**
     * Обновление пользовательских данных имени, фамилии, пароль
     */
    @Override
    public Integer updateUserFields(User user) throws DAOException {
        try {
            return conDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_t SET first_name = ?, last_name = ?, password = ? WHERE login = ?"
                );
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getLogin());
                return statement.executeUpdate();
            });
        } catch (SQLException ex) {
            throw new DAOException("Не удалось обновить данные пользователя ", ex);
        }
    }

    /**
     * Обновление пароля пользователя
     */
    @Override
    public void updateUserPassword(User user, String password) throws DAOException {
        try {
            conDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_t SET password = ? WHERE login = ?"
                );
                statement.setString(1, password);
                statement.setString(2, user.getLogin());
                return statement.executeUpdate();
            });
        } catch (SQLException ex) {
            throw new DAOException("Не удалось обновить пароль пользователя ", ex);
        }
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
