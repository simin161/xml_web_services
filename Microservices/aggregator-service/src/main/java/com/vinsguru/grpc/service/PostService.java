package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.CommentDto;
import com.vinsguru.grpc.dto.PostDto;
import com.vinsguru.grpc.dto.ReactionDto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.post.*;

import java.util.ArrayList;
import java.util.List;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToPostService;

@Service
public class PostService {

    @GrpcClient("post-service")
    private PostServiceGrpc.PostServiceBlockingStub blockingStub;

    public String addPost(PostDto post) {
        blockingStub = openChannelToPostService();
        InputAddPost input = InputAddPost.newBuilder().setEmail(post.getEmail()).setText(post.getText()).setLink(post.getLink()).setPathToImage(post.getPathToImage()).build();
        return this.blockingStub.addPost(input).getResult();
    }

    public String addComment(CommentDto comment) {
        blockingStub = openChannelToPostService();
        InputAddComment input = InputAddComment.newBuilder()
                .setEmail(comment.getCommentatorsEmail()).setPostId(comment.getPostId()).setText(comment.getText()).build();
        return this.blockingStub.addComment(input).getResult();
    }

    public String addReaction(ReactionDto reaction) {
        blockingStub = openChannelToPostService();
        InputAddReaction input = InputAddReaction.newBuilder()
                .setEmail(reaction.getEmail()).setPostId(reaction.getPostId()).setReactionType(reaction.getReactionType()).build();
        return this.blockingStub.addReaction(input).getResult();
    }

    public List<PostDto> getAllPosts(){
        com.google.protobuf.Empty request = null;
        blockingStub = openChannelToPostService();
        List<PostDto> retVal = new ArrayList<PostDto>();
        for(PostToShow iap : this.blockingStub.getAllPosts(request).getAllPostsList()){
            PostDto postDTO = new PostDto();
            postDTO.setEmail(iap.getEmail());
            postDTO.setLink(iap.getLink());
            postDTO.setText(iap.getText());
            postDTO.setPathToImage(iap.getPathToImage());
            postDTO.setDate(iap.getDate());
            retVal.add(postDTO);
        }
        return retVal;
    }


    public List<PostDto> getAllUsersPosts(String email){
        UserEmail ue = UserEmail.newBuilder().setEmail(email).build();
        blockingStub = openChannelToPostService();
        List<PostDto> retVal = new ArrayList<PostDto>();
        for(PostToShow iap : this.blockingStub.getAllUserPosts(ue).getAllPostsList()){
            PostDto postDTO = new PostDto();
            postDTO.setEmail(iap.getEmail());
            postDTO.setLink(iap.getLink());
            postDTO.setText(iap.getText());
            postDTO.setPathToImage(iap.getPathToImage());
            postDTO.setDate(iap.getDate());
            retVal.add(postDTO);
        }
        return retVal;
    }

    public List<PostDto> findAllPostsOfFollowingsByUserEmail(String email){
        blockingStub = openChannelToPostService();
        Input input = Input.newBuilder().setEmail(email).build();
        List<PostDto> posts = new ArrayList<>();
       for(OutputPost post: blockingStub.findAllPostsOfFollowingsByUserEmail(input).getPostsList()){
            posts.add(new PostDto(post.getPostId(),post.getUsersId(),post.getText(),post.getPathToImage(),post.getLink(),post.getDate()));
       }
       return  posts;
    }

    public int getNumOfCommentsByPostId(String postId) {
        blockingStub = openChannelToPostService();
        InputPostId input = InputPostId.newBuilder().setPostId(postId).build();
      return   blockingStub.getNumOfCommentsByPostId(input).getNum();

    }

    public int getNumOfReactionsByPostId(String postId) {
        blockingStub = openChannelToPostService();
        InputPostId input = InputPostId.newBuilder().setPostId(postId).build();
        return   blockingStub.getNumOfReactionsByPostId(input).getNum();

    }

}
