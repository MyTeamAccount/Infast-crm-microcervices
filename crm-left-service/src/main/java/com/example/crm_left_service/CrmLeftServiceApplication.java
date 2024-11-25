package com.example.crm_left_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CrmLeftServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmLeftServiceApplication.class, args);
	}

}
