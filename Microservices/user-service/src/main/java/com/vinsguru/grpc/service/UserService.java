package com.vinsguru.grpc.service;

import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.repository.UserRepository;
import com.vinsguru.grpc.utility.Tokens;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.user.Output;
import proto.user.UserServiceGrpc;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void addUser(proto.user.Input request,
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
    public void getUser(proto.user.Input1 request, io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {

    }

    @Override
    public void invalidateUser(proto.user.Input2 request, io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {

    }
}
