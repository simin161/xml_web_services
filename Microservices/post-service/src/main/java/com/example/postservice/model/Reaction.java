package com.example.postservice.model;

import org.bson.types.ObjectId;

public class Reaction {
    private ObjectId idReaction;
    private String usersId;
    private ReactionType reaction;

    public Reaction(String usersId, ReactionType reaction) {
        this.usersId = usersId;
        this.reaction = reaction;
    }

    public Reaction(ObjectId idReaction, String usersId, ReactionType reaction) {
        this.idReaction = idReaction;
        this.usersId = usersId;
        this.reaction = reaction;
    }

    public Reaction() {
    }

    public ObjectId getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(ObjectId idReaction) {
        this.idReaction = idReaction;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public ReactionType getReaction() {
        return reaction;
    }

    public void setReaction(ReactionType reaction) {
        this.reaction = reaction;
    }
}
