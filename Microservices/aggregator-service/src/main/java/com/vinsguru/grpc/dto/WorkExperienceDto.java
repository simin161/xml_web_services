package com.vinsguru.grpc.dto;

public class WorkExperienceDto {

    private String workPlace;
    private String workTitle;
    private String from;
    private String to;
    private String email;
    public WorkExperienceDto() {
    }

    public WorkExperienceDto(String workPlace, String workTitle, String from, String to,String email) {
        this.workPlace = workPlace;
        this.workTitle = workTitle;
        this.from = from;
        this.to = to;
        this.email=email;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkTitle() {
        return workTitle;
    }

    public void setWorkTitle(String workTitle) {
        this.workTitle = workTitle;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
