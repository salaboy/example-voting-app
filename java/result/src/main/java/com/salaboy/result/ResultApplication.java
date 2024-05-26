package com.salaboy.result;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ResultApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResultApplication.class, args);
	}

}


