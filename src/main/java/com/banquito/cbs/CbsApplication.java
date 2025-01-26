package com.banquito.cbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CbsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CbsApplication.class, args);
	}
}
