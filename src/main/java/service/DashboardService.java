package service;

import common.Logged;
import db.exceptions.DAOException;
import db.dao.TaskDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import db.pojo.Condition;
import db.pojo.Role;
import db.pojo.Task;
import db.pojo.User;

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
    public List<Task> getTasks(User user) throws DAOException {
        logger.trace("Был запрошен список всех задач");
        if (user.getRole() == null) {
            logger.warn("user Role is null");
            throw new NullPointerException("user Role is null");
        }

        List<Task> result = null;
        switch (user.getRole()) {
            case ROLE_WORKER:
                result = tasksWorker(user);
                break;
            case ROLE_MANAGER:
                result = taskManager(user);
                break;
            case ROLE_ADMIN:
                result = tasksAdmin(user);
                break;
        }
        return result;
    }

    /**
     * получение задач с уровнем доступа администратора
     * в настоящий момент возвращает все доступные задачи.
     * Возращает пустой список если база пустая
     */
    private List<Task> tasksAdmin(User user) throws DAOException {
        return taskDAO.getAllTasks();
    }

    /**
     * получение задач с уровнем доступа менеджера
     */
    private List<Task> taskManager(User user) throws DAOException {
        List<Task> list = new ArrayList<>();
        list.addAll(taskDAO.getTaskByCondition(Condition.VERIFICATION));
        list.addAll(taskDAO.getTaskByCondition(Condition.IN_PROCESS));
        return list;
    }

    /**
     * получение задач для пользователя с уровнем доступа рабочего
     */
    private List<Task> tasksWorker(User user) throws DAOException {
        return taskDAO.getTaskByUserId(user.getId());
    }
}
