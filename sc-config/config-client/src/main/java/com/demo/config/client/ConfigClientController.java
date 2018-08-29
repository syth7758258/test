package com.demo.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${SKregistUrl}")
    String SKregistUrl;

    @RequestMapping(value = "/get")
    public String getValue(){
        return SKregistUrl;
    }
}
