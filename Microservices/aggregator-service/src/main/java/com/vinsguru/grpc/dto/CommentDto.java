package com.vinsguru.grpc.dto;

public class CommentDto {

    private String postId;
    private String text;
    private String commentatorsEmail;

    public CommentDto(String postId, String text, String commentatorsEmail) {
        this.postId = postId;
        this.text = text;
        this.commentatorsEmail = commentatorsEmail;
    }

    public CommentDto() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCommentatorsEmail() {
        return commentatorsEmail;
    }

    public void setCommentatorsEmail(String commentatorsEmail) {
        this.commentatorsEmail = commentatorsEmail;
    }
}
