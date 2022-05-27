package com.vinsguru.grpc.dto;

public class PostDto {

    private String idPost;
    private String email;
    private String text;
    private String pathToImage;
    private String link;
    private String date;
    private int numOfReactions;
    private int numOfComments;

    public PostDto(String idPost, String email, String text, String pathToImage, String link, String date) {
        this.idPost = idPost;
        this.email = email;
        this.text = text;
        this.pathToImage = pathToImage;
        this.link = link;
        this.date = date;
    }

    public PostDto(String idPost, String email, String text, String pathToImage, String link, String date, int numOfReactions, int numOfComments) {
        this.idPost = idPost;
        this.email = email;
        this.text = text;
        this.pathToImage = pathToImage;
        this.link = link;
        this.date = date;
        this.numOfReactions = numOfReactions;
        this.numOfComments = numOfComments;
    }

    public PostDto() {
    }

    public int getNumOfReactions() {
        return numOfReactions;
    }

    public void setNumOfReactions(int numOfReactions) {
        this.numOfReactions = numOfReactions;
    }

    public int getNumOfComments() {
        return numOfComments;
    }

    public void setNumOfComments(int numOfComments) {
        this.numOfComments = numOfComments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
