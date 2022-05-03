package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.PostDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.post.InputAddPost;
import proto.post.PostServiceGrpc;

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

}
