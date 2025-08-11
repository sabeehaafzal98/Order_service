package com.example.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class OrderServiceApplication {
	//registers with eureka
//Feign client works because it queries Eureka .
	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}


	// try shutdown of restaurant-service and try calling the Feign endpoints here in the order service controller:-
	// 1) We should be getting an error or fallback.
	// 2) this error shows that Eureka does dynamic resolution.


// we will add the fallback mechanism in case feign-can't reach the restaurant-service.
//this ensures app doesn't crashes when a service is down.
	// 1. add dependencies.
	// 2. modify feign-client to use fallback
	// 3. create fallback class
	// 4. Enable circuit breaker in application.properties

}
