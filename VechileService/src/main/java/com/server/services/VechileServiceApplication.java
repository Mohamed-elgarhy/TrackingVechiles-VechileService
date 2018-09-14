package com.server.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * @author mkader3
 *
 *This is the main class for booting this service most of the configurations are provided by annotations
 *
 */
@EnableEurekaClient
@EnableFeignClients("com.server.services")
@SpringBootApplication
public class VechileServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VechileServiceApplication.class, args);
	}
}
