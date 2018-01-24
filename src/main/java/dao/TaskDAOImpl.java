package dao;

import connection.*;
import pojo.Condition;
import pojo.Role;
import pojo.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    private ConnectionDB connectionDB;

    public TaskDAOImpl(ConnectionDB connectionDB) {
        this.connectionDB = connectionDB;
    }

    /**
     * получение списка всех задач
     */
    @Override
    public List<Task> getAllTasks() throws SQLException {
        List<Task> result = connectionDB.getFromDB(connection -> {
            List<Task> list = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, condition, description, date_add, dead_line, user_id, title FROM task;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(constructingTaskFromField(resultSet));
            }
            return list;
        });
        return result;
    }

    /**
     * вспомогательный метод для конструирования объекта Задача из полей ResultSet
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
    public void addTask(Task task) throws SQLException {
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
    }

    /**
     * меняет статус задачи
     */
    @Override
    public void updateCondition(Task task, Condition condition) throws SQLException {
        connectionDB.getFromDB(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task SET condition = ? WHERE id = ?"
            );
            statement.setString(1, condition.name());
            statement.setInt(2, task.getId());
            return statement.execute();
        });
    }

    /**
     * получение задачи по ее id
     */
    @Override
    public Task getTaskById(int id) throws SQLException {
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
    }

    @Override
    public List<Task> getTaskByCondition(Condition condition) throws SQLException {
        return connectionDB.getFromDB(connection -> {
            List<Task> result = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, condition, description, date_add, dead_line, user_id, title FROM task WHERE task.condition = ?"
            );
            statement.setString(1, condition.name());
            ResultSet set = statement.executeQuery();
            while (set.next()){
                result.add(constructingTaskFromField(set));
            }
            return result;
        });
    }
}
