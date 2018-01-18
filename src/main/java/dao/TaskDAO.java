package dao;

import pojo.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDAO {
    List<Task> getAllTasks() throws SQLException;
}
