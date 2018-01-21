package dao;

import connection.ConnectionDB;
import connection.ConnectionDBImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Comment;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommentDAOImplTest {
    private static CommentDAO commentDAO;

//    @BeforeAll
    static void setup() throws NoSuchFieldException, SQLException {
        ConnectionDBTest test = new ConnectionDBTest();
        test.setup();
        commentDAO = new CommentDAOImpl(ConnectionDBImpl.getInstance());
    }

//    @BeforeEach
    void setup1(){
    }

//    @Test
    void addComment() {

        Connection connect = ConnectionDBImpl.getInstance().getConnect();
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            Comment test = commentDAO.addComment(2, "test", 3, timestamp);
            System.out.println(test);
            PreparedStatement statement = connect.prepareStatement("DELETE FROM comment WHERE id = ?");
            statement.setInt(1, test.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


//    @Test
    void getAllComments() {
        List<Comment> list = null;
        try {
            list = commentDAO.getAllComments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.stream().forEach(System.out::println);
    }

//    @Test
    void getCommentByUserId() {
        List<Comment> list = null;
        try {
            list = commentDAO.getCommentByUserId(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.stream().forEach(System.out::println);
    }

//    @Test
    void getCommentByTaskId() {
        List<Comment> list = null;
        try {
            list = commentDAO.getCommentByTaskId(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.stream().forEach(System.out::println);
    }
}