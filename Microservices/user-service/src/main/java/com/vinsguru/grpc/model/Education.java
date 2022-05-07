package com.vinsguru.grpc.model;

import org.bson.types.ObjectId;

import java.util.Date;

public class Education {
    private ObjectId idEducation;
    private String school;
    private String degree;
    private String fieldOfStudy;
    private Date from;
    private Date to;

    public Education() {
    }

    public Education(ObjectId idEducation, String school, String degree, String fieldOfStudy, Date from, Date to) {
        this.idEducation = idEducation;
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.from = from;
        this.to = to;
    }

    public ObjectId getIdEducation() {
        return idEducation;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
