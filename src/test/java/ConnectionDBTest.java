import org.junit.jupiter.api.Disabled;
import connection.*;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

class ConnectionDBTest {

    @Test
    void connectionTest(){

    }

    @Test
    @Disabled("the test does not correspond to the canons")
    void createTable() {

        ConnectionDB connectionDB = ConnectionDBImpl.getInstance();
        connectionDB.getFromDB((connection -> {
            try {
                Statement statement = connection.createStatement();
                ResultSet set = statement.executeQuery("SELECT * FROM task");
                while (set.next()){
                    System.out.println(set.getInt("id"));
                    System.out.println(set.getString("condition"));
                    System.out.println(set.getString("description"));
                    System.out.println(set.getLong("date_add"));
                    Timestamp timestamp = Timestamp.valueOf(set.getString("date_add"));
                    System.out.println(timestamp);
                    System.out.println(set.getInt("user_id"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }));
    }
}