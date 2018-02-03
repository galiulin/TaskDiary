package xml;

import db.pojo.Comment;
import db.pojo.Task;
import db.pojo.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JaxbUnmarshall {
    public static List<User> convertXmlToUser(String filePath) {
        List<User> result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(XmlUsers.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlUsers xmlUsers = (XmlUsers) unmarshaller.unmarshal(new File(filePath));
            result = xmlUsers.getUsers();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Task> convertXmlToTask(String filePath) {
        List<Task> result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(XmlTask.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlTask xmlTask = (XmlTask) unmarshaller.unmarshal(new File(filePath));
            result = xmlTask.getTasks();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Comment> convertXmlToComments(String filePath) {
        List<Comment> result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(XmlComment.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlComment xmlComment = (XmlComment) unmarshaller.unmarshal(new File(filePath));
            result = xmlComment.getComments();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }
}
