package com.ip_position.ipposition.services;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.dto.IpInfoDTO;
import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.IpInfoRepository;

import jakarta.transaction.Transactional;

@Service
public class IpInfoService {

    private static final String IP_INFO_DOESNT_EXIST = "IpInfo with ip %s does not exists";

    private final IpInfoRepository ipInfoRepository;
    private final CityService cityService;
    private final ProviderService providerService;
    private final PositionService positionService;
    private final Map<String, List<IpInfo>> cacheMap;
    private final Logger logger;
    private final RestTemplate restTemplate;

    public IpInfoService(IpInfoRepository ipInfoRepository, CityService cityService,
            ProviderService providerService, PositionService positionService, Map<String, List<IpInfo>> cacheMap,
            Logger logger, RestTemplate restTemplate) {
        this.ipInfoRepository = ipInfoRepository;
        this.cityService = cityService;
        this.providerService = providerService;
        this.positionService = positionService;
        this.cacheMap = cacheMap;
        this.logger = logger;
        this.restTemplate = restTemplate;
    }

    public IpInfo getIpInfoFromAPI(String ip) {
        if (!ip.matches(IpInfoDTO.IP_REGEX)) {
            throw new IllegalStateException("Not valid ip");
        }
        String endpoint = "http://ip-api.com/json/" + ip;
        ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                endpoint,
                Objects.requireNonNull(HttpMethod.GET),
                null,
                new ParameterizedTypeReference<>() {
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

        return new IpInfo(
                city,
                new Position((Double) response.get("lat"), (Double) response.get("lon")),
                response.get("timezone").toString(),
                provider,
                ip);
    }

    public List<IpInfo> findIpInfoFromDB(IpInfo ipInfo) {
        String cacheKey = CacheConfig.IP_INFO_CACHE_START + ipInfo.toString();
        if (cacheMap.containsKey(cacheKey)) {
            logger.info(() -> String.format("Cache %s value:%n%s", cacheKey, cacheMap.get(cacheKey).toString()));
            return cacheMap.get(cacheKey);
        }
        List<IpInfo> result = ipInfoRepository.findIpInfo(ipInfo);
        cacheMap.put(cacheKey, result);
        return ipInfoRepository.findIpInfo(ipInfo);
    }

    public void addNewIpInfo(IpInfo ipInfo) {
        cacheMap.clear();

        City ipInfoCity = cityService.addNewCity(ipInfo.getCity());
        Provider ipInfoProvider = providerService.addNewProvider(ipInfo.getProvider());
        Position ipInfoPosition = positionService.addNewPosition(ipInfo.getPosition());

        ipInfo.setCity(ipInfoCity);
        ipInfo.setProvider(ipInfoProvider);
        ipInfo.setPosition(ipInfoPosition);

        ipInfoCity.addProvider(ipInfoProvider);
        ipInfoProvider.addCity(ipInfoCity);
        if (ipInfoRepository.findIpInfo(ipInfo).isEmpty())
            ipInfoRepository.save(ipInfo);
    }

    @Transactional
    public void deleteIpInfo(@NonNull Long ipInfoId) {
        Optional<IpInfo> ipInfo = ipInfoRepository.findById(ipInfoId);

        if (ipInfo.isEmpty())
            throw new IllegalStateException(String.format(IP_INFO_DOESNT_EXIST, ipInfoId));

        cacheMap.clear();

        City city = ipInfo.get().getCity();
        Provider provider = ipInfo.get().getProvider();
        Position position = ipInfo.get().getPosition();
        ipInfoRepository.deleteById(ipInfoId);

        try {
            provider.removeCity(city);
            cityService.deleteCity(Objects.requireNonNull(city.getId()));
        } catch (IllegalStateException exception) {
            logger.warning(exception.getMessage());
        }
        try {
            city.removeProvider(provider);
            providerService.deleteProvider(Objects.requireNonNull(provider.getId()));
        } catch (IllegalStateException exception) {
            logger.warning(exception.getMessage());
        }
        try {
            positionService.deletePosition(Objects.requireNonNull(position.getId()));

        } catch (IllegalStateException exception) {
            logger.warning(exception.getMessage());
        }
    }

    @Transactional
    public void updateIpInfo(@NonNull Long ipInfoId) {
        IpInfo ipInfo = ipInfoRepository.findById(ipInfoId).orElseThrow(() -> new IllegalStateException(
                String.format("IpInfo with id %d does not exists", ipInfoId)));

        cacheMap.clear();

        IpInfo realIpInfo = getIpInfoFromAPI(ipInfo.getIp());

        City prevCity = ipInfo.getCity();
        Provider prevProvider = ipInfo.getProvider();
        Position prevPosition = ipInfo.getPosition();

        if (!ipInfo.getTimeZone().equals(realIpInfo.getTimeZone())) {
            ipInfo.setTimeZone(realIpInfo.getTimeZone());
            logger.info(() -> String.format("IpInfo with id=%d was updated field TimeZone on %s", ipInfoId,
                    ipInfo.getTimeZone()));
        }
        if (!ipInfo.getCity().equals(realIpInfo.getCity())) {
            ipInfo.setCity(cityService.addNewCity(realIpInfo.getCity()));
            logger.info(() -> String.format("IpInfo with id=%d was updated field City on %s", ipInfoId,
                    ipInfo.getCity().toString()));
            IpInfo filter = new IpInfo(null, prevCity, null, null, null, null);
            if (ipInfoRepository.findIpInfo(filter).isEmpty())
                cityService.deleteCity(Objects.requireNonNull(prevCity.getId()));
        }
        if (!ipInfo.getProvider().equals(realIpInfo.getProvider())) {
            ipInfo.setProvider(providerService.addNewProvider(realIpInfo.getProvider()));
            logger.info(() -> String.format("IpInfo with id=%d was updated field Provider on %s", ipInfoId,
                    ipInfo.getProvider().toString()));
            IpInfo filter = new IpInfo(null, null, null, null, prevProvider, null);
            if (ipInfoRepository.findIpInfo(filter).isEmpty())
                providerService.deleteProvider(Objects.requireNonNull(prevProvider.getId()));
        }
        if (!ipInfo.getPosition().equals(realIpInfo.getPosition())) {
            ipInfo.setPosition(positionService.addNewPosition(realIpInfo.getPosition()));
            logger.info(() -> String.format("IpInfo with id=%d was updated field Position on %s", ipInfoId,
                    ipInfo.getPosition().toString()));
            IpInfo filter = new IpInfo(null, null, prevPosition, null, null, null);
            if (ipInfoRepository.findIpInfo(filter).isEmpty())
                positionService.deletePosition(Objects.requireNonNull(prevPosition.getId()));
        }
    }
}