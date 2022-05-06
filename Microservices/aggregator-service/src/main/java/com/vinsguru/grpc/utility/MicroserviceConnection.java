package com.vinsguru.grpc.utility;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import proto.follow.FollowServiceGrpc;
import proto.post.PostServiceGrpc;
import proto.user.UserServiceGrpc;

public class MicroserviceConnection {

    private static UserServiceGrpc.UserServiceBlockingStub userBlockingStub;
    private static PostServiceGrpc.PostServiceBlockingStub postServiceBlockingStub;
    private static FollowServiceGrpc.FollowServiceBlockingStub followServiceBlockingStub;

    public static UserServiceGrpc.UserServiceBlockingStub openChannelToUserService(){
        if(userBlockingStub == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
            Channel channel = channelBuilder.build();
            userBlockingStub = UserServiceGrpc.newBlockingStub(channel);
        }
        return userBlockingStub;
    }

    public static PostServiceGrpc.PostServiceBlockingStub openChannelToPostService(){
        if(postServiceBlockingStub == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6566).usePlaintext();
            Channel channel = channelBuilder.build();
            postServiceBlockingStub = PostServiceGrpc.newBlockingStub(channel);
        }
        return postServiceBlockingStub;
    }

    public static FollowServiceGrpc.FollowServiceBlockingStub openChannelToFollowService(){
        if(followServiceBlockingStub == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6567).usePlaintext();
            Channel channel = channelBuilder.build();
            followServiceBlockingStub = FollowServiceGrpc.newBlockingStub(channel);
        }
        return followServiceBlockingStub;
    }
}
