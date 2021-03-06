package service;

import common.Logged;
import db.exceptions.DAOException;
import db.dao.UserDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import db.pojo.User;
import utils.MyPasswordEncoder;

import java.sql.SQLException;

@Component
public class UserService {

    @Logged
    private Logger logger;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MyPasswordEncoder encoder;

    /**
     * Проверяет пользователя на наличие в базе данных и сверяет пароль
     *
     * @return true если пользователь имеется в базе данных и пароль соответствует.
     * <p>
     * false во всех остальных случаях
     */
    public boolean checkUser(String login, String password) throws DAOException {
        if (login == null || password == null) {
            logger.debug("Login = " + login + ", password = " + password);
            return false;
        }
        boolean access = false;
        User user = userDAO.getUserByLogin(login);
        logger.trace("найденный пользователь " + (user == null ? "null" : user));
        if (user != null && user.getPassword().equals(password)) {
            access = true;
        }
        return access;
    }

    /**
     * получение {@link User} по логину
     * <p>
     * у полученного пользователя отсутствует пароль - значение установлено в "empty"
     * для получения пользователя с паролем смотрите UserDAO
     *
     * @return user
     * <p>
     * null если пользователь отсутствует или не получен
     */
    public User getUser(String login) throws DAOException {
        return userDAO.getUserByLogin(login);
    }

    public void saveUser(User user) throws DAOException {
        user.setPassword(encoder.encode(user.getPassword()));
        userDAO.insertNewUser(user);
    }
}
