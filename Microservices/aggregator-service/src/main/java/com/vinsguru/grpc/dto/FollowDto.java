package com.vinsguru.grpc.dto;

public class FollowDto {

    private String personEmail;
    private String followerEmail;


    public FollowDto(String personEmail, String followerEmail) {
        this.personEmail = personEmail;
        this.followerEmail = followerEmail;
    }


    public FollowDto() {
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getFollowerEmail() {
        return followerEmail;
    }

    public void setFollowerEmail(String followerEmail) {
        this.followerEmail = followerEmail;
    }
}
