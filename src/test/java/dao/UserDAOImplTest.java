package dao;

import connection.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Role;
import pojo.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    /**
     * получение пользователя по логину
     * */
    @Test
    void getUserByLogin() throws SQLException {
        assertNull(userDAO.getUserByLogin("test_admin_1"));
        assertNotNull(userDAO.getUserByLogin("adminTest"));
    }

    /**
     * добавление нового пользователя Worker
     * */
    @Test
    void insertNewUserWorker() throws SQLException {
        String login = "testMethodWorker";
        User user = new User(login, "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.WORKER);
        int count = userDAO.insertNewUser(user);
        User userFromDB = userDAO.getUserByLogin(login);
        assertEquals(user, userFromDB);
        deleteUser(user);
    }

    /**
     * добавление нового администратора
     * */
    @Test
    void insertNewUserAdmin() throws SQLException {
        String login = "testMethodAdmin";
        User user = new User(login, "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.ADMIN);
        int count = userDAO.insertNewUser(user);
        User userFromDB = userDAO.getUserByLogin(login);
        assertEquals(user, userFromDB);
        deleteUser(user);
    }

    /**
     * добавление нового менеджера
     * */
    @Test
    void insertNewUserManager() throws SQLException {
        String login = "testMethodManager";
        User user = new User(login, "firstNameTestMethod", "lastNameTestMethod", "testPass", Role.MANAGER);
        int count = userDAO.insertNewUser(user);
        User userFromDB = userDAO.getUserByLogin(login);
        assertEquals(user, userFromDB);
        deleteUser(user);
    }

    /**
     * Обновляем пользовательские поля фамилию и имя
     * */
    @Test
    void updateUserFields() throws SQLException {
        String login = "updateUserField";
        User user = new User(login, "beforeUpdateFirstName", "beforeUpdateLastName", "updatePass", Role.WORKER);
        userDAO.insertNewUser(user);
        User user1 = userDAO.getUserByLogin(login);
        assertEquals(user, user1);

        user.setFirstName("afterUpdateFirstName");
        user.setLastName("afterUpdateLastName");

        userDAO.updateUserFields(user);
        User user2 = userDAO.getUserByLogin(login);
        assertEquals(user, user2);
        assertNotEquals(user1, user2);
        deleteUser(user);
    }

    /**
     * получение списка всех пользователей
     * */
    void getAllUsers() throws SQLException {
        List<User> list = userDAO.getAllUsers();
        for (User user : list){
            System.out.println(user);
        }
    }

    /**
     * проверка удаления пользователя из бд
     * */
    @Test
    void testDeleteUser() throws SQLException {
              User user =  new User("someLoginBeforeDelete","firstName", "lastNmee", "pass", Role.WORKER);
        userDAO.insertNewUser(user);
        deleteUser(user);

        user = userDAO.getUserByLogin(user.getLogin());
        assertNull(user);
    }

    /**
     * вспомогательный метод для удаления пользователя
     * */
    private void deleteUser(User user){
        try {
           Method method = userDAO.getClass().getDeclaredMethod("deleteUser", User.class);
           method.setAccessible(true);
           method.invoke(userDAO, user);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * тестирование удаления пользователя
     * */
    @Test
    void testUpdateUserPassword() throws SQLException {
        String login = "updatePass";
        User user = new User(login, "First_name", "last_name", "before update", Role.WORKER);
        deleteUser(user);
        userDAO.insertNewUser(user);
        String newPass = "after update";
        userDAO.updateUserPassword(user, newPass);
        User user2 = userDAO.getUserByLogin(login);
        assertEquals(newPass, user2.getPassword());
        deleteUser(user);
    }
}