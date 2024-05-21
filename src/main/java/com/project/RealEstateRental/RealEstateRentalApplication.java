package com.project.RealEstateRental;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;

@SpringBootApplication
public class RealEstateRentalApplication {
	public static void main(String[] args) {
		SpringApplication.run(RealEstateRentalApplication.class, args);
	}
}
