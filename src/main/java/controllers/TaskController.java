package controllers;

import common.Logged;
import dao.DAOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pojo.Condition;
import pojo.Task;
import service.TaskService;

@Controller
public class TaskController {

    @Logged
    private Logger logger;

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String showTaskPage(@RequestParam(name = "taskId") String taskId, Model model) {
        logger.trace(String.format("Запрошена задача №%s", taskId));
        int id = Integer.parseInt(taskId);
        Task task = taskService.getTask(id);
        logger.trace("Возвращаю задачу = " + task);
        model.addAttribute("task", task);
        return "task";
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public String editTask(
            @RequestParam(name = "taskId") String taskId,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "personInCharge", required = false) String personInCharge,
            @RequestParam(name = "condition") String condition,
            Model model) {
//        try {
//            description = new String(description.getBytes(), "UTF-16");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        logger.trace("taskId:" + taskId);
        logger.trace("title:" + title);
        logger.trace("description:" + description);
        logger.trace("personInCharge:" + personInCharge);
        logger.trace("condition:" + condition);

        int id = Integer.parseInt(taskId);
        int person = Integer.parseInt(personInCharge);
        Condition condition_value = Condition.valueOf(condition);

        String result = "redirect:/task?taskId=" + id;
        try {
            taskService.editTask(id, title, description, person, condition_value);
        } catch (DAOException e) {
            logger.warn(e.getMessage(), e);
            result = "redirect:/plug";
        }
        return result;

    }
}
