package com.vinsguru.grpc.service;

import com.vinsguru.grpc.model.User;
import com.vinsguru.grpc.repository.UserRepository;
import com.vinsguru.grpc.utility.Tokens;
import net.devh.boot.grpc.server.service.GrpcService;
import org.bson.Document;
import proto.user.Input;
import proto.user.Output;
import proto.user.Output2;
import proto.user.UserServiceGrpc;

import java.util.ArrayList;
import java.util.List;


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
    }

    @Override
    public void getAllUsers(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<proto.user.Output2> responseObserver){

        List<User> documentedUsers = UserRepository.getInstance().getAllUsers();
        List<Input> inputs = new ArrayList<Input>();
        for(User u : documentedUsers){
            Input i = Input.newBuilder().setUsername(u.getUsername()).setLastName(u.getLastName()).setPassword(u.getPassword()).setEmail(u.getEmail()).setFirstName(u.getFirstName()).build();
            inputs.add(i);
        }
        proto.user.Output2 output2;
        output2 = Output2.newBuilder().addAllUser(inputs).build();
        responseObserver.onNext(output2);
        responseObserver.onCompleted();

    }
}
