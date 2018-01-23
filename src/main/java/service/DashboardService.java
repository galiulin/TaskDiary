package service;

import connection.ConnectionDBImpl;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pojo.Condition;
import pojo.Role;
import pojo.Task;
import pojo.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DashboardService {

    private TaskDAO taskDAO = new TaskDAOImpl(ConnectionDBImpl.getInstance());
    private Logger logger = Logger.getLogger(DashboardService.class);

    public List<Task> getAllTask() {
        logger.trace("запрошен список всех задач");
        List<Task> result = new ArrayList<>();
        try {
            List<Task> list = taskDAO.getAllTasks();
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return result;
    }

    public List<Task> getTasks(User user) {
        if (user.getRole() == null) {
            logger.warn("user Role is null");
            throw new NullPointerException("user Role is null");
        }

        List<Task> result = null;
        try {
            if (user.getRole() == Role.WORKER) {
                result = tasksWorker(user);
            } else if (user.getRole() == Role.MANAGER) {
                result = taskManager();
            } else if (user.getRole() == Role.ADMIN) {
                result = tasksAdmin();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Task> tasksAdmin() throws SQLException {
        List<Task> result = new ArrayList<>();
        result = taskDAO.getAllTasks();
        return result;
    }

    private List<Task> taskManager() throws SQLException {
        List<Task> list = new ArrayList<>();
        list.addAll(taskDAO.getTaskByCondition(Condition.VERIFICATION));
        list.addAll(taskDAO.getTaskByCondition(Condition.IN_PROCESS));
        return list;
    }

    private List<Task> tasksWorker(User user) throws SQLException {
        ArrayList<Task> result = new ArrayList<>();
        result.addAll(taskDAO.getTaskByCondition(Condition.IN_PROCESS).stream().filter(t ->
                t.getUserId() == user.getId()).collect(Collectors.toList()));
        return result;
    }
}
