package com.vinsguru.grpc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class MailService {

    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String siteURL, String token)
            throws MessagingException, UnsupportedEncodingException {
        String fromAddress = "findsfishy@gmail.com";
        String senderName = "Fishy Finds";
        String subject = "Please verify your registration";
        String content = "Dear user,<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Fishy Finds.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(email);
        helper.setSubject(subject);

        String verifyURL = siteURL + "/api/verifyCustomerAccount?code=" + token;

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public void sendForgottenPasswordEmail(String email, String newPassword)
            throws MessagingException, UnsupportedEncodingException {
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
}

