package com.techinterview.edtsbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EdtsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdtsBackendApplication.class, args);
	}

}
