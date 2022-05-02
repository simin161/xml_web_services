package com.vinsguru.grpc.repository;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.vinsguru.grpc.model.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import proto.user.Input;

public class UserRepository {
    private static UserRepository instance = null;

    public static UserRepository getInstance(){
        if(instance == null)
            instance = new UserRepository();

        return instance;
    }

    private MongoClient mongoClient;
    private MongoDatabase usersDatabase;
    private MongoCollection<Document> usersCollection;

    private UserRepository(){
        String connectionString = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            usersDatabase = mongoClient.getDatabase("users");
            usersCollection = usersDatabase.getCollection("users");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(User user){
        Document userToSave = new Document("_id", new ObjectId());
        userToSave.append("firstName", user.getFirstName())
                .append("lastName", user.getLastName())
                .append("email", user.getEmail())
                .append("username", user.getUsername())
                .append("password", user.getPassword());
        usersCollection.insertOne(userToSave);
    }

    public User findUserByEmail(String email){
        Document foundUser = usersCollection.find(Filters.eq("email", email)).first();
        User retVal = null;
        if(foundUser != null){
            retVal = new User(foundUser.getString("firstName"), foundUser.getString("lastName"), foundUser.getString("username"),foundUser.getString("email"),
                    foundUser.getString("password"));
        }
        return retVal;
    }

    public List<User> getAllUsers(){

        FindIterable<Document> iterable = usersCollection.find();
        List<User> retVal = new ArrayList<User>();
        for(Document d : iterable){
           // Input i = Input.newBuilder().setFirstName(d.getString("firstName")).setLastName(d.getString("lastName")).setEmail(d.getString("email"))
           //         .setUsername(d.getString("username")).setPassword(d.getString("password")).build();
            User u = new User(d.getString("firstName"),d.getString("lastName"),d.getString("username"),d.getString("email"),d.getString("password"));
            retVal.add(u);
        }
        //return  usersCollection.find();
        return retVal;
    }
}
