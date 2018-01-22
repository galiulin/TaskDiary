package servlets;

import org.apache.log4j.Logger;
import pojo.Task;
import service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TaskServlet extends HttpServlet {
    Logger logger = Logger.getLogger(TaskServlet.class);
    TaskService taskService = new TaskService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskId = req.getParameter("taskId");

        logger.debug(String.format("Запрошена задача № %s", taskId));
        int id = Integer.parseInt(taskId);
        Task task = taskService.getTask(id);
        req.setAttribute("task", task);
        req.getRequestDispatcher("/task.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
