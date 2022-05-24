package com.grpc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class JobOffer {

    @Id
    private ObjectId id;
    private String position;
    private String jobDescription;
    private String dailyActivities;
    private List<String> candidateRequirements;

    public JobOffer(String position, String jobDescription, String dailyActivities, List<String> candidateRequirements){
        this.position = position;
        this.jobDescription = jobDescription;
        this.dailyActivities = dailyActivities;
        this.candidateRequirements = candidateRequirements;
    }

    public JobOffer(){
        this.position="";
        this.jobDescription="";
        this.dailyActivities="";
        this.candidateRequirements= new ArrayList<>();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getDailyActivities() {
        return dailyActivities;
    }

    public void setDailyActivities(String dailyActivities) {
        this.dailyActivities = dailyActivities;
    }

    public List<String> getCandidateRequirements() {
        return candidateRequirements;
    }

    public void setCandidateRequirements(List<String> candidateRequirements) {
        this.candidateRequirements = candidateRequirements;
    }
}
