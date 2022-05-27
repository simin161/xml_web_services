package com.example.followerservice.model;

import org.bson.types.ObjectId;

public class Follow {

    private ObjectId id;
    private String  personId;
    private String  followerId;
    private boolean approved;

    public Follow(ObjectId id, String personId, String followerId) {
        this.id = id;
        this.personId = personId;
        this.followerId = followerId;

    }

    public Follow(ObjectId id, String personId, String followerId, boolean approved) {
        this.id = id;
        this.personId = personId;
        this.followerId = followerId;
        this.approved = approved;
    }

    public Follow() {
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}
