package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.CommentDto;
import com.vinsguru.grpc.dto.PostDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.post.InputAddComment;
import proto.post.InputAddPost;
import proto.post.PostServiceGrpc;
import proto.post.UserEmail;
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

    public List<PostDto> getAllPosts(){
        com.google.protobuf.Empty request = null;
        blockingStub = openChannelToPostService();
        List<PostDto> retVal = new ArrayList<PostDto>();
        for(InputAddPost iap : this.blockingStub.getAllPosts(request).getAllPostsList()){
            PostDto postDTO = new PostDto();
            postDTO.setEmail(iap.getEmail());
            postDTO.setLink(iap.getLink());
            postDTO.setText(iap.getText());
            postDTO.setPathToImage(iap.getPathToImage());
            retVal.add(postDTO);
        }
        return retVal;
    }

    public List<PostDto> getAllUsersPosts(String email){
        UserEmail ue = UserEmail.newBuilder().setEmail(email).build();
        blockingStub = openChannelToPostService();
        List<PostDto> retVal = new ArrayList<PostDto>();
        for(InputAddPost iap : this.blockingStub.getAllUserPosts(ue).getAllPostsList()){
            PostDto postDTO = new PostDto();
            postDTO.setEmail(iap.getEmail());
            postDTO.setLink(iap.getLink());
            postDTO.setText(iap.getText());
            postDTO.setPathToImage(iap.getPathToImage());
            retVal.add(postDTO);
        }
        return retVal;
    }

}
