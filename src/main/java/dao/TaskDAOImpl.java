package dao;

import connection.*;
import pojo.Condition;
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
                    "SELECT id, condition, description, date_add, dead_line, user_id FROM task;");
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
        Task task = new Task(id, condition, description, dateAdd, deadLine, userId);
        return task;
    }

    /**
     * добавляет новую задачу в базу данных
     */
    @Override
    public void addTask(Task task) {
        connectionDB.getFromDB(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO task " +
                            "(condition, description, date_add, dead_line, user_id) VALUES " +
                            "(?, ?, ?, ?, ?) RETURNING id ");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                task.setId(set.getInt("id"));
            }
            return task;
        });
    }

    /**
     *  меняет статус задачи
     */
    @Override
    public void updateCondition(Task task, Condition condition) {
        connectionDB.getFromDB(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE task SET condition = ? WHERE id = ?"
            );
            statement.setString(1, condition.name());
            statement.setInt(2, task.getId());
            return statement.execute();
        });
    }

}
