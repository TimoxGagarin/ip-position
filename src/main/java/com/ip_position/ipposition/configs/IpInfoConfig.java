package com.ip_position.ipposition.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.ip_position.ipposition.dto.IpInfoDTO;

@EnableWebMvc
@Configuration
public class IpInfoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IpInfoDTO ipValidator() {
        return new IpInfoDTO();
    }
}