package com.ip_position.ipposition;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.IpInfoRepository;
import com.ip_position.ipposition.services.CityService;
import com.ip_position.ipposition.services.IpInfoService;
import com.ip_position.ipposition.services.PositionService;
import com.ip_position.ipposition.services.ProviderService;

class IpInfoServiceTest {

    @Mock
    private IpInfoRepository ipInfoRepository;

    @Mock
    private CityService cityService;

    @Mock
    private ProviderService providerService;

    @Mock
    private PositionService positionService;

    @Mock
    private RestTemplate restTemplate;

    private IpInfoService ipInfoService;

    private Map<String, List<IpInfo>> cacheMap;
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheMap = new HashMap<>();
        logger = Logger.getLogger(IpInfoService.class.getName());
        ipInfoService = spy(new IpInfoService(ipInfoRepository, cityService,
                providerService, positionService, cacheMap,
                logger, restTemplate));
    }

    @Test
    void getIpInfoFromAPI_ValidIp_Success() {
        String validIp = "8.8.8.8";
        City city = new City("US", "USA", "California", "CA", "Mountain View",
                "94043");
        Provider provider = new Provider("Google", "Google LLC", "AS15169");
        Position position = new Position(37.386, -122.0838);
        IpInfo expectedIpInfo = new IpInfo(city, position, "America/Los_Angeles",
                provider, validIp);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("country", "US");
        responseBody.put("countryCode", "USA");
        responseBody.put("region", "California");
        responseBody.put("regionName", "CA");
        responseBody.put("city", "Mountain View");
        responseBody.put("zip", "94043");
        responseBody.put("lat", 37.386);
        responseBody.put("lon", -122.0838);
        responseBody.put("timezone", "America/Los_Angeles");
        responseBody.put("isp", "Google");
        responseBody.put("org", "Google LLC");
        responseBody.put("as", "AS15169");

        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {
        };

        when((ResponseEntity<Map<String, Object>>) restTemplate.exchange(anyString(), any(HttpMethod.class),
                any(),
                eq(typeRef)))
                .thenReturn(ResponseEntity.ok(responseBody));

        IpInfo ipInfo = ipInfoService.getIpInfoFromAPI(validIp);

        assertNotNull(ipInfo);
        assertEquals(expectedIpInfo, ipInfo);
    }

    @Test
    void getIpInfoFromAPI_ValidIp_Fail() {
        String validIp = "8.8.8.8";

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", "success");
        responseBody.put("country", "US");
        responseBody.put("countryCode", "USA");
        responseBody.put("region", "California");
        responseBody.put("regionName", "CA");
        responseBody.put("city", "Mountain View");
        responseBody.put("zip", "94043");
        responseBody.put("lat", 37.386);
        responseBody.put("lon", -122.0838);
        responseBody.put("timezone", "America/Los_Angeles");
        responseBody.put("isp", "Google");
        responseBody.put("org", "Google LLC");
        responseBody.put("as", "AS15169");

        ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {
        };

        when((ResponseEntity<Map<String, Object>>) restTemplate.exchange(anyString(), any(HttpMethod.class),
                any(),
                eq(typeRef)))
                .thenReturn(ResponseEntity.ok(null));
        IpInfo ipInfo = ipInfoService.getIpInfoFromAPI(validIp);
        assertNull(ipInfo);

        responseBody.put("status", "fail");
        when((ResponseEntity<Map<String, Object>>) restTemplate.exchange(anyString(), any(HttpMethod.class),
                any(),
                eq(typeRef)))
                .thenReturn(ResponseEntity.ok(responseBody));
        ipInfo = ipInfoService.getIpInfoFromAPI(validIp);
        assertNull(ipInfo);

        responseBody.remove("status");
        when((ResponseEntity<Map<String, Object>>) restTemplate.exchange(anyString(), any(HttpMethod.class),
                any(),
                eq(typeRef)))
                .thenReturn(ResponseEntity.ok(responseBody));
        ipInfo = ipInfoService.getIpInfoFromAPI(validIp);
        assertNull(ipInfo);
    }

    @Test
    void getIpInfoFromAPI_InvalidIp_ExceptionThrown() {
        String invalidIp = "invalid_ip";

        assertThrows(IllegalStateException.class, () -> ipInfoService.getIpInfoFromAPI(invalidIp));
    }

    @Test
    void addNewIpInfo_Success() {
        IpInfo ipInfo = new IpInfo(new City("Australia", "AU", "QLD", "Queensland", "South Brisbane", "4101"),
                new Position(-27.4766, 153.0166), "America/New_York",
                new Provider("Cloudflare, Inc", "APNIC and Cloudflare DNS Resolver project",
                        "AS13335 Cloudflare, Inc."),
                "1.1.1.1");

        when(cityService.addNewCity(any())).thenReturn(ipInfo.getCity());
        when(providerService.addNewProvider(any())).thenReturn(ipInfo.getProvider());
        when(positionService.addNewPosition(any())).thenReturn(ipInfo.getPosition());
        when(ipInfoRepository.findIpInfo(any())).thenReturn(new ArrayList<>());

        ipInfoService.addNewIpInfo(ipInfo);

        verify(cityService, times(1)).addNewCity(any());
        verify(providerService, times(1)).addNewProvider(any());
        verify(positionService, times(1)).addNewPosition(any());
        verify(ipInfoRepository, times(1)).save(ipInfo);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void addNewIpInfo_WithExistingIp_Success() {
        IpInfo existingIpInfo = new IpInfo(
                new City("Australia", "AU", "QLD", "Queensland", "South Brisbane", "4101"),
                new Position(-27.4766, 153.0166), "America/New_York",
                new Provider("Cloudflare, Inc", "APNIC and Cloudflare DNS Resolver project",
                        "AS13335 Cloudflare, Inc."),
                "1.1.1.1");
        List<IpInfo> existingIpInfoList = new ArrayList<>();
        existingIpInfoList.add(existingIpInfo);

        when(ipInfoRepository.findIpInfo(existingIpInfo)).thenReturn(existingIpInfoList);

        ipInfoService.addNewIpInfo(existingIpInfo);

        verify(cityService, never()).addNewCity(any());
        verify(providerService, never()).addNewProvider(any());
        verify(positionService, never()).addNewPosition(any());
        verify(ipInfoRepository, never()).save(existingIpInfo);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void findIpInfoFromDB_CacheHit() {
        IpInfo ipInfoToFind = new IpInfo(new City(), new Position(),
                "America/New_York", new Provider(), "127.0.0.1");
        List<IpInfo> cachedIpInfos = new ArrayList<>();
        cachedIpInfos.add(ipInfoToFind);

        String cacheKey = CacheConfig.IP_INFO_CACHE_START + ipInfoToFind.toString();
        cacheMap.put(cacheKey, cachedIpInfos);

        List<IpInfo> foundIpInfos = ipInfoService.findIpInfoFromDB(ipInfoToFind);

        assertEquals(cachedIpInfos, foundIpInfos);
        assertEquals(cachedIpInfos, cacheMap.get(cacheKey));
        verify(ipInfoRepository, never()).findIpInfo(ipInfoToFind);
    }

    @Test
    void findIpInfoFromDB_noCacheHit() {
        IpInfo ipInfoToFind = new IpInfo(new City(), new Position(),
                "America/New_York", new Provider(), "127.0.0.1");
        List<IpInfo> cachedIpInfos = new ArrayList<>();
        cachedIpInfos.add(ipInfoToFind);

        String cacheKey = CacheConfig.IP_INFO_CACHE_START + ipInfoToFind.toString();

        List<IpInfo> foundIpInfos = ipInfoService.findIpInfoFromDB(ipInfoToFind);

        assertNotEquals(cachedIpInfos, foundIpInfos);
        assertNotEquals(cachedIpInfos, cacheMap.get(cacheKey));
        verify(ipInfoRepository, times(1)).findIpInfo(ipInfoToFind);
    }

    @Test
    void deleteIpInfo_Success() {
        Long ipInfoId = 1L;
        IpInfo ipInfo = new IpInfo(new City("Australia", "AU", "QLD", "Queensland", "South Brisbane", "4101"),
                new Position(-27.4766, 153.0166), "America/New_York",
                new Provider("Cloudflare, Inc", "APNIC and Cloudflare DNS Resolver project",
                        "AS13335 Cloudflare, Inc."),
                "1.1.1.1");

        when(ipInfoRepository.findById(ipInfoId)).thenReturn(java.util.Optional.of(ipInfo));
        doNothing().when(cityService).deleteCity(any());
        doNothing().when(providerService).deleteProvider(any());
        doNothing().when(positionService).deletePosition(any());

        ipInfoService.deleteIpInfo(ipInfoId);

        verify(ipInfoRepository, times(1)).deleteById(ipInfoId);
        verify(cityService, times(1)).deleteCity(ipInfo.getCity().getId());
        verify(providerService,
                times(1)).deleteProvider(ipInfo.getProvider().getId());
        verify(positionService,
                times(1)).deletePosition(ipInfo.getPosition().getId());
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void deleteIpInfo_Fail() {
        Long ipInfoId = 1L;
        when(ipInfoRepository.findById(ipInfoId)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            ipInfoService.deleteIpInfo(ipInfoId);
        });
    }

    @Test
    void updateIpInfo_Success() {
        Long ipInfoId = 1L;
        IpInfo oldIpInfo = new IpInfo(
                new City("Avstralia", "AU", "QLD", "Queensland", "South Brisbane", "4101"),
                new Position(-127.4766, 153.0166), "America/New_York",
                new Provider("Cloudflare, Incorporated", "APNIC and Cloudflare DNS Resolver project",
                        "AS13335 Cloudflare, Inc."),
                "1.1.1.1");

        IpInfo newIpInfo = new IpInfo(
                new City("Australia", "AU", "QLD", "Queensland", "South Brisbane", "4101"),
                new Position(-27.4766, 153.0166), "Americanci/New_York",
                new Provider("Cloudflare, Inc", "APNIC and Cloudflare DNS Resolver project",
                        "AS13335 Cloudflare, Inc."),
                "1.1.1.1");

        when(ipInfoRepository.findById(ipInfoId)).thenReturn(Optional.of(oldIpInfo));
        Mockito.doReturn(newIpInfo).when(ipInfoService).getIpInfoFromAPI(oldIpInfo.getIp());
        when(cityService.addNewCity(newIpInfo.getCity())).thenReturn(newIpInfo.getCity());
        when(providerService.addNewProvider(newIpInfo.getProvider())).thenReturn(newIpInfo.getProvider());
        when(positionService.addNewPosition(newIpInfo.getPosition())).thenReturn(newIpInfo.getPosition());

        ipInfoService.updateIpInfo(ipInfoId);

        assertEquals(oldIpInfo.getTimeZone(), newIpInfo.getTimeZone());
        verify(cityService, times(1)).addNewCity(newIpInfo.getCity());
        verify(providerService, times(1)).addNewProvider(newIpInfo.getProvider());
        verify(positionService, times(1)).addNewPosition(newIpInfo.getPosition());
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void updateIpInfo_Fail() {
        Long ipInfoId = 1L;
        when(ipInfoRepository.findById(ipInfoId)).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class, () -> {
            ipInfoService.updateIpInfo(ipInfoId);
        });
    }
}