package com.vinsguru.grpc.service;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import proto.user.Input;
import proto.user.Input2;
import proto.user.UserServiceGrpc;
import reactor.core.publisher.Flux;
import java.util.Map;

@Service
public class AggregatorService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public String addUser(Map<String,String> message) {
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        Input input = Input.newBuilder().setEmail(message.get("email")).setFirstName(message.get("firstName"))
                .setLastName(message.get("lastName")).setPassword(message.get("password")).setUsername(message.get("username")).build();
        return this.blockingStub.addUser(input).getResult();
    }

    public String invalidateUser(String value){
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext();
        Channel channel = channelBuilder.build();
        blockingStub = UserServiceGrpc.newBlockingStub(channel);
        Input2 input = Input2.newBuilder().setAccessToken(value).build();
        return this.blockingStub.invalidateUser(input).getResult();
    }
}
