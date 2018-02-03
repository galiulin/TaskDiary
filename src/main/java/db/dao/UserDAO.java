package db.dao;

import db.pojo.User;

import java.util.List;

public interface UserDAO {

    /**
     * получение пользователя по логину
     */
    User getUserByLogin(String login) throws DAOException;

    /**
     * получение списка всех пользователей
     */
    List<User> getAllUsers() throws DAOException;

    /**
     * вставка нового пользователя
     */
    int insertNewUser(User user) throws DAOException;

    /**
     * обновление полей с фамилией и именем у пользователя
     */
    Integer updateUserFields(User user) throws DAOException;

    /**
     * обновление пользовательского пароля
     */
    void updateUserPassword(User user, String password) throws DAOException;
}
