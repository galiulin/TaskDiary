package service;

import connection.ConnectionDBImpl;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pojo.Task;

import java.sql.SQLException;

@Component
public class TaskService {

    Logger logger = Logger.getLogger(TaskService.class);

    @Autowired
    TaskDAO taskDAO;

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
