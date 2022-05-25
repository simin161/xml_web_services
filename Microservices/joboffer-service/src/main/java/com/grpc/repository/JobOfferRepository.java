package com.grpc.repository;

import com.grpc.model.JobOffer;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobOfferRepository {

    final static Class<? extends List> docClass = new ArrayList<Document>().getClass();
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

    public List<JobOffer> searchJobOfferByParam(String paramName, String paramValue){
        FindIterable<Document> foundOffers;
        List<JobOffer> jobOffers = new ArrayList<>();
        if(paramName.isEmpty()){
            foundOffers = jobOfferCollection.find();
        }else{
            foundOffers = jobOfferCollection.find(Filters.eq(paramName, paramValue));
        }
        for(Document d : foundOffers){
            JobOffer offer = new JobOffer(d.getObjectId("_id"),d.getString("position"), d.getString("jobDescription"), d.getString("dailyActivities"), d.getString("candidateRequirements"), d.getString("companyName"));
            jobOffers.add(offer);
        }
        return jobOffers;
    }

    public List<JobOffer> getAllJobOffers(){
        List<JobOffer> allOffers = new ArrayList<>();
        FindIterable<Document> allOffersDocs = jobOfferCollection.find();
        for(Document d : allOffersDocs){
            JobOffer offer = new JobOffer(d.getObjectId("_id"),d.getString("position"), d.getString("jobDescription"), d.getString("dailyActivities"), d.getString("candidateRequirements"), d.getString("companyName"));
            allOffers.add(offer);
        }
        return allOffers;
    }

    public JobOffer findJobOfferById(ObjectId id){
        for(JobOffer jo : getAllJobOffers()){
            if(jo.getId().equals(id)){
                return jo;
            }
        }
        return new JobOffer();
    }

}
