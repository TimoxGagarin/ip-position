package com.ip_position.ipposition.ipinfo;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// Configuration annotation indicating that this class provides configuration to the Spring application context
@Configuration
public class IpInfoConfig {

    // Bean definition for RestTemplate, which is used for making HTTP requests
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Bean definition for Logger, which is used for logging
    @Bean
    public Logger logger() {
        // Obtain a Logger instance using the class name
        return Logger.getLogger(getClass().getName());
    }
}