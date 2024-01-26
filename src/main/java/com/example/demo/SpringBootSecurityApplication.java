package com.example.demo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class SpringBootSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}

}
