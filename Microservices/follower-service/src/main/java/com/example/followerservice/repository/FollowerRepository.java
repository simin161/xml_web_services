package com.example.followerservice.repository;

import com.example.followerservice.model.Follow;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class FollowerRepository {


    private static FollowerRepository instance = null;

    public static FollowerRepository getInstance(){
        if(instance == null)
            instance = new FollowerRepository();

        return instance;
    }

    private MongoClient mongoClient;
    private MongoDatabase followersDatabase;
    private MongoCollection<Document> followersCollection;

    private FollowerRepository(){
        String connectionString = "mongodb://localhost:27017";
        try {
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases = mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            followersDatabase = mongoClient.getDatabase("followers");
            followersCollection = followersDatabase.getCollection("followers");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(Follow follow){
        Document followToSave = new Document("_id", new ObjectId());
        followToSave.append("personId", follow.getPersonId())
                .append("followerId", follow.getFollowerId());
        followersCollection.insertOne(followToSave);
    }

    public List<Follow> findPersonsFollowers(String personsId){
        FindIterable<Document> foundUsers = followersCollection.find(Filters.eq("personId", personsId));
        List<Follow> retVal = new ArrayList<>();
        for(Document foundUser : foundUsers)
        {
            Follow u = new Follow(null,foundUser.getString("personId"),foundUser.getString("followerId"));
            retVal.add(u);
        }
        return retVal;
    }

    public List<Follow> findPersonsFollowings(String followerId){
        FindIterable<Document> foundUsers = followersCollection.find(Filters.eq("followerId", followerId));
        List<Follow> retVal = new ArrayList<>();
        for(Document foundUser : foundUsers)
        {
            Follow u = new Follow(null,foundUser.getString("personId"),foundUser.getString("followerId"));
            retVal.add(u);
        }
        return retVal;
    }

}
