package xml;

import common.Logged;
import db.dao.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import db.pojo.Comment;
import db.pojo.Task;
import db.pojo.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class JaxbMarshall {

    @Logged
    private static Logger logger;

    @Autowired
    private static UserDAO userDAO;

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

    @Autowired
    private static TaskDAO taskDAO;

    /**
     * сохраняем задачи в xml
     * */
    public static void converTaskToXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlTask.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

//            TaskDAO taskDAO = new TaskDAOImpl(ConnectionDBImpl.getInstance());
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

    @Autowired
    private static CommentDAO commentDAO;

    public static void converCommentToXml(String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(XmlComment.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            List<Comment> comments = commentDAO.getAllComments();

            XmlComment xmlComment = new XmlComment();
            xmlComment.setComments(comments);
            marshaller.marshal(xmlComment, new File(filePath));
        } catch (JAXBException e) {
            logger.warn(e.getMessage(), e);
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
