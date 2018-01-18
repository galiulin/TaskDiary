package dao;

import pojo.Condition;
import pojo.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDAO {
    List<Task> getAllTasks() throws SQLException;

    void addTask(Task task);

    void updateCondition(Task task, Condition condition);
}
