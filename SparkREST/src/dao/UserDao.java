package dao;

import beans.User;
import beans.VerificationCode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDao {
    private ArrayList<User> allUsers;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd").setPrettyPrinting().create();

    private static UserDao instance;
    public static UserDao getInstance() {
        if(instance == null) {
            instance =  new UserDao();
        }
        return instance;
    }

    public UserDao() {
        load();
    }

    public ArrayList<User> getAllUsers(){
        return allUsers;
    }

    private void load() {
        try {
            Reader reader = Files.newBufferedReader(Paths.get("data/users.json"));
            allUsers = new ArrayList<User>(Arrays.asList(gson.fromJson(reader, User[].class)));
            reader.close();

        }catch(Exception e) {
            allUsers = new ArrayList<User>();
        }
    }

    public void save() {
        try {
            Writer writer;
            writer = Files.newBufferedWriter(Paths.get("data/users.json"));
            gson.toJson(allUsers, writer);
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

    public void addUser(User newUser) {
        allUsers.add(newUser);
    }

    public void activateUser(User userToActivate){
        for(User u : allUsers){
            if(u.getEmail().equals(userToActivate.getEmail())){
                u.setActivated(true);
                u.setVerificationCode(new VerificationCode());
            }
        }
    }
}
