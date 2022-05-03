package com.example.followerservice;


import com.example.followerservice.service.FollowerService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class FollowerServiceApplication {


    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(6567)
                .addService(new FollowerService())
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
