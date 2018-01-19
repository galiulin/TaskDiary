package xml;

import org.junit.jupiter.api.Test;
import pojo.Task;
import pojo.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JaxbUnmarshallTest {

//    @Test
    void convertXmlToUser() {
        List<User> list = JaxbUnmarshall.convertXmlToUser("ttesttfasdfa.xml");
        list.stream().forEach(System.out::println);
    }

//    @Test
    void convertXmlToTask() {
        List<Task> list = JaxbUnmarshall.convertXmlToTask("tasktestconverter.xml");
        list.stream().forEach(System.out::println);
    }
}