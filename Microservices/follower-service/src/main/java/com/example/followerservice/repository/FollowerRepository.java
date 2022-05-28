package com.example.followerservice.repository;

import com.example.followerservice.model.Follow;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
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
                .append("followerId", follow.getFollowerId())
                .append("approved",follow.isApproved());
        followersCollection.insertOne(followToSave);
    }

    public List<Follow> findPersonsFollowers(String personsId){
        FindIterable<Document> foundUsers = followersCollection.find(Filters.eq("personId", personsId));
        List<Follow> retVal = new ArrayList<>();
        for(Document foundUser : foundUsers)
        {
            if(foundUser.getBoolean("approved")){
                Follow u = new Follow(null,foundUser.getString("personId"),foundUser.getString("followerId"));
                retVal.add(u);
            }
        }
        return retVal;
    }

    public List<Follow> findPersonsFollowings(String followerId){
        FindIterable<Document> foundUsers = followersCollection.find(Filters.eq("followerId", followerId));
        List<Follow> retVal = new ArrayList<>();
        for(Document foundUser : foundUsers)
        {
            if(foundUser.getBoolean("approved")) {
                Follow u = new Follow(null, foundUser.getString("personId"), foundUser.getString("followerId"));
                retVal.add(u);
            }
        }
        return retVal;
    }

    public boolean checkIfUserIsFollowingOtherUser(String personalId, String followersId) {
        List<Follow> followers=findPersonsFollowers(personalId);
        for(Follow follow: followers){
            if(follow.getFollowerId().equals(followersId));
             return true;
        }
        return false;
    }

    public void removeFollow(String personalId, String followersId) {
        Document followerToDelete=new Document();
        FindIterable<Document> foundFollowers = followersCollection.find(Filters.eq("personId", personalId));

        for(Document doc : foundFollowers){
            if(doc.get("followerId").toString().equals(followersId)){
                followerToDelete= doc;
                break;
            }
        }
        followersCollection.deleteOne(new Document("_id", new ObjectId(followerToDelete.getObjectId("_id").toString())));
    }

    public List<Follow> findRequests(String personalId) {
        FindIterable<Document> foundUsers = followersCollection.find(Filters.eq("personId", personalId));
        List<Follow> retVal = new ArrayList<>();
        for(Document foundUser : foundUsers)
        {
            if(!foundUser.getBoolean("approved")){
                Follow u = new Follow(null,foundUser.getString("personId"),foundUser.getString("followerId"));
                retVal.add(u);
            }
        }
        return retVal;
    }

    public boolean answerFollowRequest(boolean approved, String followerEmail, String personEmail) {
        if(approved){

            Document requestToApprove=new Document();
            FindIterable<Document> foundFollowers = followersCollection.find(Filters.eq("personId", personEmail));
            for(Document doc : foundFollowers){
                if(doc.get("followerId").toString().equals(followerEmail)){
                    requestToApprove= doc;
                    break;
                }
            }
            Bson updates = Updates.combine(
                    Updates.set("approved", approved)
            );
            UpdateOptions options = new UpdateOptions().upsert(true);
            followersCollection.updateOne(requestToApprove, updates, options);
            return true;
        }else {
            System.out.println("person"+personEmail);
            System.out.println("follower"+followerEmail);
            removeFollow(personEmail,followerEmail);
            return true;
        }

    }
}
