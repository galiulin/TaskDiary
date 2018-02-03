package service;

import db.dao.UserDAO;
import db.dao.UserDAOImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import db.pojo.Role;
import db.pojo.User;

import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class LoginServiceTest {

    //    private LoginService service = new LoginService();
    private static Logger logger = Logger.getLogger(LoginServiceTest.class);

    @BeforeAll
    static void setUp() {
        try {
            Field field = UserService.class.getDeclaredField("userDAO");
            field.setAccessible(true);
            UserDAO mock = mock(UserDAO.class);
            field.set(UserService.class, mock);
            when(mock.getUserByLogin("admins")).thenReturn(new User(5, "admins", "name", "lastN", "pass", Role.ROLE_ADMIN));
        } catch (NoSuchFieldException e) {
            logger.warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    static void tearDown() {
        try {
            Field field = UserService.class.getDeclaredField("userDAO");
            field.setAccessible(true);
            UserDAO userDAO = new UserDAOImpl();
            field.set(UserService.class, userDAO);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checkUser() {
//        assertTrue(LoginService.checkUser("admins", "pass"));
//        assertFalse(LoginService.checkUser("admins", "password"));
//        assertFalse(LoginService.checkUser("admins", null));
//        assertFalse(LoginService.checkUser(null, null));
//        assertFalse(LoginService.checkUser(null, "password"));
//        assertFalse(LoginService.checkUser("adm", "pass"));
    }
}