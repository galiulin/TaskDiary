package utils.sendlog;

import common.Logged;
import org.apache.log4j.Logger;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {
    @Logged
    private static Logger logger;

    private final static String username = "vecjhrf2@gmail.com";
    private final static String password = "QWEqwe123";
    private static Properties properties = new Properties();

    public static void main(String[] args) {
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

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

            Transport.send(message);

            logger.trace("было отправлено сообщение по электронной почте");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
