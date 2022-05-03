package com.example.postservice.utility;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import proto.user.UserServiceGrpc;

public class MicroserviceConnection {



    public void setUpCommunicationPostUser(UserServiceGrpc.UserServiceBlockingStub blockingStub){
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        UserServiceGrpc.newBlockingStub(channel);

        blockingStub = UserServiceGrpc.newBlockingStub(channel);
    }

}
