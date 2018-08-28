package com.demo.figin;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements FeignService {
    @Override
    public String sayHiFromClientOne(String name) {
        return name+"所调用的服务已经关闭了，你等哈儿来";
    }
}
