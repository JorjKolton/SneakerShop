package com.sobchenko.sneakershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.sobchenko.sneakershop.model")
@EnableJpaRepositories("com.sobchenko.sneakershop.repository")
@SpringBootApplication
public class SneakerShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SneakerShopApplication.class, args);
	}

}