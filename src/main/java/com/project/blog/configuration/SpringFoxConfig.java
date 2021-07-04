package com.project.blog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(new ArrayList<>(Arrays.asList(new ApiKey("Bearer %token", "Authorization", "Header"))));
    }

    private ApiInfo apiInfo(){
        return  new ApiInfoBuilder()
                .title("Blog API")
                .version("1.0")
                .description("API for Blog Application")
                .contact(new Contact("Odedoyin Akindele", "https://www.github.com/lapulgaatomica", "akindeleodedoyin@gmail.com"))
                .build();
    }
}
