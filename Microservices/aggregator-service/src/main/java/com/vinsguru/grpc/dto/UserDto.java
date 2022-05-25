package com.vinsguru.grpc.dto;

public class UserDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean privateProfile;
    private String birthday;
    private String gender;
    private String phone;
    private String biography;
    private String interests;
    private String skills;
    private boolean isEnabled;
    private String userAPIToken;

    public UserDto(String firstName, String lastName, String username, String email, boolean privateProfile, String birthday, String gender, String phone, String biography, String interests, String skills, String userAPIToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.privateProfile = privateProfile;
        this.birthday = birthday;
        this.gender = gender;
        this.phone = phone;
        this.biography = biography;
        this.interests = interests;
        this.skills = skills;
        this.userAPIToken= userAPIToken;
    }

    public UserDto(String username,String firstName, String lastName,String email ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean isEnabled(){
        return isEnabled;
    }

    public void setEnabled(boolean enabled){
        this.isEnabled = enabled;
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

    public boolean isPrivateProfile() {
        return privateProfile;
    }

    public void setPrivateProfile(boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public UserDto() {
    }

    public String getUserAPIToken() {
        return userAPIToken;
    }

    public void setUserAPIToken(String userAPIToken) {
        this.userAPIToken = userAPIToken;
    }
}
