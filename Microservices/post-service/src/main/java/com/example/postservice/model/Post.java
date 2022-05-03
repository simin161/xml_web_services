package com.example.postservice.model;

import javax.persistence.Id;
import java.util.List;

public class Post {
    @Id
    private String id;
    private String usersId;
    private String text;
    private String pathToImage;
    private String link;
    private List<Comment> comments;

    public Post(String usersId, String text, String pathToImage, String link,List<Comment> comments) {
        this.usersId = usersId;
        this.text = text;
        this.pathToImage = pathToImage;
        this.link = link;
        this.comments = comments;
    }

    public Post() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
