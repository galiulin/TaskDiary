package service;

import connection.ConnectionDBImpl;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import org.apache.log4j.Logger;
import pojo.Task;

import java.sql.SQLException;

public class TaskService {

    Logger logger = Logger.getLogger(TaskService.class);

    TaskDAO taskDAO = new TaskDAOImpl(ConnectionDBImpl.getInstance());

    public Task getTask(int id){
        Task task = null;
        try {
            task = taskDAO.getTaskById(id);
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return task;
    }
}
