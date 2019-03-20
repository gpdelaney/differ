package com.waes.differ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
public class DifferApplication {

	public static void main(String[] args) {
		SpringApplication.run(DifferApplication.class, args);
	}

}
