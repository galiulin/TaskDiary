package dao;

import connection.ConnectionDB;
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

    public List<Task> getAllTasks() throws SQLException {
        List<Task> result = new ArrayList<>();
        Connection connection = connectionDB.getConnect();
        PreparedStatement statement = connection.prepareStatement("SELECT id, condition, description, date_add, dead_line, user_id FROM task;");
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            result.add(constructingTaskFromField(resultSet));
        }
        return result;
    }

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

}
