package dao;

import connection.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Role;
import pojo.User;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDAOImplTest {
    UserDAOImpl userDAO;


    @BeforeAll
    static void setup() throws NoSuchFieldException, SQLException {
        ConnectionDBTest test = new ConnectionDBTest();
        test.setup();
    }

    @BeforeEach
    void setup1(){
        userDAO = new UserDAOImpl(ConnectionDBImpl.getInstance());
    }

    @Test
    void getUserByLogin() throws SQLException {
        System.out.println(userDAO.getUserByLogin("test_admin_1"));
    }

    @Test
    void insertNewUserWorker() throws SQLException {
        String login = "testMethodWorker";
        User user = new User(login, "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.WORKER);
        int count = userDAO.insertNewUser(user);
        User userFromDB = userDAO.getUserByLogin(login);
        assertEquals(user, userFromDB);
    }

    @Test
    void insertNewUserAdmin() throws SQLException {
        String login = "testMethodAdmin";
        User user = new User(login, "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.ADMIN);
        int count = userDAO.insertNewUser(user);
        User userFromDB = userDAO.getUserByLogin(login);
        assertEquals(user, userFromDB);
    }
    @Test
    void insertNewUserManager() throws SQLException {
        String login = "testMethodManager";
        User user = new User(login, "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.MANAGER);
        int count = userDAO.insertNewUser(user);
        User userFromDB = userDAO.getUserByLogin(login);
        assertEquals(user, userFromDB);
    }

    @Test
    void updateUserFields() throws SQLException {
        User user = new User(9, "testUpdate", "updatedFirstName", "updatedLastName", "updatePass", Role.WORKER);
        userDAO.updateUserFields(user);
    }

    @Test
    void getAllUsers() throws SQLException {
        List<User> list = userDAO.getAllUsers();
        for (User user : list){
            System.out.println(user);
        }
    }
}