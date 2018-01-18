package xml;

import pojo.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JaxbUnmarshall {
    public static List<User> convertUserToXml(String filePath) {
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
}
