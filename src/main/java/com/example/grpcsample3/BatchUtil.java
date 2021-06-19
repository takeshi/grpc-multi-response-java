package com.example.grpcsample3;

import io.grpc.examples.batch.BatchReply;

public class BatchUtil {

    static public void sendMessage(String message) {
        var replyObserver = BatchExecutor.getBatchReply();
        var msg = BatchReply.newBuilder().setType("message").setOutput(message).build();
        replyObserver.onNext(msg);
        System.out.println("send message to client " + message);
    }
}
