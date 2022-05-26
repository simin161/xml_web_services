package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.FollowDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.follow.*;

import java.util.ArrayList;
import java.util.List;

import static com.vinsguru.grpc.utility.MicroserviceConnection.openChannelToFollowService;

@Service
public class FollowerService {

    @GrpcClient("follower-service")
    private FollowServiceGrpc.FollowServiceBlockingStub blockingStub;

    public String addFollower(FollowDto follow) {
        blockingStub = openChannelToFollowService();
        InputAddFollow input = InputAddFollow.newBuilder().setPersonEmail(follow.getPersonEmail())
        .setFollowerEmail(follow.getFollowerEmail())
        .build();
        return this.blockingStub.addFollow(input).getResult();
    }

    public List<FollowDto> findPersonsFollowers(String email) {
        blockingStub = openChannelToFollowService();
        InputEmail input = InputEmail.newBuilder().setEmail(email)
                .build();
        List<FollowDto> followers = new ArrayList<>();
        for(Followers follower: this.blockingStub.findPersonsFollowers(input).getFollowersList()){
            followers.add(new FollowDto(follower.getPersonEmail(),follower.getFollowerEmail()));
        }
        return  followers;
    }

    public List<FollowDto> findPersonsFollowings(String email) {
        blockingStub = openChannelToFollowService();
        InputEmail input = InputEmail.newBuilder().setEmail(email)
                .build();
        List<FollowDto> followers = new ArrayList<>();
        for(Followers follower: this.blockingStub.findPersonsFollowings(input).getFollowersList()){
            followers.add(new FollowDto(follower.getPersonEmail(),follower.getFollowerEmail()));
        }
        return  followers;
    }

    public boolean checkIfUserIsFollowingOtherUser(String myUserEmail, String otherUserEmail) {
        blockingStub = openChannelToFollowService();
        Followers input = Followers.newBuilder().setPersonEmail(otherUserEmail).setFollowerEmail(myUserEmail).build();
        return this.blockingStub.checkIfUserIsFollowingOtherUser(input).getPersonIsFollowing();
    }

    public void removeFollower(FollowDto follow) {
        blockingStub = openChannelToFollowService();
        System.out.println("FOLOV PERSON " +follow.getPersonEmail());
        System.out.println("FOLOVer " +follow.getFollowerEmail());
        InputRemoveFollow input = InputRemoveFollow.newBuilder().setPersonEmail(follow.getPersonEmail())
                .setFollowerEmail(follow.getFollowerEmail())
                .build();
        this.blockingStub.removeFollow(input);
    }
}
