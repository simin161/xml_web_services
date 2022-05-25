package com.vinsguru.grpc.mail;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.vinsguru.grpc.model.User;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class MailService {

    public void sendVerificationEmail(User u, String siteURL)
            throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {
        String fromAddress = "dislinkt_24@yahoo.com";
        String senderName = "Dislinkt";
        String subject = "Please verify your registration";
        String content = "Dear user,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Dislinkt Team.";

        String host = "127.0.0.1";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(fromAddress, "bxyiibxcspxtcytc");

            }

        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(u.getEmail()));
        message.setSubject(subject);
        String verifyURL = siteURL + "/api/verifyAccount?code=" + u.getVerificationCode();
        content = content.replace("[[URL]]", verifyURL);
        message.setContent(content, "text/html");

        Transport.send(message);

    }

    public void sendForgottenPasswordEmail(String email, String newPassword)
            throws MessagingException, UnsupportedEncodingException, javax.mail.MessagingException {
        String fromAddress = "dislinkt_24@yahoo.com";
        String senderName = "Dislinkt";
        String subject = "Forgotten password";
        String content = "Dear user,<br>"
                + "This is your new password: [[newPassword]]<br>"
                + "Please update your password at your earliest convenience. <br>"
                + "Thank you,<br>"
                + "Dislinkt team.";

        content = content.replace("[[newPassword]]", newPassword);

        String host = "127.0.0.1";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(fromAddress, "bxyiibxcspxtcytc");

            }

        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setContent(content, "text/html");

        Transport.send(message);
    }

    public void sendPasswordlessLoginEmail(String email, String siteURL) throws javax.mail.MessagingException {
        String fromAddress = "dislinkt_24@yahoo.com";
        String senderName = "Dislinkt";
        String subject = "Your passwordless login is ready";
        String content = "Dear user,<br>"
                + "Please click the link below to login into your account:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">LOGIN</a></h3>"
                + "Thank you,<br>"
                + "Dislinkt Team.";

        content = content.replace("[[URL]]", "http://localhost:3000/profilePage/" + siteURL);

        String host = "127.0.0.1";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(fromAddress, "bxyiibxcspxtcytc");

            }

        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject);
        message.setContent(content, "text/html");

        Transport.send(message);

    }
}

