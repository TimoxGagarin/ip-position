package com.ip_position.ipposition.configs;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.ip_position.ipposition.utils.RequestCounter;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan
public class AppConfig {
    @Bean
    public Logger logger() {
        return Logger.getLogger(getClass().getName());
    }

    @Bean
    public RequestCounter requestCounter() {
        return new RequestCounter();
    }
}