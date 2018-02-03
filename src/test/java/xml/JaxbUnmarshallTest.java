package xml;

import db.pojo.Task;
import db.pojo.User;

import java.util.List;

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