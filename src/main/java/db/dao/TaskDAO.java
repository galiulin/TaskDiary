package db.dao;

import db.pojo.Condition;
import db.pojo.Task;

import java.util.List;

public interface TaskDAO {

    /**
     * получение всех задач
     *
     * @return ArrayList со списком всех задач
     * <p>
     * пустой лист если задач нет
     */
    List<Task> getAllTasks() throws DAOException;

    void addTask(Task task) throws DAOException;

    void updateCondition(Task task, Condition condition) throws DAOException;

    /**
     * получение задачи по id
     */
    Task getTaskById(int id) throws DAOException;

    /**
     * получение задач по состояниям
     */
    List<Task> getTaskByCondition(Condition condition) throws DAOException;

    /**
     * редактирование имеющейся задачи
     * <p>
     *
     * FIXME:на текущий момент не редактируется дата окончания выполнения задачи
     */
    Task editTask(Task task) throws DAOException;
}
