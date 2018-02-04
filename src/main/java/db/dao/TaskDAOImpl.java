package db.dao;

import db.connection.*;
import db.exceptions.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import db.pojo.Condition;
import db.pojo.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class TaskDAOImpl implements TaskDAO {

    @Autowired
    private ConnectionDB connectionDB;

    /**
     * получение списка всех задач
     *
     * @return ArrayList со списком задач,
     * <p>
     * пустой ArrayList если задач нет
     */
    @Override
    public List<Task> getAllTasks() throws DAOException {
        try {
            return connectionDB.getFromDB(connection -> {
                List<Task> list = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, condition, description, date_add, dead_line, user_id, title FROM task;");
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    list.add(constructingTaskFromField(resultSet));
                }
                return list;
            });
        } catch (SQLException ex) {
            throw new DAOException("getAllTasks()", ex);
        }
    }

    /**
     * вспомогательный метод для конструирования {@link Task} из полей ResultSet
     */
    private Task constructingTaskFromField(ResultSet resultSet) throws SQLException {
            int id = resultSet.getInt("id");
            Condition condition = Condition.valueOf(resultSet.getString("condition"));
            String description = resultSet.getString("description");
            Timestamp dateAdd = Timestamp.valueOf(resultSet.getString("date_add"));
            Timestamp deadLine = Timestamp.valueOf(resultSet.getString("dead_line"));
            int userId = resultSet.getInt("user_id");
            String title = resultSet.getString("title");
            Task task = new Task(id, condition, title, description, dateAdd, deadLine, userId);
            return task;
    }

    /**
     * добавляет новую задачу в базу данных
     */
    @Override
    public void addTask(Task task) throws DAOException {
        try {
            connectionDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO task " +
                                "(condition, description, date_add, dead_line, user_id, title) VALUES " +
                                "(?, ?, ?, ?, ?, ?) RETURNING id ");
                statement.setString(1, task.getCondition().name());
                statement.setString(2, task.getDescription());
                statement.setTimestamp(3, task.getDateAdd());
                statement.setTimestamp(4, task.getDeadLine());
                statement.setInt(5, task.getUserId());
                statement.setString(6, task.getTitle());
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    task.setId(set.getInt("id"));
                }
                return task;
            });
        } catch (SQLException ex) {
            throw new DAOException("addTask()", ex);
        }
    }

    /**
     * меняет статус задачи
     */
    @Override
    public void updateCondition(Task task, Condition condition) throws DAOException {
        try {
            connectionDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE task SET condition = ? WHERE id = ?"
                );
                statement.setString(1, condition.name());
                statement.setInt(2, task.getId());
                return statement.execute();
            });
        } catch (SQLException ex) {
            throw new DAOException("addTask()", ex);
        }
    }

    /**
     * получение задачи по ее id
     */
    @Override
    public Task getTaskById(int id) throws DAOException {
        try {
            return connectionDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, condition, description, date_add, dead_line, user_id, title FROM task WHERE task.id = ?"
                );
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                Task task = null;
                if (set.next()) {
                    task = constructingTaskFromField(set);
                }
                return task;
            });
        } catch (SQLException ex) {
            throw new DAOException("addTask()", ex);
        }
    }

    @Override
    public List<Task> getTaskByCondition(Condition condition) throws DAOException {
        try {
            return connectionDB.getFromDB(connection -> {
                List<Task> result;
                result = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, condition, description, date_add, dead_line, user_id, title FROM task WHERE task.condition = ?"
                );
                statement.setString(1, condition.name());
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    result.add(constructingTaskFromField(set));
                }
                return result;
            });
        } catch (SQLException ex) {
            throw new DAOException("addTask()", ex);
        }
    }

    @Override
    public Task editTask(Task task) throws DAOException {
        try {
            return connectionDB.getFromDB(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE task SET " +
                                "condition=?, " +
                                "description=?, " +
                                "user_id=?, " +
                                "title=? " +
                                "WHERE id=? RETURNING date_add, dead_line"
                );
                statement.setString(1, task.getCondition().name());
                statement.setString(2, task.getDescription());
                statement.setInt(3, task.getUserId());
                statement.setString(4, task.getTitle());
                statement.setInt(5, task.getId());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    task.setDateAdd(resultSet.getTimestamp("date_add"));
                    task.setDeadLine(resultSet.getTimestamp("dead_line"));
                }
                return task;
            });
        } catch (SQLException ex) {
            throw new DAOException("exception in editTask", ex);
        }
    }

    @Override
    public List<Task> getTaskByUserId(int id) throws DAOException {
        try {
            return connectionDB.getFromDB(connection -> {
                List<Task> result = new ArrayList<>();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT id, condition, description, date_add, dead_line, user_id, title FROM task WHERE task.user_id = ?"
                );
                statement.setInt(1, id);
                ResultSet set = statement.executeQuery();
                while (set.next()) {
                    Task task = constructingTaskFromField(set);
                    result.add(task);
                }
                return result;

            });
        } catch (SQLException ex) {
            throw new DAOException("не удалось получить задачи пользователя " + ex);
        }
    }
}
