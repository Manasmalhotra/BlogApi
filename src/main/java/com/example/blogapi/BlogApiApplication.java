package com.example.blogapi;

import com.example.blogapi.role.RoleEntity;
import com.example.blogapi.role.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
public class BlogApiApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }
	public static void main(String[] args) {
		SpringApplication.run(BlogApiApplication.class, args);
	}

    @Autowired
    RoleRepository roleRepository;



    @Override
    public void run(String... args) throws Exception {
        /*
        RoleEntity role1=new RoleEntity();
        role1.setName("ROLE_ADMIN");
        roleRepository.save(role1);

        RoleEntity role2=new RoleEntity();
        role2.setName("ROLE_USER");
        roleRepository.save(role2);
     */
    }
}
