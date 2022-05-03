package com.example.postservice.model;

import org.bson.types.ObjectId;

import javax.persistence.Id;

public class Comment {

    @Id
    private ObjectId idComment;
    private String text;
    private String commentatorsId;

    public Comment(String text, String commentatorsId) {
        this.text = text;
        this.commentatorsId = commentatorsId;
    }

    public Comment() {
    }

    public ObjectId getIdComment() {
        return idComment;
    }

    public void setIdComment(ObjectId idComment) {
        this.idComment = idComment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentatorsId() {
        return commentatorsId;
    }

    public void setCommentatorsId(String commentatorsId) {
        this.commentatorsId = commentatorsId;
    }
}
