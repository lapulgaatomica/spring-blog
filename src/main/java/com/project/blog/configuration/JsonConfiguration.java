package com.project.blog.configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfiguration {
    //customizes our JSON serialization so it can handle Hibernate objects
    //helps to prevent error when some entities are fetched in lazy mode
    @Bean
    public Module hibernateModule(){
        return new Hibernate5Module();
    }
}
