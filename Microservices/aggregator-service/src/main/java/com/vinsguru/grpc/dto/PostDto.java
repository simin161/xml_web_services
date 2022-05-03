package com.vinsguru.grpc.dto;

public class PostDto {

    private String email;
    private String text;
    private String pathToImage;
    private String link;

    public PostDto(String email, String text, String pathToImage, String link) {
        this.email = email;
        this.text = text;
        this.pathToImage = pathToImage;
        this.link = link;
    }

    public PostDto() {
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
}
