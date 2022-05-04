package com.vinsguru.grpc.dto;

public class ReactionDto {

   private String postId;
   private String reactionType;
   private String email;

    public ReactionDto(String postId, String reactionType, String email) {
        this.postId = postId;
        this.reactionType = reactionType;
        this.email = email;
    }

    public ReactionDto() {
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
