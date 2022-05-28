package com.example.followerservice.service;

import com.example.followerservice.model.Follow;
import com.example.followerservice.repository.FollowerRepository;
import com.example.followerservice.utility.MicroserviceConnection;
import com.google.protobuf.Empty;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.user.*;

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

                FollowerRepository.getInstance().insert(new Follow(null, personalId, followersId,true));
                output = OutputAddFollow.newBuilder().setResult("success").build();
            }else {
                FollowerRepository.getInstance().insert(new Follow(null, personalId, followersId,false));
                output = OutputAddFollow.newBuilder().setResult("private profile").build();
            }
        }else {output = OutputAddFollow.newBuilder().setResult("Bad request").build();}
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
            InputID inputid = InputID.newBuilder().setId(u.getFollowerId()).build();
            String username= blockingStub.getUserById(inputid).getUsername();
            Followers follows = Followers.newBuilder().setFollowerEmail(username).setPersonEmail(request.getEmail()).build();
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
        List<Follow> personsFollowings = FollowerRepository.getInstance().findPersonsFollowings(personalId);
        List<Followers> inputs = new ArrayList<>();
        for(Follow u : personsFollowings){
            InputID inputid = InputID.newBuilder().setId(u.getPersonId()).build();
            String username= blockingStub.getUserById(inputid).getUsername();
            Followers follows = Followers.newBuilder().setFollowerEmail(request.getEmail()).setPersonEmail(username).build();
            inputs.add(follows);
        }
        OutputFollowers output2;
        output2 = OutputFollowers.newBuilder().addAllFollowers(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();
    }

    @Override
    public void checkIfUserIsFollowingOtherUser(Followers request, StreamObserver<OutputBoolean> responseObserver) {
        OutputBoolean output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getPersonEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();
        InputForGetUserByEmail input1 = InputForGetUserByEmail.newBuilder().setEmail(request.getFollowerEmail()).build();
        String followersId= blockingStub.findUserIdByEmail(input1).getUsersId();
        if(personalId != null && followersId != null ){
                boolean follow=FollowerRepository.getInstance().checkIfUserIsFollowingOtherUser(personalId, followersId);
                output = OutputBoolean.newBuilder().setPersonIsFollowing(follow).build();
        }else { output = OutputBoolean.newBuilder().setPersonIsFollowing(false).build();}
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void removeFollow(InputRemoveFollow request, StreamObserver<OutputBoolean> responseObserver) {
        OutputBoolean output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getPersonEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();
        InputForGetUserByEmail input1 = InputForGetUserByEmail.newBuilder().setEmail(request.getFollowerEmail()).build();
        String followersId= blockingStub.findUserIdByEmail(input1).getUsersId();
        if(personalId != null && followersId != null ){
                FollowerRepository.getInstance().removeFollow(personalId, followersId);
                output = OutputBoolean.newBuilder().setPersonIsFollowing(true).build();
        }
        output = OutputBoolean.newBuilder().setPersonIsFollowing(false).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void findRequests(InputEmail request, StreamObserver<OutputFollowers> responseObserver) {
        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();
        List<Follow> requests = FollowerRepository.getInstance().findRequests(personalId);
        List<Followers> inputs = new ArrayList<>();
        for(Follow u : requests){
            InputID inputid = InputID.newBuilder().setId(u.getFollowerId()).build();
            String username= blockingStub.getUserById(inputid).getUsername();
            Followers follows = Followers.newBuilder().setFollowerEmail(username).setPersonEmail(request.getEmail()).build();
            inputs.add(follows);
        }
        OutputFollowers output2;
        output2 = OutputFollowers.newBuilder().addAllFollowers(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();
    }

    @Override
    public void answerFollowRequest(InputAnswer request, StreamObserver<OutputBoolean> responseObserver) {
        OutputBoolean output;
        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getPersonEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();
        InputUsername input1 =InputUsername.newBuilder().setUsername(request.getFollowerEmail()).build();
        String followersId =  blockingStub.getUsersIdByUsername(input1).getUsersId();
        boolean ret = FollowerRepository.getInstance().answerFollowRequest(request.getApproved(),followersId,personalId);
        output = OutputBoolean.newBuilder().setPersonIsFollowing(ret).build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void findPersonsFollowingsIds(InputEmail request, StreamObserver<OutputFollowers> responseObserver) {
        UserServiceGrpc.UserServiceBlockingStub blockingStub = MicroserviceConnection.openChannelToUserService();
        InputForGetUserByEmail input = InputForGetUserByEmail.newBuilder().setEmail(request.getEmail()).build();
        String personalId= blockingStub.findUserIdByEmail(input).getUsersId();
        List<Follow> personsFollowings = FollowerRepository.getInstance().findPersonsFollowings(personalId);
        List<Followers> inputs = new ArrayList<>();
        for(Follow u : personsFollowings){
            Followers follows = Followers.newBuilder().setFollowerEmail(u.getFollowerId()).setPersonEmail(u.getPersonId()).build();
            inputs.add(follows);
        }
        OutputFollowers output2;
        output2 = OutputFollowers.newBuilder().addAllFollowers(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();
    }

}
