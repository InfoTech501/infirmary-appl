package com.rocs.infirmary.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/**
 * Main class for the Infirmary application
 * Serves as the entry point to start the Spring Boot application
 */
@SpringBootApplication
public class InfirmaryApplication {
	/**
	 * The main method to launch the Spring Boot application.
	 *
	 * @param args arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(InfirmaryApplication.class, args);
	}
	/**
	 * This creates and returns BCryptPasswordEncoder.
	 * @return a BCryptPasswordEncoder for password encryption
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
