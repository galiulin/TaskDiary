package servlets;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import pojo.Role;
import pojo.Task;
import pojo.User;
import service.DashboardService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DashboardServlet extends HttpServlet {

    @Autowired
    private DashboardService dashboardService;

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("login");
        List<Task> allTask = null;
        try {
            allTask = this.dashboardService.getTasks(user);
            req.setAttribute("tasks", allTask);
            req.getRequestDispatcher("/pages/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.warn(e.getMessage(), e);
            resp.getWriter().write("упс, что-то пошло не так");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
