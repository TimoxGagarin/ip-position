package com.ip_position.ipposition.ipinfo;

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

import com.ip_position.ipposition.city.City;
import com.ip_position.ipposition.city.CityRepository;
import com.ip_position.ipposition.latlng.LatLng;
import com.ip_position.ipposition.latlng.LatLngRepository;
import com.ip_position.ipposition.provider.Provider;
import com.ip_position.ipposition.provider.ProviderRepository;

@Service
public class IpInfoService {

    // Repositories for handling database operations
    private final IpInfoRepository ipInfoRepository;
    private final CityRepository cityRepository;
    private final ProviderRepository providerRepository;
    private final LatLngRepository latLngRepository;

    // Logger for logging information
    private Logger logger;

    // RestTemplate for making HTTP requests
    private final RestTemplate restTemplate;

    // Constructor-based dependency injection
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

    // Method to get information for all IPs in the database
    public List<IpInfo> getIpsInfo() {
        return ipInfoRepository.findAll();
    }

    // Method to get information for a specific IP
    public IpInfo getIpInfo(String ip) {
        // Construct the API endpoint for IP information
        String endpoint = "http://ip-api.com/json/" + ip;

        // Make a GET request to the IP information API
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                });
        Map<String, Object> response = responseEntity.getBody();
        City city;
        Provider provider;

        // Check if the response is successful and contains necessary information
        if (response != null && response.containsKey("status") && response.get("status").equals("success")) {
            // Create City and Provider objects from the response
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
            // Return null if the response is not successful
            return null;
        }

        // Create IpInfo object from the response
        IpInfo result = new IpInfo(
                city,
                new LatLng((Double) response.get("lat"), (Double) response.get("lon")),
                response.get("timezone").toString(),
                provider,
                ip);
        // Log information about the added IpInfo
        logger.log(Level.INFO, "{0} was added into table IpInfo", result);
        return result;
    }

    // Method to add new IpInfo
    public void addNewIpInfo(IpInfo ipInfo) {
        // Extract components (City, Provider, LatLng) from IpInfo
        City ipInfoCity = ipInfo.getCity();
        Provider ipInfoProvider = ipInfo.getProvider();
        LatLng ipInfoLatLng = ipInfo.getPosition();

        // Check if the City already exists in the database
        Optional<City> existingCity = cityRepository.findCityByCityRegionCountry(
                ipInfoCity.getCityName(),
                ipInfoCity.getRegionName(),
                ipInfoCity.getCountry());

        if (existingCity.isPresent()) {
            // If City exists, update the IpInfo with the existing City
            ipInfo.setCity(existingCity.get());
        } else {
            // If City does not exist, save the new City in the database
            cityRepository.save(ipInfoCity);
        }

        // Check if the Provider already exists in the database
        Optional<Provider> existingProvider = providerRepository.findProviderByIsp(ipInfoProvider.getIsp());

        if (existingProvider.isPresent()) {
            // If Provider exists, update the IpInfo with the existing Provider
            ipInfo.setProvider(existingProvider.get());
        } else {
            // If Provider does not exist, save the new Provider in the database
            providerRepository.save(ipInfoProvider);
        }

        // Check if the LatLng already exists in the database
        Optional<LatLng> existingLatLng = latLngRepository.findPositionByLatLng(
                ipInfoLatLng.getLatitude(),
                ipInfoLatLng.getLongitude());

        if (existingLatLng.isPresent()) {
            // If LatLng exists, update the IpInfo with the existing LatLng
            ipInfo.setPosition(existingLatLng.get());
        } else {
            // If LatLng does not exist, save the new LatLng in the database
            latLngRepository.save(ipInfoLatLng);
        }

        // Save the IpInfo in the database
        ipInfoRepository.save(ipInfo);
    }
}
