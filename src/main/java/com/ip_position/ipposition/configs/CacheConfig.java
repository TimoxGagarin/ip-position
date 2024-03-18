package com.ip_position.ipposition.configs;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.entity.Provider;

@Configuration
public class CacheConfig {

    @Bean
    public HashMap<String, List<IpInfo>> memoryCacheIpInfo() {
        return new HashMap<String, List<IpInfo>>();
    }

    @Bean
    public HashMap<String, List<City>> memoryCacheCity() {
        return new HashMap<String, List<City>>();
    }

    @Bean
    public HashMap<String, List<Provider>> memoryCacheProvider() {
        return new HashMap<String, List<Provider>>();
    }

    @Bean
    public HashMap<String, List<Position>> memoryCachePosition() {
        return new HashMap<String, List<Position>>();
    }
}
