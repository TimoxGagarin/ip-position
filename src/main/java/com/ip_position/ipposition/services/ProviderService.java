package com.ip_position.ipposition.services;

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

    public void addNewProvider(Provider provider) {
        logger.log(Level.INFO, "{0} was added into table Provider", provider);
        providerRepository.save(provider);
    }
}