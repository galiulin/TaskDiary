package service;

import connection.ConnectionDBImpl;
import dao.DAOException;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.Condition;
import pojo.Task;

import java.sql.SQLException;

@Component
public class TaskService {

    Logger logger = Logger.getLogger(TaskService.class);

    @Autowired
    TaskDAO taskDAO;

    public Task getTask(int id) {
        Task task = null;
        try {
            task = taskDAO.getTaskById(id);
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return task;
    }

    public Task editTask(int id, String title, String description, int person, Condition condition) throws DAOException {
        Task task = new Task(id, condition, title, description, null, null, person); //FIXME Добавить возможность изменять конечную дату
        return taskDAO.editTask(task);
    }
}
