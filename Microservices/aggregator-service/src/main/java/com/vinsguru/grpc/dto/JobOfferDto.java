package com.vinsguru.grpc.dto;

import java.util.ArrayList;
import java.util.List;

public class JobOfferDto {

    private String position;
    private String jobDescription;
    private String dailyActivities;
    private String companyName;
    private String candidateRequirements;

    public JobOfferDto(String position, String jobDescription, String dailyActivities, String candidateRequirements, String companyName){
        this.position = position;
        this.jobDescription = jobDescription;
        this.dailyActivities = dailyActivities;
        this.candidateRequirements = candidateRequirements;
        this.companyName = companyName;
    }

    public JobOfferDto(){
        this.position="";
        this.jobDescription="";
        this.dailyActivities="";
        this.candidateRequirements= "";
        this.companyName="";
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
