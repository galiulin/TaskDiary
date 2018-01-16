package dao;

import connection.ConnectionDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Task;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDAOImplTest {

    private TaskDAOImpl taskDAO;

    @BeforeEach
    void setUp() {
        taskDAO = new TaskDAOImpl(ConnectionDB.getInstance());
    }

    @Test
    void getAllTasks() throws SQLException {
        List<Task> list = taskDAO.getAllTasks();
        list.forEach(System.out::println);
    }
}