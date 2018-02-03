package service;

import common.Logged;
import db.dao.DAOException;
import db.dao.TaskDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import db.pojo.Condition;
import db.pojo.Task;

import java.sql.SQLException;

@Component
public class TaskService {

    @Logged
    private Logger logger;

    @Autowired
    private TaskDAO taskDAO;

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
