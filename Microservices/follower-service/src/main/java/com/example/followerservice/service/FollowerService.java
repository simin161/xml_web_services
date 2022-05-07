package com.example.followerservice.service;

import com.example.followerservice.model.Follow;
import com.example.followerservice.repository.FollowerRepository;
import com.example.followerservice.utility.MicroserviceConnection;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.user.Input;
import proto.user.InputForGetUserByEmail;
import proto.user.Output2;
import proto.user.UserServiceGrpc;

import  proto.follow.*;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class FollowerService extends FollowServiceGrpc.FollowServiceImplBase {

    @Override
    public void addFollow(InputAddFollow request, StreamObserver<OutputAddFollow> responseObserver) {
        OutputAddFollow output;

        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getPersonEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();

        InputForGetUserByEmail input1 = InputForGetUserByEmail.newBuilder().setEmail(request.getFollowerEmail()).build();
        String followersId= blockingStub.findUserIdByEmail(input1).getUsersId();


        if(personalId != null && followersId != null ){
            if(!blockingStub.checkIfAccountIsPrivate(input).getPrivate()) {

                FollowerRepository.getInstance().insert(new Follow(null, personalId, followersId));
                output = OutputAddFollow.newBuilder().setResult("success").build();
            }else {
                output = OutputAddFollow.newBuilder().setResult("private profile").build();
            }
        }else {
            output = OutputAddFollow.newBuilder().setResult("Bad request").build();
        }
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void findPersonsFollowers(InputEmail request, StreamObserver<OutputFollowers> responseObserver) {

        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();

        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();

        List<Follow> personsFollowers = FollowerRepository.getInstance().findPersonsFollowers(personalId);


        List<Followers> inputs = new ArrayList<>();
        for(Follow u : personsFollowers){
             Followers follows = Followers.newBuilder().setFollowerEmail(u.getFollowerId()).setPersonEmail(u.getPersonId()).build();
            inputs.add(follows);
        }
        OutputFollowers output2;
        output2 = OutputFollowers.newBuilder().addAllFollowers(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();
    }

    @Override
    public void findPersonsFollowings(InputEmail request, StreamObserver<OutputFollowers> responseObserver) {

        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();

        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();

        List<Follow> personsFollowers = FollowerRepository.getInstance().findPersonsFollowings(personalId);


        List<Followers> inputs = new ArrayList<>();
        for(Follow u : personsFollowers){
            Followers follows = Followers.newBuilder().setFollowerEmail(u.getFollowerId()).setPersonEmail(u.getPersonId()).build();
            inputs.add(follows);
        }
        OutputFollowers output2;
        output2 = OutputFollowers.newBuilder().addAllFollowers(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();

    }
}
