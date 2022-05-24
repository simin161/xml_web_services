package com.agent.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Document
public class User {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private boolean privateProfile;
    private Date birthday;
    private String gender;
    private String phone;
    private String biography;
    private String interests;
    private String skills;
    private List<Education> educations;
    public List<WorkExperience> experinces;
    private String verificationCode;
    private boolean isActivated;

}
