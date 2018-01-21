package pojo;


import xml.DateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;
import java.util.Objects;

@XmlRootElement(name = "comment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Comment {

    @XmlAttribute
    private int id;

    @XmlElement(name = "task_id")
    private int taskId;

    @XmlElement(name = "user_id")
    private int userId;

    @XmlElement(name = "date")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp date;

    @XmlElement(name = "comment_field")
    private String comment;

    public Comment(int taskId, int userId, Timestamp date, String comment) {
        this.taskId = taskId;
        this.userId = userId;
        this.date = date;
        this.comment = comment;
    }

    public Comment(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public java.sql.Timestamp getDate() {
        return date;
    }

    public void setTimestamp(java.sql.Timestamp date) {
        this.date = date;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment1 = (Comment) o;
        return getId() == comment1.getId() &&
                getTaskId() == comment1.getTaskId() &&
                getUserId() == comment1.getUserId() &&
                Objects.equals(getDate(), comment1.getDate()) &&
                Objects.equals(getComment(), comment1.getComment());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getTaskId(), getUserId(), getDate(), getComment());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", taskId=" + taskId +
                ", userId=" + userId +
                ", date=" + date +
                ", comment='" + comment + '\'' +
                '}';
    }
}
