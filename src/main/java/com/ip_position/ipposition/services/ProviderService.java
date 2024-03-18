package com.ip_position.ipposition.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.ProviderRepository;

@Service
public class ProviderService {
    private final ProviderRepository providerRepository;
    private final Map<String, List<Provider>> cacheMap;

    @Autowired
    private Logger logger;

    public ProviderService(ProviderRepository providerRepository, HashMap<String, List<Provider>> cacheMap) {
        this.providerRepository = providerRepository;
        this.cacheMap = cacheMap;
    }

    public Provider addNewProvider(Provider provider) {
        List<Provider> existingProvider = providerRepository.findProvider(provider);
        if (existingProvider.isEmpty()) {
            cacheMap.clear();
            providerRepository.save(provider);
            return provider;
        }
        return existingProvider.get(0);
    }

    public List<Provider> findProviders(Provider provider) {
        String providerCache = CacheConfig.providerCache + provider.toString();
        if (cacheMap.containsKey(providerCache)) {
            logger.info(String.format("Cache %s value:\n%s", providerCache, cacheMap.get(providerCache).toString()));
            return cacheMap.get(providerCache);
        }
        List<Provider> result = providerRepository.findProvider(provider);
        cacheMap.put(providerCache, result);
        return result;
    }

    public void deleteProvider(@NonNull Long providerId) {
        boolean hasReferences = providerRepository.hasReferences(providerId);

        if (hasReferences) {
            throw new IllegalStateException("Provider with id " + providerId + " does not exists or has references");
        }

        cacheMap.clear();
        providerRepository.clearRelations(providerId);
        providerRepository.deleteById(providerId);
    }
}