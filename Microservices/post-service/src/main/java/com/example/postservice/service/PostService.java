package com.example.postservice.service;


import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.utility.MicroserviceConnection;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import proto.post.*;
import proto.user.*;


@GrpcService
public class PostService extends PostServiceGrpc.PostServiceImplBase {

    private UserServiceGrpc.UserServiceBlockingStub blockingStub;
    private final MicroserviceConnection msConnection = new MicroserviceConnection();
    @Override
    public void addPost(InputAddPost request, StreamObserver<OutputAddPost> responseObserver) {

        OutputAddPost output;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
         UserServiceGrpc.newBlockingStub(channel);

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId= blockingStub.findUserIdByEmail(input).getUsersId();
        if(usersId != null){
            PostRepository.getInstance().insert(new Post(usersId,request.getText(),request.getPathToImage(),request.getLink()));
            output = OutputAddPost.newBuilder().setResult("success").build();
        }else {
            output = OutputAddPost.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllPosts(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<proto.post.AllPosts> responseObserver){
        List<Post> documentedPosts = PostRepository.getInstance().getAllPosts();
        List<InputAddPost> iaps = new ArrayList<InputAddPost>();

        msConnection.setUpCommunicationPostUser(blockingStub);
        for(Post p : documentedPosts){
            OutputId userId = OutputId.newBuilder().setUsersId(p.getUsersId()).build();
            String email = blockingStub.findUserEmailById(userId).getEmail();
            InputAddPost iap = InputAddPost.newBuilder().setPathToImage(p.getPathToImage()).setText(p.getText())
                    .setLink(p.getLink()).setEmail(email).build();
            iaps.add(iap);
        }
        AllPosts allPosts;
        allPosts = AllPosts.newBuilder().addAllAllPosts(iaps).build();
        responseObserver.onNext(allPosts);
        responseObserver.onCompleted();

    }
}
