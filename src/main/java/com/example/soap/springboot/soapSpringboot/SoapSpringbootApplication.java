package com.example.soap.springboot.soapSpringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SoapSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapSpringbootApplication.class, args);
	}

}
