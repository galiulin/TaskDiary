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
    List<Task> getAllTasks() throws SQLException;

    void addTask(Task task) throws SQLException;

    void updateCondition(Task task, Condition condition) throws SQLException;

    /**
     * получение задачи по id
     * */
    Task getTaskById(int id) throws SQLException;

    /**
     * получение задач по состояниям
     * */
    List<Task> getTaskByCondition(Condition condition) throws SQLException;
}
