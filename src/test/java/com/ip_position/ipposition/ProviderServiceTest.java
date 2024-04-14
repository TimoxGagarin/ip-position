package com.ip_position.ipposition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.ProviderRepository;
import com.ip_position.ipposition.services.ProviderService;

class ProviderServiceTest {

    @Mock
    private ProviderRepository providerRepository;

    private Map<String, List<Provider>> cacheMap;
    private Logger logger;

    private ProviderService providerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheMap = new HashMap<>();
        logger = Logger.getLogger(ProviderService.class.getName());
        providerService = new ProviderService(providerRepository, cacheMap, logger);
    }

    @Test
    void addNewProvider_Success() {
        Provider providerToAdd = new Provider("Amazon Technologies Inc.", "AWS EC2 (ap-southeast-1)",
                "AS16509 Amazon.com, Inc.");

        when(providerRepository.findProvider(providerToAdd)).thenReturn(new ArrayList<>());
        when(providerRepository.save(providerToAdd)).thenReturn(providerToAdd);

        Provider addedProvider = providerService.addNewProvider(providerToAdd);

        assertEquals(providerToAdd, addedProvider);
        verify(providerRepository, times(1)).findProvider(providerToAdd);
        verify(providerRepository, times(1)).save(providerToAdd);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void findProviders_CacheHit() {
        Provider providerToFind = new Provider("Amazon Technologies Inc.", "AWS EC2 (ap-southeast-1)",
                "AS16509 Amazon.com, Inc.");
        List<Provider> cachedProviders = new ArrayList<>();
        cachedProviders.add(new Provider("Amazon Technologies Inc.", "AWS EC2 (ap-southeast-1)",
                "AS16509 Amazon.com, Inc."));

        String cacheKey = CacheConfig.PROVIDER_CACHE_START + providerToFind.toString();
        cacheMap.put(cacheKey, cachedProviders);

        List<Provider> foundProviders = providerService.findProviders(providerToFind);

        assertEquals(cachedProviders, foundProviders);
        assertEquals(cachedProviders, cacheMap.get(cacheKey));
        verify(providerRepository, never()).findProvider(providerToFind);
    }

    @Test
    void deleteProvider_Success() {
        Long providerId = 1L;

        when(providerRepository.hasReferences(providerId)).thenReturn(false);

        providerService.deleteProvider(providerId);

        verify(providerRepository, times(1)).clearRelations(providerId);
        verify(providerRepository, times(1)).deleteById(providerId);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void deleteProvider_WithReferences_ExceptionThrown() {
        Long providerId = 1L;

        when(providerRepository.hasReferences(providerId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> providerService.deleteProvider(providerId));

        verify(providerRepository, never()).clearRelations(providerId);
        verify(providerRepository, never()).deleteById(providerId);
        assertTrue(cacheMap.isEmpty());
    }
}