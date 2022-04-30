package com.vinsguru.grpc.service;

import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.repository.UserRepository;
import net.devh.boot.grpc.server.service.GrpcService;
import proto.user.UserServiceGrpc;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void addUser(proto.user.Input request,
                        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        System.out.println("MIKROSERVIS AAAAAAA");
        UserRepository.getInstance().insert(new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getEmail(), request.getPassword()));
    }
}
