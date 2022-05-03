package com.example.postservice.service;

import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import com.example.postservice.utility.MicroserviceConnection;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.ArrayList;
import proto.post.*;
import proto.user.InputForGetUserByEmail;
import proto.user.OutputId;
import proto.user.UserServiceGrpc;
import java.util.List;

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
            PostRepository.getInstance().insert(new Post(usersId,request.getText(),request.getPathToImage(),request.getLink(),new ArrayList<>()));
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
        String usersId = blockingStub.findUserIdByEmail(input).getUsersId();


        if (usersId != null) {
            blockingStub.checkIfAccountIsPrivate(input).getPrivate();
            PostRepository.getInstance().addComment(request.getPostId(), new Comment(request.getText(), usersId));
            output = Output.newBuilder().setResult("success").build();
        } else {
            output = Output.newBuilder().setResult("Bad request").build();
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
    @Override
    public void getAllUserPosts(UserEmail request, StreamObserver<AllPosts> responseObserver){
        List<Post> documentedPosts = PostRepository.getInstance().getAllPosts();
        List<InputAddPost> iaps = new ArrayList<InputAddPost>();

        msConnection.setUpCommunicationPostUser(blockingStub);
        for(Post p : documentedPosts){
            OutputId userId = OutputId.newBuilder().setUsersId(p.getUsersId()).build();
            String email = blockingStub.findUserEmailById(userId).getEmail();
            if(request.getEmail().equals(email)){
                InputAddPost iap = InputAddPost.newBuilder().setPathToImage(p.getPathToImage()).setText(p.getText())
                        .setLink(p.getLink()).setEmail(email).build();
                iaps.add(iap);
            }
        }
        AllPosts allUserPosts;
        allUserPosts = AllPosts.newBuilder().addAllAllPosts(iaps).build();
        responseObserver.onNext(allUserPosts);
        responseObserver.onCompleted();
    }
}
