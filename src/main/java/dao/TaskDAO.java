package dao;

import pojo.Condition;
import pojo.Role;
import pojo.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDAO {

    /**
     * получение всех задач
     */
    List<Task> getAllTasks() throws DAOException;

    void addTask(Task task) throws DAOException;

    void updateCondition(Task task, Condition condition) throws DAOException;

    /**
     * получение задачи по id
     * */
    Task getTaskById(int id) throws DAOException;

    /**
     * получение задач по состояниям
     * */
    List<Task> getTaskByCondition(Condition condition) throws DAOException;
}
