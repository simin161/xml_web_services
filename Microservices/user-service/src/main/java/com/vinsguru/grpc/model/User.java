package com.vinsguru.grpc.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Objects;


public class User {
    @Id
    private ObjectId id;
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
    private String userAPItoken;


    public User(String firstName, String lastName, String username, String email, String password, String gender, Date birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.privateProfile = false;
        this.birthday = birthDate;
        this.gender = gender;
        this.educations=new ArrayList<>();
        this.experinces=new ArrayList<>();
        this.userAPItoken="";
    }

    public User(String firstName, String lastName, String username, String email, String password, String gender, Date birthDate, String verificationCode, boolean isActivated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.privateProfile = false;
        this.birthday = birthDate;
        this.gender = gender;
        this.educations=new ArrayList<>();
        this.experinces=new ArrayList<>();
        this.userAPItoken="";
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public void setVerificationCode(String verificationCode){
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode(){
        return verificationCode;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivateProfile() {
        return privateProfile;
    }

    public void setPrivateProfile(boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public User() {
    }

    public User(String firstName, String lastName, String username, String email, String password, String verificationCode, boolean isActivated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.privateProfile = false;
        this.birthday = new Date();
        this.gender = "";
        this.phone = "";
        this.biography = "";
        this.interests = "";
        this.skills = "";
        this.educations=new ArrayList<>();
        this.experinces=new ArrayList<>();
        this.verificationCode = verificationCode;
        this.isActivated = isActivated;
        this.userAPItoken = "";
    }

    public User(String email, String verificationCode, boolean isActivated){
        this.email=email;
        this.isActivated= isActivated;
        this.verificationCode=verificationCode;
        this.userAPItoken = "";
    }

    public User(ObjectId id,String firstName, String lastName, String username, String email, String password, boolean privateProfile,
                Date birthday, String gender, String phone, String biography, String interests, String skills,List<Education> educations,
                List<WorkExperience> experinces) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.privateProfile = privateProfile;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.biography = biography;
        this.interests = interests;
        this.skills = skills;
        this.educations=educations;
        this.experinces=experinces;
        this.userAPItoken = "";
    }

    public List<WorkExperience> getExperinces() {
        return experinces;
    }

    public void setExperinces(List<WorkExperience> experinces) {
        this.experinces = experinces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && username.equals(user.username) && email.equals(user.email) && password.equals(user.password);
    }

    public String getUserAPItoken() {
        return userAPItoken;
    }

    public void setUserAPItoken(String userAPItoken) {
        this.userAPItoken = userAPItoken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, username, email, password);
    }
}
