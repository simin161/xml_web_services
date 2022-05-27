package com.grpc.repository;

import com.grpc.model.JobOffer;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
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
                .append("candidateRequirements", jobOffer.getCandidateRequirements())
                .append("userAPItoken", jobOffer.getUserAPItoken());
        jobOfferCollection.insertOne(jobOfferToSave);
    }

    public List<JobOffer> searchJobOfferByParam(String paramName, String paramValue){
        FindIterable<Document> foundOffers;
        List<JobOffer> jobOffers = new ArrayList<>();
        if(paramValue.isEmpty()){
            foundOffers = jobOfferCollection.find();
        }else{
            jobOfferCollection.createIndex(Indexes.text("position"));
            if(paramValue.split(" ").length == 1){
                Bson filter = Filters.text(paramValue);
                foundOffers = jobOfferCollection.find(filter);
            }else{
                Bson filter = Filters.text("\"" + paramValue + "\"");
                foundOffers = jobOfferCollection.find(filter);
            }

            //foundOffers = jobOfferCollection.find(Filters.eq(paramName, paramValue));
        }
        for(Document d : foundOffers){
            JobOffer offer = new JobOffer(d.getObjectId("_id"),d.getString("position"), d.getString("jobDescription"), d.getString("dailyActivities"), d.getString("candidateRequirements"), d.getString("companyName"), d.getString("userAPItoken"));
            jobOffers.add(offer);
        }
        return jobOffers;
    }

    public List<JobOffer> getAllJobOffers(){
        List<JobOffer> allOffers = new ArrayList<>();
        FindIterable<Document> allOffersDocs = jobOfferCollection.find();
        for(Document d : allOffersDocs){
            JobOffer offer = new JobOffer(d.getObjectId("_id"),d.getString("position"), d.getString("jobDescription"), d.getString("dailyActivities"), d.getString("candidateRequirements"), d.getString("companyName"), d.getString("userAPItoken"));
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

    public void updateJobOfferAPIToken(JobOffer jobOffer){
        Document query = new Document().append("_id", jobOffer.getId());
        Bson updates = Updates.combine(
                Updates.set("userAPItoken", jobOffer.getUserAPItoken())
        );
        UpdateOptions options = new UpdateOptions().upsert(true);
        jobOfferCollection.updateOne(query, updates, options);
    }

}
