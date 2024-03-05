package com.ip_position.ipposition.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.IpInfoRepository;
import com.ip_position.ipposition.validators.IpValidator;

import jakarta.transaction.Transactional;

@Service
public class IpInfoService {

    private final IpInfoRepository ipInfoRepository;
    private final CityService cityService;
    private final ProviderService providerService;
    private final PositionService positionService;
    private Logger logger;
    private final RestTemplate restTemplate;
    private final IpValidator ipValidator;

    public IpInfoService(IpInfoRepository ipInfoRepository, RestTemplate restTemplate, Logger logger,
            IpValidator ipValidator, CityService cityService, ProviderService providerService,
            PositionService positionService) {
        this.ipInfoRepository = ipInfoRepository;

        this.restTemplate = restTemplate;
        this.logger = logger;
        this.ipValidator = ipValidator;
        this.cityService = cityService;
        this.providerService = providerService;
        this.positionService = positionService;
    }

    private static final String IP_INFO_DOESNT_EXIST = "IpInfo with ip %s does not exists";

    public List<IpInfo> getIpsInfo() {
        return ipInfoRepository.findAll();
    }

    public IpInfo getIpInfoFromAPI(String ip) {
        if (!ipValidator.isValidIPAddress(ip))
            throw new IllegalStateException(String.format(IP_INFO_DOESNT_EXIST, ip));

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
                new Position((Double) response.get("lat"), (Double) response.get("lon")),
                response.get("timezone").toString(),
                provider,
                ip);
        logger.log(Level.INFO, "{0} was added into table IpInfo", result);
        return result;
    }

    public IpInfo getIpInfoFromDB(String ip) {
        if (!ipValidator.isValidIPAddress(ip))
            throw new IllegalStateException(String.format(IP_INFO_DOESNT_EXIST, ip));

        return ipInfoRepository.findResponseByIP(ip).orElseThrow(() -> new IllegalStateException(
                String.format("IpInfo with ip %s does not exists in database", ip)));
    }

    public void addNewIpInfo(IpInfo ipInfo) {
        City ipInfoCity = ipInfo.getCity();
        Provider ipInfoProvider = ipInfo.getProvider();
        Position ipInfoPosition = ipInfo.getPosition();

        ipInfoCity.addProvider(ipInfoProvider);
        ipInfoProvider.addCity(ipInfoCity);

        ipInfo.setCity(cityService.addNewCity(ipInfoCity));
        ipInfo.setProvider(providerService.addNewProvider(ipInfoProvider));
        ipInfo.setPosition(positionService.addNewPosition(ipInfoPosition));

        if (!ipInfoRepository.findResponseByIP(ipInfo.getIp()).isPresent())
            ipInfoRepository.save(ipInfo);
    }

    public void deleteIpInfo(Long ipInfoId) {
        Optional<IpInfo> ipInfo = ipInfoRepository.findById(ipInfoId);

        if (!ipInfo.isPresent())
            throw new IllegalStateException(String.format(IP_INFO_DOESNT_EXIST, ipInfoId));

        City city = ipInfo.get().getCity();
        Provider provider = ipInfo.get().getProvider();
        Position position = ipInfo.get().getPosition();
        ipInfoRepository.deleteById(ipInfoId);

        try {
            provider.removeCity(city);
            cityService.deleteCity(city.getId());
        } catch (IllegalStateException exception) {
            logger.warning(exception.getMessage());
        }
        try {
            city.removeProvider(provider);
            providerService.deleteProvider(provider.getId());
        } catch (IllegalStateException exception) {
            logger.warning(exception.getMessage());
        }
        try {
            positionService.deletePosition(position.getId());

        } catch (IllegalStateException exception) {
            logger.warning(exception.getMessage());
        }

        logger.log(Level.INFO, "IpInfo with id={0} was deleted from table IpInfo", ipInfoId);
    }

    @Transactional
    public void updateIpInfo(Long ipInfoId) {
        IpInfo ipInfo = ipInfoRepository.findById(ipInfoId).orElseThrow(() -> new IllegalStateException(
                String.format("IpInfo with id %ld does not exists", ipInfoId)));

        IpInfo realIpInfo = getIpInfoFromAPI(ipInfo.getIp());

        if (!ipInfo.getTimeZone().equals(realIpInfo.getTimeZone())) {
            ipInfo.setTimeZone(realIpInfo.getTimeZone());
            logger.log(Level.INFO, "IpInfo with id={0} was updated field TimeZone on {1}",
                    new Object[] { ipInfoId, ipInfo.getTimeZone() });
        }
        if (!ipInfo.getCity().equals(realIpInfo.getCity())) {
            Long cityId = ipInfo.getCity().getId();
            ipInfo.setCity(cityService.addNewCity(realIpInfo.getCity()));
            logger.log(Level.INFO, "IpInfo with id={0} was updated field City on {1}",
                    new Object[] { ipInfoId, ipInfo.getCity() });
            if (ipInfoRepository.findAllByCityId(cityId).isEmpty())
                cityService.deleteCity(cityId);
        }
        if (!ipInfo.getProvider().equals(realIpInfo.getProvider())) {
            Long providerId = ipInfo.getProvider().getId();
            ipInfo.setProvider(providerService.addNewProvider(realIpInfo.getProvider()));
            logger.log(Level.INFO, "IpInfo with id={0} was updated field Provider on {1}",
                    new Object[] { ipInfoId, ipInfo.getPosition() });
            if (ipInfoRepository.findAllByProviderId(providerId).isEmpty())
                providerService.deleteProvider(providerId);
        }
        if (!ipInfo.getPosition().equals(realIpInfo.getPosition())) {
            Long positionId = ipInfo.getPosition().getId();
            ipInfo.setPosition(positionService.addNewPosition(realIpInfo.getPosition()));
            logger.log(Level.INFO, "IpInfo with id={0} was updated field Position on {1}",
                    new Object[] { ipInfoId, ipInfo.getPosition() });
            if (ipInfoRepository.findAllByPositionId(positionId).isEmpty())
                positionService.deletePosition(positionId);
        }
    }

    public Set<City> getCitiesOfProvider(String providerIsp) {
        Provider provider = providerService.findProviderByIsp(providerIsp);
        if (provider != null)
            return provider.getCities();
        return new HashSet<>();
    }

    public Set<Provider> getProvidersOfCity(String cityName) {
        City city = cityService.findCityByName(cityName);
        if (city != null)
            return city.getProviders();
        return new HashSet<>();
    }
}
