package dao;

import pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {

    /**
     * получение пользователя по логину
     */
    User getUserByLogin(String login) throws SQLException;

    /**
     * получение списка всех пользователей
     */
    List<User> getAllUsers() throws SQLException;

    /**
     * вставка нового пользователя
     */
    int insertNewUser(User user) throws SQLException;

    /**
     * обновление полей с фамилией и именем у пользователя
     */
    Integer updateUserFields(User user) throws SQLException;

    /**
     * обновление пользовательского пароля
     */
    void updateUserPassword(User user, String password) throws SQLException;
}
