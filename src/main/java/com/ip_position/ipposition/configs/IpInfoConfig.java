package com.ip_position.ipposition.configs;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.ip_position.ipposition.validators.IpValidator;

@Configuration
public class IpInfoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Logger logger() {
        return Logger.getLogger(getClass().getName());
    }

    @Bean
    public IpValidator ipValidator() {
        return new IpValidator();
    }
}