package com.server.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableEurekaClient
@EnableFeignClients("com.server.services")
@SpringBootApplication
public class VechileServiceApplication {

	public static void main(String[] args) {
		//System.setProperty("spring.config.name", "accounts-server");
		SpringApplication.run(VechileServiceApplication.class, args);
	}
}
