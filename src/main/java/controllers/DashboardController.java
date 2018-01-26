package controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pojo.Role;
import pojo.Task;
import pojo.User;
import service.DashboardService;

import java.sql.SQLException;
import java.util.List;

@Controller
public class DashboardController {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    DashboardService dashboardService;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String showDashboardPage(Model model) {
        String result = "redirect:/plug";
        logger.debug("запрошена страница dashboard");
        User user = new User("admin", "admin", "admin", "admin", Role.ADMIN);
        List<Task> allTask = null;
        try {
            allTask = this.dashboardService.getTasks(user);
            model.addAttribute("tasks", allTask);
            logger.debug(String.format("количество задач для отображения на экране %d", allTask.size()));
            result = "dashboard";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
