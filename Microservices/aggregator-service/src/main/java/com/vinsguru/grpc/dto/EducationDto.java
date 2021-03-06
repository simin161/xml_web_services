package com.vinsguru.grpc.dto;

import java.util.Date;

public class EducationDto {
    private String email;
    private String school;
    private String degree;
    private String fieldOfStudy;
    private String from;
    private String to;
    private String id;

    public EducationDto(String school, String degree, String fieldOfStudy, String from, String to,String id) {
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.from = from;
        this.to = to;
        this.id=id;
    }

    public EducationDto(String email,String school, String degree, String fieldOfStudy, String from, String to,String id) {
        this.email = email;
        this.school = school;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.from = from;
        this.to = to;
        this.id=id;
    }

    public EducationDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
