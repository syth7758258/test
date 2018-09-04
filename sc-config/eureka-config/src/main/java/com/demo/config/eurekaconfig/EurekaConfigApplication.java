package com.demo.config.eurekaconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaConfigApplication.class, args);
	}
}
