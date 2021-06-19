package com.example.grpcsample3.service;

import com.example.grpcsample3.BatchUtil;
import com.example.grpcsample3.service.dto.HelloInDto;
import com.example.grpcsample3.service.dto.HelloOutDto;

public class SampleService {

    public HelloOutDto sayHello(HelloInDto inDto) {
        try {
            var outDto = new HelloOutDto();
            BatchUtil.sendMessage("hello1 " + inDto.getName());
            Thread.sleep(1000);
            BatchUtil.sendMessage("hello2 " + inDto.getName());
            Thread.sleep(1000);
            BatchUtil.sendMessage("hello3 " + inDto.getName());
            Thread.sleep(1000);
            BatchUtil.sendMessage("hello4 " + inDto.getName());
            Thread.sleep(1000);
            outDto.setStatus("success");
            return outDto;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
