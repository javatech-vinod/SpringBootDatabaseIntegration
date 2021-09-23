package com.atradius.cibt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpringBootRestApiIntegration {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestApiIntegration.class, args);
	}

}
