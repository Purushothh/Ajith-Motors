package sample;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class Mail {

    public static String getRandomNumber() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String resetPassword(String email) {
        String tempPassword=getRandomNumber();
        try {
            String host = "smtp.gmail.com";
            String username = "jfstestacc12@gmail.com";//Email address of sender (JFS)
            String password = "jfstest12";//Password of sender (JFS)
            String to = email;//Email address of customer
            String from = "jfstestacc12@gmail.com";
            String subject = "Password Reset";//Email subject
            boolean sessionDebug = false;

            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            //java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            message.setRecipients(Message.RecipientType.TO, address);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setContent("Someone requested a reset of your " +
                    "password for Ajith Motors.<br/>The following user account is " +
                    "associated with this email address:<br/><br/>" +
                    "Username: admin<br/>Temporary password: "+tempPassword+"<br/><br/>" +
                    "This temporary password will expire in 7 days.<br/>" +
                    "You should log in and choose a new password now. If someone else made this " +
                    "request, or if you have remembered your original password, and you no longer " +
                    "wish to change it, you may ignore this message and continue using your old " +
                    "password.", "text/html; charset=utf-8");

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return tempPassword;
    }
}
