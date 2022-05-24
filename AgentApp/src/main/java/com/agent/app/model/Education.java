package com.agent.app.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

public class Education {
    private String idEducation;
    private String school;
    private String degree;
    private String fieldOfStudy;
    private Date from;
    private Date to;
}
