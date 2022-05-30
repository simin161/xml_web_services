package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.CommentDto;
import com.vinsguru.grpc.dto.PostDto;
import com.vinsguru.grpc.dto.ReactionDto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.post.*;
import proto.user.InputForGetUserByEmail;
import proto.user.InputID;
import proto.user.UserServiceGrpc;

import java.util.ArrayList;
import java.util.List;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToPostService;
import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToUserService;

@Service
public class PostService {

    @GrpcClient("post-service")
    private PostServiceGrpc.PostServiceBlockingStub blockingStub;
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

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

    public String deleteReaction(ReactionDto reaction) {
        blockingStub = openChannelToPostService();
        InputAddReaction input = InputAddReaction.newBuilder()
                .setEmail(reaction.getEmail()).setPostId(reaction.getPostId()).setReactionType(reaction.getReactionType()).build();
        return this.blockingStub.deleteReaction(input).getResult();
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
        List<PostDto> invertedRetVal = new ArrayList<PostDto>();
        for(PostToShow iap : this.blockingStub.getAllUserPosts(ue).getAllPostsList()){
            PostDto postDTO = new PostDto();
            postDTO.setEmail(iap.getEmail());
            postDTO.setLink(iap.getLink());
            postDTO.setText(iap.getText());
            postDTO.setPathToImage(iap.getPathToImage());
            postDTO.setDate(iap.getDate());
            postDTO.setIdPost(iap.getPostId());
            int numOfReactions = this.blockingStub.getNumOfReactionsByPostId(InputPostId.newBuilder().setPostId(iap.getPostId()).build()).getNum();
            int numOfComments = this.blockingStub.getNumOfCommentsByPostId(InputPostId.newBuilder().setPostId(iap.getPostId()).build()).getNum();
            postDTO.setNumOfReactions(numOfReactions);
            postDTO.setNumOfComments(numOfComments);
            retVal.add(postDTO);
        }
        for(int i = retVal.size()-1; i>=0; i--){
            invertedRetVal.add(retVal.get(i));
        }
        return invertedRetVal;
    }

    public List<PostDto> findAllPostsOfFollowingsByUserEmail(String email){
        blockingStub = openChannelToPostService();
        userServiceBlockingStub=openChannelToUserService();
        Input input = Input.newBuilder().setEmail(email).build();
        List<PostDto> posts = new ArrayList<>();
       for(OutputPost post: blockingStub.findAllPostsOfFollowingsByUserEmail(input).getPostsList()){
           int numOfReactions = this.blockingStub.getNumOfReactionsByPostId(InputPostId.newBuilder().setPostId(post.getPostId()).build()).getNum();
           int numOfComments = this.blockingStub.getNumOfCommentsByPostId(InputPostId.newBuilder().setPostId(post.getPostId()).build()).getNum();
           InputID inputID= InputID.newBuilder().setId(post.getUsersId()).build();
           String fullName=userServiceBlockingStub.getUserById(inputID).getFirstName();
           fullName+=" "+userServiceBlockingStub.getUserById(inputID).getLastName();
           posts.add(new PostDto(post.getPostId(),post.getUsersId(),post.getText(),post.getPathToImage(),post.getLink(),post.getDate(),numOfReactions,numOfComments,fullName));
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

    public List<ReactionDto> getReactionsByPostId(String postId) {
        blockingStub = openChannelToPostService();
        InputPostId input = InputPostId.newBuilder().setPostId(postId).build();
        List<ReactionDto> reactions = new ArrayList<>();
        for(OutputReaction reaction:blockingStub.getReactionsByPostId(input).getReactionsList()){
            reactions.add(new ReactionDto(reaction.getIdReaction(),reaction.getUsersId(),reaction.getReaction()));
        }
     return reactions;

    }

    public List<CommentDto> getCommentsByPostId(String postId) {
        blockingStub = openChannelToPostService();
        InputPostId input = InputPostId.newBuilder().setPostId(postId).build();
        List<CommentDto> comments = new ArrayList<>();
        for(OutputComment comment:blockingStub.getCommentsByPostId(input).getCommentsList()){
            comments.add(new CommentDto(comment.getIdComment(),comment.getText(),comment.getCommentatorsId()));
        }
        return comments;

    }
    public String checkReaction(String postId,String email) {
        blockingStub = openChannelToPostService();
        InputCheck input = InputCheck.newBuilder().setEmail(email).setPostId(postId).build();
        return this.blockingStub.checkReaction(input).getCheck();
    }

    public List<PostDto> getAllFeedPosts(String email, int value) {
        Input input = Input.newBuilder().setEmail(email).build();
        blockingStub = openChannelToPostService();
        List<OutputPost> outputPosts = blockingStub.findAllPostsOfFollowingsByUserEmail(input).getPostsList();
        List<PostDto> posts = new ArrayList<>();
        List<PostDto> postsToShow = new ArrayList<>();
        for(OutputPost op : outputPosts){
            userServiceBlockingStub = openChannelToUserService();
            PostDto postDto = new PostDto();
            postDto.setIdPost(op.getPostId());
            postDto.setText(op.getText());
            postDto.setLink(op.getLink());
            postDto.setEmail(userServiceBlockingStub.getUserById(InputID.newBuilder().setId(op.getUsersId().toString()).build()).getEmail());
            postDto.setFullName(userServiceBlockingStub.getUserById(InputID.newBuilder().setId(op.getUsersId().toString()).build()).getFirstName() + " "
                    + userServiceBlockingStub.getUserById(InputID.newBuilder().setId(op.getUsersId().toString()).build()).getLastName());
            postDto.setDate(op.getDate());
            postDto.setPathToImage(op.getPathToImage());
            posts.add(postDto);
        }
        if(posts.size()>5 && posts.size()%5==0) {
            for (int i = posts.size() - 1 - value * 5; i >= posts.size() - (1 + value) * 5; i--) {
                postsToShow.add(posts.get(i));
            }
        }else if(posts.size()>5){
            if(posts.size()-(1+value)*5<0){
                for(int i = posts.size()-1 - value*5; i >=0; i--){
                    postsToShow.add(posts.get(i));
                }
            }else {
                for (int i = posts.size() - 1 - value * 5; i >= posts.size() - (1 + value) * 5; i--) {
                    postsToShow.add(posts.get(i));
                }
            }
        }
        else{
            //obrnuti redosled
            for(int i = posts.size()-1; i >= 0; i--){
                postsToShow.add(posts.get(i));
            }
            return postsToShow;
        }
        return postsToShow;
    }
}
