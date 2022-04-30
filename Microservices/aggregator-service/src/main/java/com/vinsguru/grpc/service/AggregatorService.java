package com.vinsguru.grpc.service;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.user.Input;
import proto.user.UserServiceGrpc;

import java.util.Map;

@Service
public class AggregatorService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public void addUser(Map<String,String> message) {
        System.out.println("USO SAM U SERVIS AGREGATOR");
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        Input input = Input.newBuilder().setEmail(message.get("email")).setFirstName(message.get("firstName"))
                .setLastName(message.get("lastName")).setPassword(message.get("password")).setUsername(message.get("username")).build();
        this.blockingStub.addUser(input);
    }
}
