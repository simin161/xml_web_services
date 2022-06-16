package service;

import beans.User;
import beans.VerificationCode;
import beans.enums.UserType;
import dao.UserDao;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class UserService {

    private static MailService mailService = new MailService();

    public boolean register(User newUser, String siteURL){
        boolean returnValue = false;
        if(!checkExistanceOfEmail(newUser.getEmail())) {
            newUser.setUserType(UserType.REGULAR_USER);
            newUser.setActivated(false);
            VerificationCode vcode = new VerificationCode();
            vcode.setDateOfCreation(LocalDateTime.now());
            byte[] array = new byte[64];
            new Random().nextBytes(array);
            vcode.setCode(new String(array, StandardCharsets.UTF_8));
            newUser.setVerificationCode(vcode);
            try{
                UserDao.getInstance().addUser(newUser);
                UserDao.getInstance().save();
                mailService.sendVerificationEmail(newUser, siteURL);
                returnValue = true;
            }catch(Exception e){
                e.printStackTrace();
            }

        }
        return returnValue;
    }

    public static User findUserByEmail(String email){

        for(User user : UserDao.getInstance().getAllUsers()){
            if(user.getEmail().equals(email))
                return user;
        }

        return null;
    }

    public List<User> getAllUsers(){
        return UserDao.getInstance().getAllUsers();
    }

    public boolean checkExistanceOfEmail(String email) {
        boolean returnValue = false;
        for(User user : UserDao.getInstance().getAllUsers()) {
            if(email.equals(user.getEmail())) {
                returnValue = true;
                break;
            }
        }
        return returnValue;
    }

    public String verifyAccount(String code) {
        String retVal = "error";
        User user = findUserByCode(code);
        if(user != null){
            if(!user.getVerificationCode().getDateOfCreation().plusHours(1).isBefore(LocalDateTime.now())){
                UserDao.getInstance().activateUser(user);
                UserDao.getInstance().save();
                retVal = "verified";
            }
        }
        return retVal;
    }

    private User findUserByCode(String code) {
        List<User> allUsers = UserDao.getInstance().getAllUsers();
        for(User u : allUsers){
            if(u.getVerificationCode().getCode().equals(code)){
                return u;
            }
        }
        return null;
    }
}
