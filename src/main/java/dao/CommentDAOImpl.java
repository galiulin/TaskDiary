package dao;

import connection.ConnectionDB;
import pojo.Comment;
import pojo.Task;
import pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

public class CommentDAOImpl {
    ConnectionDB connectionDB;

    public CommentDAOImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    /**
     * добавление комментария к задаче
     */
    public void addComment(User user, Task task, Comment comment, Timestamp time) {
        comment.setUserId(user.getId());
        comment.setTaskId(task.getId());
        comment.setTimestamp(time);
        addComment(comment);
    }

    /**
     * добавление комментария к задаче
     */
    public void addComment(int userId, int taskId, String comment, Timestamp time) {
        Comment comm = new Comment(taskId, userId, time, comment);
        addComment(comm);
    }

    /**
     * добавление комментария к задаче
     */
    public void addComment(Comment comment) {
        connectionDB.getFromDB(conn -> {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO comment (task_id, user_id, date, comment) VALUES " +
                            "(?, ?, ?, ?) RETURNING id;"
            );
            statement.setInt(1, comment.getTaskId());
            statement.setInt(2, comment.getUserId());
            statement.setTimestamp(3, comment.getDate());
            statement.setString(4, comment.getComment());
            ResultSet resId = statement.executeQuery();
            while (resId.next()) {
                comment.setId(resId.getInt("id"));
            }

            return comment;
        });
    }

    /**todo получение всех комментариев */
    public void getAllComments(){
        connectionDB.getFromDB(conn -> {
            Statement statement = conn.createStatement();
            statement.executeQuery("SELECT id, comment, task_id, user_id, date FROM comment");
            return null;
        });
    }
}
