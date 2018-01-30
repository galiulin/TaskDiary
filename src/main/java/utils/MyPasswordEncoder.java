package utils;

import connection.ConnectionDB;
import connection.ConnectionDBImpl;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {

        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }

    private String getMD5(CharSequence rawPassword) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(rawPassword.toString().getBytes());
            result = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws SQLException {
        MyPasswordEncoder myPasswordEncoder = new MyPasswordEncoder();

        String line =(myPasswordEncoder.encode("admin"));

        ConnectionDB connectionDB = ConnectionDBImpl.getInstance();
        String res = connectionDB.getFromDB(connection -> {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO trash (value) VALUES (?)");
            statement.setString(1, line);
            statement.execute();
//            Statement statement = connection.createStatement();
//            ResultSet set = statement.executeQuery("SELECT value FROM trash WHERE id = 4");
//            while (set.next()) {
//               return set.getString("value");
//            }
            return null;
        });
        System.out.println(res);
        System.out.println(myPasswordEncoder.matches("admin", res));
    }
}
