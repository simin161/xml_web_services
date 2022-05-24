package com.grpc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class JobOffer {

    @Id
    private ObjectId id;
    private String position;
    private String jobDescription;
    private String dailyActivities;
    private String candidateRequirements;

    public JobOffer(String position, String jobDescription, String dailyActivities, String candidateRequirements){
        this.position = position;
        this.jobDescription = jobDescription;
        this.dailyActivities = dailyActivities;
        this.candidateRequirements = candidateRequirements;
    }

    public JobOffer(){
        this.position="";
        this.jobDescription="";
        this.dailyActivities="";
        this.candidateRequirements="";
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

    public String getCandidateRequirements() {
        return candidateRequirements;
    }

    public void setCandidateRequirements(String candidateRequirements) {
        this.candidateRequirements = candidateRequirements;
    }
}
