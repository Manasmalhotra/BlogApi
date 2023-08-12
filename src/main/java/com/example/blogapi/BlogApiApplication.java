package com.example.blogapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@OpenAPIDefinition(
        info=@Info(
                title="Spring Boot Blog API REST APIs.",
                description="Spring Boot Blog App REST API documentation",
                version="v1.0",
                contact = @Contact(name="Manas",
                email="mansmalhotra67@gmail.com")
        ),
        externalDocs = @ExternalDocumentation(description="Spring Boot Blog Ap Documentation"
                                              ,url="https://github.com/Manasmalhotra/BlogApi")
)
public class BlogApiApplication {

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

}
