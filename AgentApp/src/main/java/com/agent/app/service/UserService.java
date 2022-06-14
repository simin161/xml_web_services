package com.agent.app.service;

import com.agent.app.model.Authority;
import com.agent.app.model.User;
import com.agent.app.repository.AuthorityRepository;
import com.agent.app.repository.UserRepository;
import com.agent.app.security.TokenUtils;
import com.agent.app.utility.Validation;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TokenUtils tokenUtils;
    private String fromAddress = "dislinkt_team_23@yahoo.com";
    private String senderName = "Dislinkt";
    private String footer = "Thank you, <br> Dislinkt Team.";

    public String getUserApiToken(String email){
        User user = userRepository.findByEmail(email);
        String retVal = "";
        if(user != null)
            retVal = user.getApiToken();
        return retVal;
    }

    public boolean addUser(Map<String, String> message) {
        if(userRepository.findByEmail(message.get("email")) != null)
            return false;
    try {
        User user = new User();
        user.setPassword(passwordEncoder.encode(message.get("password")));
        user.setEmail(message.get("email"));
        user.setFirstName(message.get("firstName"));
        user.setLastName(message.get("lastName"));
        List<Authority> authorityList = new ArrayList<>();
        authorityList.add(authorityRepository.findById(1L).orElse(null));
        authorityList.add(authorityRepository.findById(4L).orElse(null));
        user.setAuthorities(authorityList);
        user.setVerificationCode(RandomString.make(64));
        userRepository.save(user);
        sendVerificationEmail(user);
    }catch(Exception e){
        return false;
    }
        return true;
    }

    public boolean checkIfAdmin(String email) {
        User user = userRepository.findByEmail(email);
        List<Authority> a = (List<Authority>) user.getAuthorities();
        for( Authority aa : a){
            if(aa.getName().equals("ROLE_ADMIN")){
                return true;
            }
        }
        return false;
    }

    public boolean updateApiToken(String email, String apiToken) {
        boolean retVal = false;
        User user = userRepository.findByEmail(email);
        if(user != null) {
            user.setApiToken(apiToken);
            userRepository.save(user);
            retVal = true;
        }
        return retVal;
    }

    public boolean passwordlessLogin(String email) {
        try {
           // if (Validation.validateEmail(email)) {

                User u = userRepository.findByEmail(email);
                if (u != null) {
                    String jwt = tokenUtils.generateToken(u.getEmail());
                    int expiresIn = tokenUtils.getExpiredIn();
                    String toAddress = u.getEmail();
                    String subject = "Your passwordless login is ready";
                    String content = "Dear user,<br>"
                            + "Please click the link below to login into your account:<br>"
                            + "<h3><a href=\"[[URL]]\" target=\"_self\">LOGIN</a></h3>"
                            + footer;

                    content = content.replace("[[URL]]", "http://localhost:8082/#/firstPage/" + jwt);
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message);

                    helper.setFrom(fromAddress, senderName);
                    helper.setTo(toAddress);
                    helper.setSubject(subject);
                    helper.setText(content, true);
                    mailSender.send(message);
                    return true;
                } else {
                    return false;
                }
            //}
        } catch (Exception e) {}
        return false;
    }
    public boolean forgottenPassword(String email) {
        try {
            // if (Validation.validateEmail(email)) {

            User u = userRepository.findByEmail(email);
            if (u != null) {
                String newPassword = String.valueOf(LocalDateTime.now().hashCode());
                newPassword = newPassword.replace('-', '0');
                newPassword = newPassword.substring(0, 6);
                String newPasswordForEmail = new String(newPassword);
                newPassword = passwordEncoder.encode(newPassword);
                String toAddress = u.getEmail();
                String subject = "Your new password is ready";
                String content = "Dear user,<br>"
                        + "Your password:<br>"
                        + "<p>" + newPasswordForEmail + "</p>"
                        + footer;

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                helper.setFrom(fromAddress, senderName);
                helper.setTo(toAddress);
                helper.setSubject(subject);
                helper.setText(content, true);
                mailSender.send(message);
                u.setPassword(newPassword);
                userRepository.save(u);
                return true;
            } else {
                return false;
            }
            //}
        } catch (Exception e) {}
        return false;
    }

    public void sendVerificationEmail(User user)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + footer;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = "http://localhost:8082/api/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public boolean verify(String code) {
        User user = userRepository.findByVerificationCode(code);
        return user == null || user.isActivated() ? false : activateAccount(user);
    }

    private boolean activateAccount(User user){
        user.setActivated(true);
        userRepository.save(user);
        return true;
    }

    public boolean changePassword(String email, String password) {
        User user = userRepository.findByEmail(email);
        if(user != null && Validation.validatePassword(password)){
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
