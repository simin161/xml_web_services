package service;

import beans.User;
import beans.enums.UserType;
import dao.UserDao;

import java.util.List;

public class UserService {

    public boolean register(User newUser){
        boolean returnValue = false;
        if(!checkExistanceOfEmail(newUser.getEmail())) {
            newUser.setUserType(UserType.REGULAR_USER);
            UserDao.getInstance().addUser(newUser);
            UserDao.getInstance().save();
            returnValue = true;
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
}
