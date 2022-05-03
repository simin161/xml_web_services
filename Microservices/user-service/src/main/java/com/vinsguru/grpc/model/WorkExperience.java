package com.vinsguru.grpc.model;

import java.util.Date;

public class WorkExperience {
    private String workPlace;
    private String workTitle;
    private Date from;
    private Date to;

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

    public WorkExperience(String workPlace, String workTitle, Date from, Date to) {
        this.workPlace = workPlace;
        this.workTitle = workTitle;
        this.from = from;
        this.to = to;
    }

    public WorkExperience() {
    }
}
