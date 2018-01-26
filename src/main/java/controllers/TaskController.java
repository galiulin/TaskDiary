package controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Task;
import service.TaskService;

@Controller
public class TaskController {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String showTaskPage(@RequestParam(name = "taskId") String taskId, Model model) {
        logger.trace(String.format("Запрошена задача №%s", taskId));
        int id = Integer.parseInt(taskId);
        Task task = taskService.getTask(id);
        logger.trace("Возвращаю задачу = " + task);
        model.addAttribute("task", task);
        return "task";
    }
}
