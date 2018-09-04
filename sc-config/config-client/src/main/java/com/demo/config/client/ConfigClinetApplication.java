package com.demo.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConfigClinetApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClinetApplication.class,args);
    }
}
