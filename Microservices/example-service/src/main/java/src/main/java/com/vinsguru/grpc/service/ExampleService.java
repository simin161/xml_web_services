package src.main.java.com.vinsguru.grpc.service;


import net.devh.boot.grpc.server.service.GrpcService;
import proto.example.UserServiceGrpc;

@GrpcService
public class ExampleService extends UserServiceGrpc.UserServiceImplBase {

    @Override
    public void exampleMethod(proto.example.Input request, io.grpc.stub.StreamObserver<proto.example.Output> responseObserver) {
        System.out.println("Entered Example Microservice From User Microservice");
    }
}
