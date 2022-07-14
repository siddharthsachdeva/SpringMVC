package com.sid.dashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SidAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SidAdminApplication.class, args);
	}
}
