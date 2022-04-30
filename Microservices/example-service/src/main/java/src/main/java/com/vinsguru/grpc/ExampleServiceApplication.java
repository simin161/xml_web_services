package src.main.java.com.vinsguru.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import src.main.java.com.vinsguru.grpc.service.ExampleService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
public class ExampleServiceApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder
                .forPort(6566)
                .addService(new ExampleService())
                .build();

        // start
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("gRPC server is shutting down!");
            server.shutdown();
        }));

        // wait for 1 hr
        server.awaitTermination(1, TimeUnit.HOURS);

    }

}
