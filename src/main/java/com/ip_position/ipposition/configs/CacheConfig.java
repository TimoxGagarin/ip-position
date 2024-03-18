package com.ip_position.ipposition.configs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.entity.Provider;

@Configuration
public class CacheConfig {
    public static final String ipInfoCache = "findIpInfo_";
    public static final String cityCache = "findCities_";
    public static final String providerCache = "findProviders_";
    public static final String positionCache = "findPositions_";

    @Bean
    public Map<String, List<IpInfo>> memoryCacheIpInfo() {
        return new HashMap<String, List<IpInfo>>();
    }

    @Bean
    public Map<String, List<City>> memoryCacheCity() {
        return new HashMap<String, List<City>>();
    }

    @Bean
    public Map<String, List<Provider>> memoryCacheProvider() {
        return new HashMap<String, List<Provider>>();
    }

    @Bean
    public Map<String, List<Position>> memoryCachePosition() {
        return new HashMap<String, List<Position>>();
    }
}
