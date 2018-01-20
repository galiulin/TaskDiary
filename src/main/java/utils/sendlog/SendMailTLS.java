package utils.sendlog;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {
    final static String username = "vecjhrf2@gmail.com";
    final static String password = "QWEqwe123";
    static Properties properties = new Properties();
    static {

    }

    public static void main(String[] args) {
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.post", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Vecjhrf2@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("NoNNamedN@yandex.ru"));
            message.setSubject("Testing Subject");
            message.setText("this is a test");

            Transport.send(message); //fixme дальше работа не продолжается

            System.out.println("done");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
