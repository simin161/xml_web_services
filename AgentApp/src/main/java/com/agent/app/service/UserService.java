package com.agent.app.service;

import com.agent.app.model.Authority;
import com.agent.app.model.User;
import com.agent.app.model.VerificationCode;
import com.agent.app.repository.AuthorityRepository;
import com.agent.app.repository.UserRepository;
import com.agent.app.security.DefaultMFATokenManager;
import com.agent.app.security.MfaTokenData;
import com.agent.app.security.TokenUtils;
import com.agent.app.utility.QRModel;
import com.agent.app.utility.Validation;
import dev.samstevens.totp.exceptions.QrGenerationException;
import com.agent.app.repository.VerificationCodeRepository;
import com.agent.app.security.TokenUtils;
import com.agent.app.utility.LoggingStrings;
import com.agent.app.utility.Validation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserService {
    private static final Object APP_NAME = "Agent App";
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private DefaultMFATokenManager mfaTokenManager;

    private String fromAddress = "dislinkt_team_23@yahoo.com";
    private String senderName = "Dislinkt";
    private String footer = "Thank you, <br> Dislinkt Team.";
    private String componentName = "|com.agent.app.service.UserService|";

    public String getUserApiToken(String email){
        User user = userRepository.findByEmail(email);
        String retVal = "";
        if(user != null)
            retVal = user.getApiToken();
        return retVal;
    }

    public boolean addUser(Map<String, String> message) {
        if (userRepository.findByEmail(message.get("email")) != null)
            return false;
        try {
            if (Validation.validateEmail(message.get("email"))) {
                if (userRepository.findByEmail(message.get("email")) != null)
                    return false;
                try {
                    if (Validation.validatePassword(message.get("password")) && Validation.validateName(message.get("firstName"))
                            && Validation.validateName(message.get("lastName"))) {
                        User user = new User();
                        user.setPassword(passwordEncoder.encode(message.get("password")));
                        user.setEmail(message.get("email"));
                        user.setFirstName(message.get("firstName"));
                        user.setLastName(message.get("lastName"));
                        List<Authority> authorityList = new ArrayList<>();
                        authorityList.add(authorityRepository.findById(1L).orElse(null));
                        authorityList.add(authorityRepository.findById(4L).orElse(null));
                        user.setAuthorities(authorityList);
                        user.setVerificationCode(saveVerificationCode());
                        userRepository.save(user);
                        sendVerificationEmail(user);
                        return true;
                    }
                } catch (Exception e) {
                    log.error(LoggingStrings.getAuthenticationFailed(componentName, message.get("email")));
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    private VerificationCode saveVerificationCode(){
        VerificationCode code = new VerificationCode(RandomString.make(64));
        verificationCodeRepository.save(code);
        return code;
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
        if(Validation.validateNonBrackets(apiToken)) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                user.setApiToken(apiToken);
                userRepository.save(user);
                retVal = true;
            }
        }
        return retVal;
    }

    public boolean passwordlessLogin(String email) {
        try {
            if (Validation.validateEmail(email)) {
                User u = userRepository.findByEmail(email);
                if (u != null) {
                    String jwt = tokenUtils.generateToken(u.getEmail());
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
            }
        } catch (Exception e) {
            log.error(LoggingStrings.getAuthenticationFailed(componentName, email));
        }
        return false;
    }
    public boolean forgottenPassword(String email) {
        try {
            if (Validation.validateEmail(email)) {

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
            }
        } catch (Exception e) {
            log.error(LoggingStrings.getAuthenticationFailed(componentName, e.toString()));
        }
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
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code);
        if(verificationCode.getDateOfCreation().isBefore(LocalDateTime.now())) {
            User user = userRepository.findByVerificationCode(verificationCode);
            return user == null || user.isActivated() ? false : activateAccount(user);
        }
        log.warn(LocalDateTime.now().toString() + componentName + "User with verification code " + code + " failed to verify");
        return false;
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

    public MfaTokenData mfaSetup(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("User not found");
        }
        return new MfaTokenData(mfaTokenManager.getQRCode(user.getSecret()), user.getSecret());
    }

    public QRModel enable2FA(String email, QRModel model){
            try {
                MfaTokenData mfaData = mfaSetup(email);
                model.setQrCode(mfaData.getQrCode());
                model.setQrCodeKey(mfaData.getMfaCode());
                model.setQrCodeSetup("true");
                return model;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return model;
    }

    public boolean resendVerificationCode(String email) {
        boolean retVal = false;
        try {
            if(Validation.validateEmail(email)) {
                User user = userRepository.findByEmail(email);
                if (user != null) {
                    VerificationCode code = user.getVerificationCode();
                    code.setDateOfCreation(LocalDateTime.now());
                    verificationCodeRepository.save(code);
                    sendVerificationEmail(user);
                    retVal = true;
                }else{
                    log.info(LocalDateTime.now().toString() + componentName + "User " + email + " not found");
                }
            }else{
                log.info(LocalDateTime.now().toString() + componentName + "User attempted to resend email with invalid input: " + email);
            }
        }catch(Exception e){
            log.error(e.toString());
        }
        return retVal;
    }
}
