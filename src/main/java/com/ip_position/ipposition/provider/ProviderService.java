package com.ip_position.ipposition.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

// Service annotation indicating that this class is a Spring service
@Service
public class ProviderService {

    // Dependency injection of ProviderRepository and Logger
    private ProviderRepository providerRepository;
    private Logger logger;

    // Constructor-based dependency injection
    public ProviderService(ProviderRepository providerRepository, Logger logger) {
        this.providerRepository = providerRepository;
        this.logger = logger;
    }

    // Method to add a new Provider to the database
    public void addNewProvider(Provider provider) {
        // Log information about the added Provider
        logger.log(Level.INFO, "{0} was added into table Provider", provider);

        // Save the Provider using the injected ProviderRepository
        providerRepository.save(provider);
    }
}