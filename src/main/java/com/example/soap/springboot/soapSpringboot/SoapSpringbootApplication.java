package com.example.soap.springboot.soapSpringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SoapSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapSpringbootApplication.class, args);
	}



}
