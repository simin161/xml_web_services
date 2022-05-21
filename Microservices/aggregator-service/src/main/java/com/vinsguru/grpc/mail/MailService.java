package com.vinsguru.grpc.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordlessLoginEmail(String email, String jwt, int expiresIn, String siteURL) throws MessagingException, UnsupportedEncodingException {

        String fromAddress = "dislinkt_24@yahoo.com";
        String senderName = "Dislinkt";
        String subject = "Your passwordless login is ready";
        String content = "Dear user,<br>"
                + "Please click the link below to login into your account:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Dislinkt Team.";

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
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(email);
        helper.setSubject(subject);

        String passwordlessURL =  "http://localhost:3000/profilePage";

        content = content.replace("[[URL]]", passwordlessURL);

        helper.setText(content, true);

        mailSender.send(message);

    }
}
