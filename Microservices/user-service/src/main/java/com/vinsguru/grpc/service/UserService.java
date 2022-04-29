package com.vinsguru.grpc.service;

import net.devh.boot.grpc.server.service.GrpcService;
import proto.user.UserServiceGrpc;


@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    // server sends multiples data one by one
    // input is 6, server will send 2, 4, 6, 8, 10, 12
    @Override
    public void addUser(proto.user.Input request,
                        io.grpc.stub.StreamObserver<proto.user.Output> responseObserver) {
        System.out.println("MIKROSERVIS AAAAAAA");
    }
}
