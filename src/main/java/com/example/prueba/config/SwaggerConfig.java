package com.example.prueba.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

// HTTP http://localhost:8080/swagger-ui/
// JSON http://localhost:8080/v2/api-docs

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails() {
        return new ApiInfo("Spring Boot Books API REST",
                "Library Api rest docs",
                "1.0",
                "http://www.pabloalbin.com",
                new Contact("Pablo", "http://www.pabloalbin.com", "pablo.albin88@gmail.com"),
                "MIT",
                "http://www.pabloalbin.com",
                Collections.emptyList());
    }
}
