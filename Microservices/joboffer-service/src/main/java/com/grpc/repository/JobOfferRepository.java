package com.grpc.repository;

import com.grpc.model.JobOffer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class JobOfferRepository {

    private static JobOfferRepository instance = null;
    private MongoClient mongoClient;
    private MongoDatabase jobOfferDatabase;
    private MongoCollection<Document> jobOfferCollection;

    public static JobOfferRepository getInstance(){
        if(instance == null){
            instance = new JobOfferRepository();
        }
        return instance;
    }

    private JobOfferRepository(){
        String connectionString = "mongodb://localhost:27017";
        try{
            mongoClient = MongoClients.create(connectionString);
            List<Document> databases= mongoClient.listDatabases().into(new ArrayList<>());
            databases.forEach(db -> System.out.println(db.toJson()));
            jobOfferDatabase = mongoClient.getDatabase("joboffers");
            jobOfferCollection = jobOfferDatabase.getCollection("joboffers");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createJobOffer(JobOffer jobOffer){
        Document jobOfferToSave = new Document("_id", new ObjectId());
        jobOfferToSave.append("position", jobOffer.getPosition())
                .append("jobDescription", jobOffer.getJobDescription())
                .append("dailyActivities", jobOffer.getDailyActivities())
                .append("candidateRequirements", jobOffer.getCandidateRequirements());
        jobOfferCollection.insertOne(jobOfferToSave);
    }

    

}
