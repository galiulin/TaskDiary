package xml;

import pojo.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlUsers {

        @XmlElement(name = "user")
        private List<User> users = null;

        public List<User> getUsers() {
            return users;
        }

        public void setUsers(List<User> users) {
            this.users = users;
    }
}
