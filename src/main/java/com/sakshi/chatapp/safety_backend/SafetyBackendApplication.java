package com.sakshi.chatapp.safety_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SafetyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyBackendApplication.class, args);
		//stem.out.println("8000");
	
	}

}
