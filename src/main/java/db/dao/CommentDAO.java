package db.dao;

import db.exceptions.DAOException;
import db.pojo.Comment;
import db.pojo.Task;
import db.pojo.User;

import java.sql.Timestamp;
import java.util.List;

public interface CommentDAO {
    /**
     * добавление комментария к задаче
     *
     * @return только что добавленный комментарий вместе с полученным id
     */
    Comment addComment(Task task, Comment comment, User user, Timestamp time) throws DAOException;


    /**
     * добавление комментария к задаче
     *
     * @return только что добавленный комментарий вместе с полученным id
     */
    Comment addComment(int taskId, String comment, int userId, Timestamp time) throws DAOException;

    /**
     * добавление комментария к задаче
     *
     * @return только что добавленный комментарий вместе с полученным id
     */
    Comment addComment(Comment comment) throws DAOException;

    /**
     * получение всех комментариев
     *
     * @return список всех комментариев
     */
    List<Comment> getAllComments() throws DAOException;


    /**
     * @param userId id пользователя по которому будет проводиться поиск.
     * @return список пользователя по указанному id
     */
    List<Comment> getCommentByUserId(int userId) throws DAOException;

    /**
     * поиск комментариев по id задачи
     *
     * @param taskId id задачи
     * @return список пользователей по указанному id задачи
     */
    List<Comment> getCommentByTaskId(int taskId) throws DAOException;
}
