package com.github.reenatobruno.parts_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PartsApiApplication {

	static void main(String[] args) {
		SpringApplication.run(PartsApiApplication.class, args);
	}

}
