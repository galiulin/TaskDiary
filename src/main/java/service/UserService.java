package service;

import dao.DAOException;
import dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.User;

@Component
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User getUserByLogin(String login) throws DAOException {
        return userDAO.getUserByLogin(login);
    }
}
