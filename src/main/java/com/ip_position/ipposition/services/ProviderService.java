package com.ip_position.ipposition.services;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.repositories.ProviderRepository;

@Service
public class ProviderService {
    private ProviderRepository providerRepository;
    private Logger logger;

    public ProviderService(ProviderRepository providerRepository, Logger logger) {
        this.providerRepository = providerRepository;
        this.logger = logger;
    }

    public Provider addNewProvider(Provider provider) {
        logger.log(Level.INFO, "{0} was added into table Provider", provider.toString().replaceAll("[\n\r]", "_"));

        Optional<Provider> existingProvider = providerRepository.findProviderByAll(provider);
        if (!existingProvider.isPresent()) {
            providerRepository.save(provider);
            return provider;
        }
        return existingProvider.get();
    }

    public Provider findProviderByIsp(String providerIsp) {
        Optional<Provider> provider = providerRepository.findProviderByInternetServiceProvider(providerIsp);
        if (provider.isPresent())
            return provider.get();
        return null;
    }

    public void deleteProvider(Long providerId) {
        boolean hasReferences = providerRepository.hasReferences(providerId);

        if (hasReferences) {
            throw new IllegalStateException("provider with id " + providerId + " does not exists or has references");
        }
        providerRepository.clearRelations(providerId);
        providerRepository.deleteById(providerId);
        logger.log(Level.INFO, "Provider with id={0} was deleted from table Provider", providerId);
    }
}