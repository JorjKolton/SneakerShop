package com.sobchenko.sneakershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EntityScan("com.sobchenko.sneakershop.model")
@EnableJpaRepositories("com.sobchenko.sneakershop.repository")
@SpringBootApplication
public class SneakerShopApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SneakerShopApplication.class, args);
		PasswordEncoder pass = context.getBean(PasswordEncoder.class);
		System.out.println(pass.encode("pass"));
	}

}