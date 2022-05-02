package com.vinsguru.grpc.repository;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.vinsguru.grpc.model.User;
import org.bson.Document;
import org.bson.types.ObjectId;

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

    public List<Document> getAllUsers(){
        return (List<Document>) usersCollection.find();
    }
}
