package com.javafullstackguru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HrsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrsmApplication.class, args);
	}

}
