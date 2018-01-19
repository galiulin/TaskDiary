package xml;

import connection.ConnectionDBImpl;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import dao.UserDAOImpl;
import pojo.Task;
import pojo.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class JaxbMarshall {

    /**
     * сохраняем User в XML файл
     *
     * @param filePath Путь до конечного файла в который сохраняется объект
     */
    public static void convertUserToXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlUsers.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            UserDAOImpl userDAO = new UserDAOImpl(ConnectionDBImpl.getInstance());
            List<User> users = userDAO.getAllUsers();

            XmlUsers xmlUsers = new XmlUsers();
            xmlUsers.setUsers(users);
            marshaller.marshal(xmlUsers, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * сохраняем задачи в xml
     * */
    public static void converTaskToXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlTask.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            TaskDAO taskDAO = new TaskDAOImpl(ConnectionDBImpl.getInstance());
            List<Task> tasks = taskDAO.getAllTasks();

            XmlTask xmlTasks = new XmlTask();
            xmlTasks.setTasks(tasks);
            marshaller.marshal(xmlTasks, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
