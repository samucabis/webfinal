package com.br.ufc.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Web2018Application {

	public static void main(String[] args) {
		SpringApplication.run(Web2018Application.class, args);
	      System.out.println(new BCryptPasswordEncoder().encode("123"));
	}
}
