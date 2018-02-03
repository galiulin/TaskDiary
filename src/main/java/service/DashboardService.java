package service;

import common.Logged;
import connection.ConnectionDBImpl;
import dao.DAOException;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TaskDAO taskDAO;

    @Logged
    private Logger logger;

    /**
     * получение списка всех имеющихся задач
     */
    public List<Task> getAllTask() throws DAOException {
        logger.trace("запрошен список всех задач");
        List<Task> result = null;
            result = taskDAO.getAllTasks();
        return result;
    }

    /**
     * получение списка задач в зависимости от уровня доступа пользователя
     */
    public List<Task> getTasks(User user) throws SQLException {
        if (user.getRole() == null) {
            logger.warn("user Role is null");
            throw new NullPointerException("user Role is null");
        }

        List<Task> result = null;
            if (user.getRole() == Role.ROLE_WORKER) {
                result = tasksWorker(user);
            } else if (user.getRole() == Role.ROLE_MANAGER) {
                result = taskManager();
            } else if (user.getRole() == Role.ROLE_ADMIN) {
                result = tasksAdmin();
            }
        return result;
    }

    /**
     * получение задач с уровнем доступа администратора
     * в настоящий момент это все задачи
     * возращает пустой список если база пустая
     */
    private List<Task> tasksAdmin() throws SQLException {
        List<Task> result = null;
        result = taskDAO.getAllTasks();
        return result;
    }

    /**
     * получение задач с уровнем доступа менеджера
     */
    private List<Task> taskManager() throws SQLException {
        List<Task> list = new ArrayList<>();
        list.addAll(taskDAO.getTaskByCondition(Condition.VERIFICATION));
        list.addAll(taskDAO.getTaskByCondition(Condition.IN_PROCESS));
        return list;
    }

    /**
     * получение задач с уровнем доступа рабочего
     */
    private List<Task> tasksWorker(User user) throws SQLException {
        ArrayList<Task> result = new ArrayList<>();
        result.addAll(taskDAO.getTaskByCondition(Condition.IN_PROCESS).stream().filter(t ->
                t.getUserId() == user.getId()).collect(Collectors.toList()));
        return result;
    }
}
