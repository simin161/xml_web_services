package com.vinsguru.grpc.utility;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import proto.follow.FollowServiceGrpc;
import proto.post.PostServiceGrpc;
import proto.user.UserServiceGrpc;

public class MicroserviceConnection {

    public static UserServiceGrpc.UserServiceBlockingStub openChannelToUserService(){
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        return UserServiceGrpc.newBlockingStub(channel);
    }

    public static PostServiceGrpc.PostServiceBlockingStub openChannelToPostService(){
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6566).usePlaintext();
        Channel channel = channelBuilder.build();
        return PostServiceGrpc.newBlockingStub(channel);
    }

    public static FollowServiceGrpc.FollowServiceBlockingStub openChannelToFollowService(){
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6567).usePlaintext();
        Channel channel = channelBuilder.build();
        return FollowServiceGrpc.newBlockingStub(channel);
    }
}
