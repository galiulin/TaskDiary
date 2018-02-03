package db.pojo;


import xml.DateTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Timestamp;

@XmlRootElement(name = "Task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {

    @XmlAttribute
    private int id;

    @XmlElement(name = "condition")
    private Condition condition;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "date_add")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp dateAdd;

    @XmlElement(name = "dead_line")
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private Timestamp deadLine;

    @XmlElement(name = "user_id")
    private int userId;

    @XmlElement(name = "title")
    private String title;

    public Task(Condition condition, String title, String description, Timestamp dateAdd, Timestamp deadLine, int userId) {
        this.condition = condition;
        this.description = description;
        this.dateAdd = dateAdd;
        this.deadLine = deadLine;
        this.userId = userId;
        this.title = title;
    }

    public Task(int id, Condition condition, String title, String description, Timestamp dateAdd, Timestamp deadLine, int userId) {
        this.id = id;
        this.condition = condition;
        this.description = description;
        this.dateAdd = dateAdd;
        this.deadLine = deadLine;
        this.userId = userId;
        this.title = title;
    }

    public Task(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Timestamp getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(java.sql.Timestamp dateAdd) {
        this.dateAdd = dateAdd;
    }


    public Timestamp getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(java.sql.Timestamp deadLine) {
        this.deadLine = deadLine;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", condition=" + condition +
                ", description='" + description + '\'' +
                ", dateAdd=" + dateAdd +
                ", deadLine=" + deadLine +
                ", userId=" + userId +
                '}';
    }
}
