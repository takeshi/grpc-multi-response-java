package com.example.grpcsample3;

import io.grpc.ServerBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.grpc.stub.StreamObserver;

class DemoServer {

    public static void main(String[] args) throws Exception {

        var server = ServerBuilder.forPort(6565)
                .addService(new GreeterImpl())
                .addService(ProtoReflectionService.newInstance())
                .addService(new BatchExecutor())
                .build();

        server.start();

        server.awaitTermination();
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }

        @Override
        public void sayHello2(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello3 " + req.getName()).build();
            responseObserver.onNext(reply);
            HelloReply reply2 = HelloReply.newBuilder().setMessage("Hello4 " + req.getName()).build();
            responseObserver.onNext(reply2);
            HelloReply reply3 = HelloReply.newBuilder().setMessage("Hello5 " + req.getName()).build();
            responseObserver.onNext(reply3);
            responseObserver.onCompleted();
        }
    }
}