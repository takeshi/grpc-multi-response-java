package com.example.grpcsample3;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

class DemoClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);

        HelloRequest request = HelloRequest.newBuilder()
                .setName("Tom")
                .build();

        HelloReply reply = stub.sayHello(request);

        System.out.println("Reply: " + reply);

        var res = stub.sayHello2(request);
        res.forEachRemaining((hello)->{
            System.out.println(hello);
        });
    }
}