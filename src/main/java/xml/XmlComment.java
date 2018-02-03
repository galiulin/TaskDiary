package xml;

import db.pojo.Comment;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "comments")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlComment {

        @XmlElement(name = "comment")
        private List<Comment> comments = null;

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }
    }
