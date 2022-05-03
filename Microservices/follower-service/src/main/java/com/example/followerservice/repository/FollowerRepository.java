package com.example.followerservice.repository;

import com.example.followerservice.model.Follow;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
}
