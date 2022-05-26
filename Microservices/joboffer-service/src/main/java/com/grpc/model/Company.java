package com.grpc.model;

public class Company {

    private String companyName;
    private String ownerEmail;

    public Company(){
        this.companyName="";
        this.ownerEmail="";
    }

    public Company(String companyName, String ownerEmail){
        this.companyName = companyName;
        this.ownerEmail = ownerEmail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
}
