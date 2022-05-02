package com.vinsguru.grpc.service;

import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.repository.UserRepository;
<<<<<<< HEAD
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
=======
import com.vinsguru.grpc.utility.Tokens;
>>>>>>> master
import net.devh.boot.grpc.server.service.GrpcService;
import proto.user.Output;
import proto.user.UserServiceGrpc;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private proto.example.UserServiceGrpc.UserServiceBlockingStub blockingStub;

    @Override
    public void addUser(proto.user.Input request,
<<<<<<< HEAD
        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        System.out.println("MIKROSERVIS AAAAAAA");
        UserRepository.getInstance().insert(new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword()));
        ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress("localhost", 6566).usePlaintext();
        Channel channel = channelBuilder.build();
        blockingStub = proto.example.UserServiceGrpc.newBlockingStub(channel);
        blockingStub.exampleMethod(null);
=======
                        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        proto.user.Output output;
        if(UserRepository.getInstance().findUserByEmail(request.getEmail()) == null) {
            UserRepository.getInstance().insert(new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword()));
            output = Output.newBuilder().setResult(Tokens.generateToken(request.getUsername(), request.getEmail())).build();
        }else
            output = Output.newBuilder().setResult("false").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void logInUser(proto.user.Input1 request, io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        User user = UserRepository.getInstance().findUserByEmail(request.getEmail());
        proto.user.Output output;
        if(user != null && user.getPassword().equals(request.getPassword()))
            output = Output.newBuilder().setResult(Tokens.generateToken(user.getUsername(), user.getEmail())).build();
        else
            output = Output.newBuilder().setResult("false").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
    }

    @Override
    public void invalidateUser(proto.user.Input2 request, io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        proto.user.Output output = Output.newBuilder().setResult("").build();
        responseObserver.onNext(output);
        responseObserver.onCompleted();
>>>>>>> master
    }
}
