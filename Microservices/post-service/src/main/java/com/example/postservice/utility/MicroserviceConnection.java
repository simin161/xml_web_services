package com.example.postservice.utility;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import proto.follow.FollowServiceGrpc;
import proto.user.UserServiceGrpc;

public class MicroserviceConnection {

    private static UserServiceGrpc.UserServiceBlockingStub blockingStubUser;
    private static FollowServiceGrpc.FollowServiceBlockingStub blockingStubFollower;

    public UserServiceGrpc.UserServiceBlockingStub setUpCommunicationPostUser(){
        if(blockingStubUser == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
            Channel channel = channelBuilder.build();
            blockingStubUser = UserServiceGrpc.newBlockingStub(channel);
        }
        return blockingStubUser;
    }

    public FollowServiceGrpc.FollowServiceBlockingStub setUpCommunicationPostFollower() {
        if (blockingStubFollower == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6567).usePlaintext();
            Channel channel = channelBuilder.build();
            blockingStubFollower = FollowServiceGrpc.newBlockingStub(channel);
        }
        return blockingStubFollower;
    }

}
