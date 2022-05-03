package com.vinsguru.grpc.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import com.vinsguru.grpc.model.Education;
import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.model.WorkExperience;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import proto.user.InputUpdateWorkExperience;

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
                .append("password", user.getPassword())
                .append("privateProfile",user.isPrivateProfile())
                .append("birthday",user.getBirthday())
                .append("gender",user.getGender())
                .append("phone",user.getPhone())
                .append("biography",user.getBiography())
                .append("interests",user.getInterests())
                .append("skills",user.getSkills())
                .append("educations",user.getEducations())
                .append("experiences",user.getEducations());
        usersCollection.insertOne(userToSave);
    }


    public User findUserByEmail(String email){
        Document foundUser = usersCollection.find(Filters.eq("email", email)).first();;
        User retVal = null;
        if(foundUser != null){
            retVal = new User(foundUser.getString("firstName"), foundUser.getString("lastName"), foundUser.getString("username"),foundUser.getString("email"),
                    foundUser.getString("password"),foundUser.getBoolean("privateProfile"), foundUser.getDate("birthday"),foundUser.getString("gender"),
                    foundUser.getString("phone"),foundUser.getString("biography"),foundUser.getString("interests"),foundUser.getString("skills"),null,null);

    public List<User> findUserByParam(String paramName, String paramValue){
        FindIterable<Document> foundUsers = usersCollection.find(Filters.eq(paramName, paramValue));
        List<User> retVal = new ArrayList<>();
        for(Document foundUser : foundUsers)
        {

                User u = new User(foundUser.getString("firstName"), foundUser.getString("lastName"), foundUser.getString("username"), foundUser.getString("email"),
                    foundUser.getString("password"));
                retVal.add(u);

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

    public void update(User user){
        Document query = new Document().append("email",  user.getEmail());
        Bson updates = Updates.combine(
                Updates.set("firstName", user.getFirstName()),
                Updates.set("lastName", user.getLastName()),
                Updates.set("privateProfile", user.isPrivateProfile()),
                Updates.set("birthday", user.getBirthday()),
                Updates.set("gender", user.getGender()),
                Updates.set("phone", user.getPhone()),
                Updates.set("biography", user.getBiography()),
                Updates.set("interests", user.getInterests()),
                Updates.set("skills", user.getSkills())
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        usersCollection.updateOne(query, updates, options);
    }
    public void updateEducation(String email,Education education){
        Document query = new Document().append("email", email);
        Bson updates = Updates.combine(
                Updates.addToSet("educations", Arrays.asList(education.getSchool(),education.getDegree(),education.getFieldOfStudy(),education.getFrom(),
                        education.getTo()))
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        usersCollection.updateOne(query, updates, options);
    }

    public List<Education> getEducationsUserByEmail(String email) {
        Document foundUser = usersCollection.find(Filters.eq("email", email)).first();;
        return foundUser.getList("educations",Education.class);
    }

    public void updateWorkExperience(String email,WorkExperience workExperience) {
        Document query = new Document().append("email", email);
        Bson updates = Updates.combine(
                Updates.addToSet("experiences", Arrays.asList(workExperience.getWorkPlace(),workExperience.getWorkTitle(),workExperience.getFrom(),
                        workExperience.getTo()))
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        usersCollection.updateOne(query, updates, options);
    }
}
