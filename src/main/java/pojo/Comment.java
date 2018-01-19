package pojo;


import java.sql.Timestamp;

public class Comment {

    private int id;
    private int taskId;
    private int userId;
    private Timestamp date;
    private String comment;

    public Comment(int taskId, int userId, Timestamp date, String comment) {
        this.taskId = taskId;
        this.userId = userId;
        this.date = date;
        this.comment = comment;
    }

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

}
