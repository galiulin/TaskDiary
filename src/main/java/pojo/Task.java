package pojo;


import java.sql.Timestamp;

public class Task {

    private int id;
    private Condition condition;
    private String description;
    private java.sql.Timestamp dateAdd;
    private java.sql.Timestamp deadLine;
    private int userId;

    public Task(Condition condition, String description, Timestamp dateAdd, Timestamp deadLine, int userId) {
        this.condition = condition;
        this.description = description;
        this.dateAdd = dateAdd;
        this.deadLine = deadLine;
        this.userId = userId;
    }

    public Task(int id, Condition condition, String description, Timestamp dateAdd, Timestamp deadLine, int userId) {
        this.id = id;
        this.condition = condition;
        this.description = description;
        this.dateAdd = dateAdd;
        this.deadLine = deadLine;
        this.userId = userId;
    }

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


    public java.sql.Timestamp getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(java.sql.Timestamp dateAdd) {
        this.dateAdd = dateAdd;
    }


    public java.sql.Timestamp getDeadLine() {
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
