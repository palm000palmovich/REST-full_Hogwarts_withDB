package ru.hogwarts.schoolloohcs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class SchoolloohcsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SchoolloohcsApplication.class, args);
	}
}
