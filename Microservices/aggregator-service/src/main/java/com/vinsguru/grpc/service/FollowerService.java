package com.vinsguru.grpc.service;

import com.vinsguru.grpc.dto.FollowDto;
import com.vinsguru.grpc.dto.PostDto;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.follow.Follow;
import proto.follow.FollowServiceGrpc;
import proto.follow.InputAddFollow;
import proto.post.InputAddPost;
import proto.post.PostServiceGrpc;

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
}
