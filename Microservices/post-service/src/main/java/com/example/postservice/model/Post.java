package com.example.postservice.model;

import org.bson.types.ObjectId;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

public class Post {
    @Id
    private ObjectId id;
    private String usersId;
    private String text;
    private String pathToImage;
    private String link;
    private List<Comment> comments;
    private List<Reaction> reactions;
    private Date date;

    public Post(ObjectId id, String usersId, String text, String pathToImage, String link, Date date) {
        this.id = id;
        this.usersId = usersId;
        this.text = text;
        this.pathToImage = pathToImage;
        this.link = link;
        this.date = date;
    }

    public Post(String usersId, String text, String pathToImage, String link, List<Comment> comments, List<Reaction> reactions, Date date) {
        this.usersId = usersId;
        this.text = text;
        this.pathToImage = pathToImage;
        this.link = link;
        this.comments = comments;
        this.reactions = reactions;
        this.date = date;
    }

    public Post() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPathToImage() {
        return pathToImage;
    }

    public void setPathToImage(String pathToImage) {
        this.pathToImage = pathToImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }
}
