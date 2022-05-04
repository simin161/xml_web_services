package com.example.postservice.service;


import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.model.Reaction;
import com.example.postservice.model.ReactionType;
import com.example.postservice.repository.PostRepository;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import proto.follow.FollowServiceGrpc;
import proto.follow.Followers;
import proto.follow.InputEmail;
import proto.post.*;
import proto.user.InputForGetUserByEmail;
import proto.user.OutputId;
import proto.user.UserServiceGrpc;



@GrpcService
public class PostService extends PostServiceGrpc.PostServiceImplBase {

    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    private FollowServiceGrpc.FollowServiceBlockingStub blockingFollowStub;
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
            PostRepository.getInstance().insert(new Post(usersId,request.getText(),request.getPathToImage(),request.getLink(),new ArrayList<>(),new ArrayList<>(),new Date()));
            output = OutputAddPost.newBuilder().setResult("success").build();
        }else {
            output = OutputAddPost.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void addComment(InputAddComment request, StreamObserver<Output> responseObserver) {
        Output output;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        UserServiceGrpc.newBlockingStub(channel);

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId= blockingStub.findUserIdByEmail(input).getUsersId();



        if(usersId != null ){

           PostRepository.getInstance().addComment(request.getPostId(),new Comment(request.getText(),usersId));
            output = Output.newBuilder().setResult("success").build();
        }else {
            output = Output.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void addReaction(InputAddReaction request, StreamObserver<Output> responseObserver) {
        ReactionType reactionType;
        Output output;
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        UserServiceGrpc.newBlockingStub(channel);

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String usersId= blockingStub.findUserIdByEmail(input).getUsersId();
        if(request.getReactionType().equals("LIKE")){
           reactionType = ReactionType.LIKE;
        }else {
            reactionType = ReactionType.DISLIKE;
        }

        if(usersId != null ){

           PostRepository.getInstance().addReaction(request.getPostId(),new Reaction(usersId,reactionType));
            output = Output.newBuilder().setResult("success").build();
        }else {
            output = Output.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();

    }

    @Override
    public void findAllPostsOfFollowingsByUserEmail(Input request, StreamObserver<OutputPosts> responseObserver) {
        OutputPosts output;


        ManagedChannelBuilder<?> channelBuilderFollower = ManagedChannelBuilder.forAddress("localhost", 6567).usePlaintext();
        Channel channelFollower = channelBuilderFollower.build();
        UserServiceGrpc.newBlockingStub(channelFollower);

        blockingFollowStub = FollowServiceGrpc.newBlockingStub(channelFollower);
        InputEmail input = InputEmail.newBuilder().setEmail(request.getEmail()).build();
        List<Followers> followers =blockingFollowStub.findPersonsFollowings(input).getFollowersList();



    }

    @Override
    public void getNumOfCommentsByPostId(InputPostId request, StreamObserver<OutputNumber> responseObserver) {

    }

    @Override
    public void getNumOfReactionsByPostId(InputPostId request, StreamObserver<OutputNumber> responseObserver) {

    }
}
