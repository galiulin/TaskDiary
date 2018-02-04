package controllers;

import common.Logged;
import db.exceptions.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import db.pojo.Role;
import db.pojo.Task;
import db.pojo.User;
import service.DashboardService;

import java.sql.SQLException;
import java.util.List;

@Controller
public class DashboardController {

    @Logged
    private Logger logger;

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String showDashboardPage(Model model) {
        String result;

        logger.debug("запрошена страница dashboard");
        try {
            User user = new User("admin", "admin", "admin", "admin", Role.ROLE_ADMIN); //TODO заменить пользователя на реального
            List<Task> allTask = this.dashboardService.getTasks(user);
            model.addAttribute("tasks", allTask);
            logger.debug(String.format("количество задач для отображения на экране %d", allTask.size()));
            result = "dashboard";
        } catch (DAOException e) {
            logger.error(e);
            result = "redirect:/plug";
        }
        return result;
    }
}
