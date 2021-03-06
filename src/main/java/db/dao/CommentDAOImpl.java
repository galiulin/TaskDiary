package db.dao;

import common.Logged;
import db.connection.ConnectionDB;
import db.exceptions.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import db.pojo.Comment;
import db.pojo.Task;
import db.pojo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDAOImpl implements CommentDAO {

    @Logged
    private Logger logger;

    @Autowired
    private ConnectionDB connectionDB;

    /**
     * добавление комментария к задаче
     */
    @Override
    public Comment addComment(Task task, Comment comment, User user, Timestamp time) throws DAOException {
        comment.setUserId(user.getId());
        comment.setTaskId(task.getId());
        comment.setTimestamp(time);
        return addComment(comment);
    }

    /**
     * добавление комментария к задаче
     */
    @Override
    public Comment addComment(int taskId, String comment, int userId, Timestamp time) throws DAOException {
        Comment comm = new Comment(taskId, userId, time, comment);
        return addComment(comm);
    }

    /**
     * добавление комментария к задаче
     */
    @Override
    public Comment addComment(Comment comment) throws DAOException {
        try {
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
        } catch (SQLException ex) {
            throw new DAOException("addComment " + comment, ex);
        }
    }

    /**
     * получение всех комментариев
     */
    @Override
    public List<Comment> getAllComments() throws DAOException {
        try {
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
        } catch (SQLException ex) {
            throw new DAOException("getAllComments", ex);
        }
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
    public List<Comment> getCommentByUserId(int userId) throws DAOException {
        try {
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
        } catch (SQLException ex) {
            throw new DAOException("getCommentByUserId(" + userId, ex);
        }
    }

    /**
     * получение комментария по id задачи
     */
    @Override
    public List<Comment> getCommentByTaskId(int taskId) throws DAOException {
        try {
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
        } catch (SQLException ex) {
            throw new DAOException("getCommentByTaskId(" + taskId, ex);
        }
    }
}
