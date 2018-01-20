package dao;

import connection.ConnectionDB;
import org.apache.log4j.Logger;
import pojo.Comment;
import pojo.Task;
import pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAOImpl implements CommentDAO {
    Logger logger = Logger.getLogger(CommentDAOImpl.class);
    ConnectionDB connectionDB;

    public CommentDAOImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;

        logger.trace("init");
    }

    /**
     * добавление комментария к задаче
     */
    @Override
    public Comment addComment(Task task, Comment comment, User user, Timestamp time) {
        comment.setUserId(user.getId());
        comment.setTaskId(task.getId());
        comment.setTimestamp(time);
        return addComment(comment);
    }

    /**
     * добавление комментария к задаче
     */
    @Override
    public Comment addComment(int taskId, String comment, int userId, Timestamp time) {
        Comment comm = new Comment(taskId, userId, time, comment);
        return addComment(comm);
    }

    /**
     * добавление комментария к задаче
     */
    @Override
    public Comment addComment(Comment comment) {
        return connectionDB.getFromDB(conn -> {
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
            logger.trace(String.format("addComment(%s)", comment));

            return comment;
        });
    }

    /**
     * получение всех комментариев
     */
    @Override
    public List<Comment> getAllComments() {
        return connectionDB.getFromDB(conn -> {
            List<Comment> list = new ArrayList<>();
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("SELECT id, comment, task_id, user_id, date FROM comment");
            while (set.next()) {
                list.add(parseResSet(set));
            }
            logger.trace(String.format("getAllComments()"));
            return list;
        });
    }

    /**
     * вспомогательный метод для парсинга ResultSet
     */
    private Comment parseResSet(ResultSet set) throws SQLException {
        int id = set.getInt("id");
        String commentStr = set.getString("comment");
        int task_id = set.getInt("task_id");
        int user_id = set.getInt("user_id");
        Timestamp date = set.getTimestamp("date");
        Comment comment = new Comment(task_id, user_id, date, commentStr);
        comment.setId(id);
        return comment;
    }

    /**
     * получение комментария по id пользователя
     */
    @Override
    public List<Comment> getCommentByUserId(int userId) {
        return connectionDB.getFromDB(conn -> {
            List<Comment> result = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement("SELECT id, comment, task_id, user_id, date FROM comment WHERE user_id = ?;");
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(parseResSet(set));
            }
            logger.trace(String.format("getCommentByUserId(%d)", userId));
            return result;
        });
    }

    /**
     * получение комментария по id задачи
     */
    @Override
    public List<Comment> getCommentByTaskId(int taskId) {
        return connectionDB.getFromDB(conn -> {
            List<Comment> result = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement("SELECT id, comment, task_id, user_id, date FROM comment WHERE task_id = ?;");
            statement.setInt(1, taskId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                result.add(parseResSet(set));
            }
            logger.trace(String.format("getCommentByTaksId(%d)", taskId));
            return result;
        });
    }
}
