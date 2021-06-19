package com.example.grpcsample3;

import ch.qos.logback.core.net.ObjectWriter;
import com.example.grpcsample3.service.SampleService;
import com.example.grpcsample3.service.dto.HelloInDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.examples.batch.BatchExecutorGrpc;
import io.grpc.examples.batch.BatchReply;
import io.grpc.examples.batch.BatchRequest;
import io.grpc.stub.StreamObserver;

public class BatchExecutor extends BatchExecutorGrpc.BatchExecutorImplBase {

    static public <DTO> DTO toDto(String json, Class<DTO> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (json == null) {
                return clazz.getConstructor().newInstance();
            }
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static public <DTO> String toJson(DTO dto) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static private ThreadLocal<StreamObserver<BatchReply>> batchReploy = new ThreadLocal<>();

    static public StreamObserver<BatchReply> getBatchReply() {
        if (batchReploy.get() == null) {
            throw new IllegalStateException("this context is not batch context.");
        }
        return batchReploy.get();
    }

    @Override
    public void execute(BatchRequest request, StreamObserver<BatchReply> responseObserver) {

        try {
            batchReploy.set(responseObserver);
//            String input = request.getInputJson();
//            HelloInDto inDto = toDto(input, HelloInDto.class);
            var inDto = new HelloInDto();
            inDto.setName("hello");
            SampleService servic = new SampleService();
            var outDto = servic.sayHello(inDto);
            var outputJson = toJson(outDto);
            var response = BatchReply.newBuilder().setType("response").setOutput(outputJson).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        } finally {
            batchReploy.remove();
        }

    }
}
