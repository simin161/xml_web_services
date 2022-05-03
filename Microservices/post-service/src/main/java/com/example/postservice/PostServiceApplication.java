package com.example.postservice;



//import io.grpc.Server;
//import io.grpc.ServerBuilder;

import com.example.postservice.service.PostService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class PostServiceApplication {


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(6566)
                .addService(new PostService())
                .build();

        // startnpm startnpm
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("gRPC server is shutting down!");
            server.shutdown();
        }));

        // wait for 1 hr
        server.awaitTermination(1, TimeUnit.HOURS);
    }

}
