package dao;

import pojo.Comment;
import pojo.Task;
import pojo.User;

import java.sql.Timestamp;
import java.util.List;

public interface CommentDAO {
    /**
     * добавление комментария к задаче
     *
     * @return только что добавленный комментарий вместе с полученным id
     */
    Comment addComment(Task task, Comment comment, User user, Timestamp time);


    /**
     * добавление комментария к задаче
     *
     * @return только что добавленный комментарий вместе с полученным id
     */
    Comment addComment(int taskId, String comment, int userId, Timestamp time);

    /**
     * добавление комментария к задаче
     *
     * @return только что добавленный комментарий вместе с полученным id
     */
    Comment addComment(Comment comment);

    /**
     * получение всех комментариев
     *
     * @return список всех комментариев
     */
    List<Comment> getAllComments();


    /**
     * @param userId id пользователя по которому будет проводиться поиск.
     * @return список пользователя по указанному id
     */
    List<Comment> getCommentByUserId(int userId);

    /**
     * поиск комментариев по id задачи
     *
     * @param taskId id задачи
     * @return список пользователей по указанному id задачи
     */
    List<Comment> getCommentByTaskId(int taskId);
}
