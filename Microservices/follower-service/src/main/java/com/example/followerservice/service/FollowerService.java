package com.example.followerservice.service;

import com.example.followerservice.model.Follow;
import com.example.followerservice.repository.FollowerRepository;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.bson.types.ObjectId;
import proto.post.InputAddPost;
import proto.post.OutputAddPost;
import proto.user.InputForGetUserByEmail;
import proto.user.UserServiceGrpc;

import  proto.follow.*;

@GrpcService
public class FollowerService extends FollowServiceGrpc.FollowServiceImplBase {

    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    @Override
    public void addFollow(InputAddFollow request, StreamObserver<OutputAddFollow> responseObserver) {
        OutputAddFollow output;

        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        UserServiceGrpc.newBlockingStub(channel);

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getPersonEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();

        InputForGetUserByEmail input1 = InputForGetUserByEmail.newBuilder().setEmail(request.getFollowerEmail()).build();
        String followersId= blockingStub.findUserIdByEmail(input1).getUsersId();

        if(personalId != null && followersId != null ){
            FollowerRepository.getInstance().insert(new Follow(null,personalId,followersId));
            output = OutputAddFollow.newBuilder().setResult("success").build();
        }else {
            output = OutputAddFollow.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();



    }


}
