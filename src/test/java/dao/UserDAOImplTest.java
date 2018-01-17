package dao;

import connection.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Role;
import pojo.User;

import java.sql.SQLException;
import java.util.List;

class UserDAOImplTest {
    UserDAOImpl userDAO;

    @BeforeEach
    void setup(){
        userDAO = new UserDAOImpl(ConnectionDBImpl.getInstance());
    }

    @Test
    void getUserByLogin() throws SQLException {
        System.out.println(userDAO.getUserByLogin("test_admin_1"));
    }

    @Test
//    @Disabled
    void insertNewUser() throws SQLException {
        User user = new User("testMethod", "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.WORKER);
        int count = userDAO.insertNewUser(user);
//        assertEquals(1, count);
        System.out.println(count);
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