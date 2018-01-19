package xml;

import connection.ConnectionDB;
import connection.ConnectionDBImpl;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import org.junit.jupiter.api.Test;
import pojo.Task;
import pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JaxbMarshallTest {

//    @Test
    void convertUserToXml() throws SQLException {
        ConnectionDB connectionDB = ConnectionDBImpl.getInstance();
//        Connection connection = connectionDB.getConnect();
        UserDAO userDAO = new UserDAOImpl(connectionDB);
        List<User> allUsers = userDAO.getAllUsers();
        JaxbMarshall.convertUserToXml("ttesttfasdfa.xml");
    }

    @Test
    void converTasksToXml() throws SQLException {
        TaskDAO taskDAO = new TaskDAOImpl(ConnectionDBImpl.getInstance());
        List<Task> allTasks = taskDAO.getAllTasks();
        JaxbMarshall.converTaskToXml("tasktestconverter.xml");
    }
}