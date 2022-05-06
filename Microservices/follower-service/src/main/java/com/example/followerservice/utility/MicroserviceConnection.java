package com.example.followerservice.utility;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import proto.follow.FollowServiceGrpc;
import proto.post.PostServiceGrpc;
import proto.user.UserServiceGrpc;

public class MicroserviceConnection {

    private static UserServiceGrpc.UserServiceBlockingStub userBlockingStub;

    public static UserServiceGrpc.UserServiceBlockingStub openChannelToUserService(){
        if(userBlockingStub == null) {
            ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
            Channel channel = channelBuilder.build();
            userBlockingStub = UserServiceGrpc.newBlockingStub(channel);
        }
        return userBlockingStub;
    }

}
