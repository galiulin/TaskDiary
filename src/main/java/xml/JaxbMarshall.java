package xml;

import connection.ConnectionDB;
import connection.ConnectionDBImpl;
import dao.UserDAOImpl;
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
}
