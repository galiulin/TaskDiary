package service;

import connection.ConnectionDBImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pojo.User;

import java.sql.SQLException;

@Component
public class LoginService {

    private UserDAO userDAO = new UserDAOImpl(ConnectionDBImpl.getInstance());
    private static Logger logger = Logger.getLogger(LoginService.class);

    /**
     * Проверяет пользователя на наличие в базе данных и сверяет пароль
     *
     * @return true если пользователь имеется в базе данных и пароль соответствует.
     * <p>
     * false во всех остальных случаях
     */
    public boolean checkUser(String login, String password) {
        if (login == null || password == null) {
            logger.debug("Login = " + login + ", password = " + password);
            return false;
        }
        boolean access = false;
        try {
            User user = userDAO.getUserByLogin(login);
            logger.trace("найденный пользователь " + (user == null ? "null" : user));
            if (user != null && user.getPassword().equals(password)) {
                access = true;
            }
        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return access;
    }
}
