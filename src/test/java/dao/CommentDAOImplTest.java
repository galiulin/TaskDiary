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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment test = commentDAO.addComment(2, "test", 3, timestamp);
        System.out.println(test);

        Connection connect = ConnectionDBImpl.getInstance().getConnect();
        try {
            PreparedStatement statement = connect.prepareStatement("DELETE FROM comment WHERE id = ?");
            statement.setInt(1, test.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


//    @Test
    void getAllComments() {
        List<Comment> list = commentDAO.getAllComments();
        list.stream().forEach(System.out::println);
    }

//    @Test
    void getCommentByUserId() {
        List<Comment> list = commentDAO.getCommentByUserId(3);
        list.stream().forEach(System.out::println);
    }

//    @Test
    void getCommentByTaskId() {
        List<Comment> list = commentDAO.getCommentByTaskId(3);
        list.stream().forEach(System.out::println);
    }
}