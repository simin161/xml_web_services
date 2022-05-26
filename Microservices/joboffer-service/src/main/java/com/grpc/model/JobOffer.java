package com.grpc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import java.util.Objects;

public class JobOffer {

    @Id
    private ObjectId id;
    private String position;
    private String companyName;
    private String jobDescription;
    private String dailyActivities;
    private String candidateRequirements;
    private String userAPItoken;

    public JobOffer(String position, String jobDescription, String dailyActivities, String candidateRequirements, String companyName, String userAPItoken){
        this.position = position;
        this.jobDescription = jobDescription;
        this.dailyActivities = dailyActivities;
        this.candidateRequirements = candidateRequirements;
        this.companyName = companyName;
        this.userAPItoken = userAPItoken;
    }

    public JobOffer(ObjectId id, String position, String jobDescription, String dailyActivities, String candidateRequirements, String companyName, String userAPItoken){
        this.id = id;
        this.position = position;
        this.jobDescription = jobDescription;
        this.dailyActivities = dailyActivities;
        this.candidateRequirements = candidateRequirements;
        this.companyName = companyName;
        this.userAPItoken = userAPItoken;
    }
    public JobOffer(){
        this.position="";
        this.jobDescription="";
        this.dailyActivities="";
        this.candidateRequirements="";
        this.companyName="";
        this.userAPItoken = "";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOffer jobOffer = (JobOffer) o;
        return Objects.equals(getId(), jobOffer.getId()) && Objects.equals(getPosition(), jobOffer.getPosition()) && Objects.equals(getJobDescription(), jobOffer.getJobDescription()) && Objects.equals(getDailyActivities(), jobOffer.getDailyActivities()) && Objects.equals(getCandidateRequirements(), jobOffer.getCandidateRequirements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPosition(), getJobDescription(), getDailyActivities(), getCandidateRequirements());
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getUserAPItoken() {
        return userAPItoken;
    }

    public void setUserAPItoken(String userAPItoken) {
        this.userAPItoken = userAPItoken;
    }
}
