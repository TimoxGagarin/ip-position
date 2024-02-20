package com.ip_position.ipposition.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.LatLng;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.CityRepository;
import com.ip_position.ipposition.repositories.IpInfoRepository;
import com.ip_position.ipposition.repositories.LatLngRepository;
import com.ip_position.ipposition.repositories.ProviderRepository;

@Service
public class IpInfoService {

    private final IpInfoRepository ipInfoRepository;
    private final CityRepository cityRepository;
    private final ProviderRepository providerRepository;
    private final LatLngRepository latLngRepository;
    private Logger logger;
    private final RestTemplate restTemplate;

    public IpInfoService(IpInfoRepository ipInfoRepository, CityRepository cityRepository,
            ProviderRepository providerRepository,
            LatLngRepository latLngRepository, RestTemplate restTemplate, Logger logger) {
        this.ipInfoRepository = ipInfoRepository;
        this.cityRepository = cityRepository;
        this.providerRepository = providerRepository;
        this.latLngRepository = latLngRepository;

        this.restTemplate = restTemplate;
        this.logger = logger;
    }

    public List<IpInfo> getIpsInfo() {
        return ipInfoRepository.findAll();
    }

    public IpInfo getIpInfo(String ip) {
        String endpoint = "http://ip-api.com/json/" + ip;
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        Map<String, Object> response = responseEntity.getBody();
        City city;
        Provider provider;

        if (response != null && response.containsKey("status") && response.get("status").equals("success")) {
            city = new City(
                    response.get("country").toString(),
                    response.get("countryCode").toString(),
                    response.get("region").toString(),
                    response.get("regionName").toString(),
                    response.get("city").toString(),
                    response.get("zip").toString());
            provider = new Provider(
                    response.get("isp").toString(),
                    response.get("org").toString(),
                    response.get("as").toString());
        } else {
            return null;
        }

        IpInfo result = new IpInfo(
                city,
                new LatLng((Double) response.get("lat"), (Double) response.get("lon")),
                response.get("timezone").toString(),
                provider,
                ip);
        logger.log(Level.INFO, "{0} was added into table IpInfo", result);
        return result;
    }

    // Method to add new IpInfo
    public void addNewIpInfo(IpInfo ipInfo) {
        City ipInfoCity = ipInfo.getCity();
        Provider ipInfoProvider = ipInfo.getProvider();
        LatLng ipInfoLatLng = ipInfo.getPosition();
        Optional<City> existingCity = cityRepository.findCityByCityRegionCountry(
                ipInfoCity.getCityName(),
                ipInfoCity.getRegionName(),
                ipInfoCity.getCountry());

        if (existingCity.isPresent()) {
            ipInfo.setCity(existingCity.get());
        } else {
            cityRepository.save(ipInfoCity);
        }

        Optional<Provider> existingProvider = providerRepository.findProviderByIsp(ipInfoProvider.getIsp());

        if (existingProvider.isPresent()) {
            ipInfo.setProvider(existingProvider.get());
        } else {
            providerRepository.save(ipInfoProvider);
        }

        Optional<LatLng> existingLatLng = latLngRepository.findPositionByLatLng(
                ipInfoLatLng.getLatitude(),
                ipInfoLatLng.getLongitude());

        if (existingLatLng.isPresent()) {
            ipInfo.setPosition(existingLatLng.get());
        } else {
            latLngRepository.save(ipInfoLatLng);
        }

        ipInfoRepository.save(ipInfo);
    }
}
