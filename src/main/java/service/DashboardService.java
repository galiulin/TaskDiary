package service;

import connection.ConnectionDBImpl;
import dao.TaskDAO;
import dao.TaskDAOImpl;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pojo.Condition;
import pojo.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DashboardService {

    private TaskDAO taskDAO = new TaskDAOImpl(ConnectionDBImpl.getInstance());
    private Logger logger = Logger.getLogger(DashboardService.class);

    public List<Task> getAllTask() {
        logger.trace("запрошен список всех задач");
        List<Task> result = new ArrayList<>();
        try {
            List<Task> list = taskDAO.getAllTasks();
            list.stream().filter(task -> (task.getCondition().equals(Condition.IN_PROCESS)
                    || task.getCondition().equals(Condition.VERIFICATION))).forEach(obj -> result.add(obj));
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
        }
        return result;
    }
}
