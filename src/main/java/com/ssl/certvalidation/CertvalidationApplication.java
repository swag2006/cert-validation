package com.ssl.certvalidation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

@EntityScan("com.ssl.certvalidation.model")
@SpringBootApplication
public class CertvalidationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CertvalidationApplication.class, args);
	}



}
