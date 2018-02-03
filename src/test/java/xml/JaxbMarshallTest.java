package xml;

import db.connection.ConnectionDB;
import db.connection.ConnectionDBImpl;
import db.dao.TaskDAO;
import db.dao.TaskDAOImpl;
import db.dao.UserDAO;
import db.dao.UserDAOImpl;
import org.junit.jupiter.api.Test;
import db.pojo.Task;
import db.pojo.User;

import java.sql.SQLException;
import java.util.List;

class JaxbMarshallTest {

//    @Test
    void convertUserToXml() throws SQLException {
        ConnectionDB connectionDB = ConnectionDBImpl.getInstance();
//        Connection db.connection = connectionDB.getConnect();
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