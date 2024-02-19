package com.ip_position.ipposition.latlng;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

// Service annotation indicating that this class is a Spring service
@Service
public class LatLngService {

    // Dependency injection of LatLngRepository and Logger
    private final LatLngRepository latLngRepository;
    private Logger logger;

    // Constructor-based dependency injection
    public LatLngService(LatLngRepository latLngRepository, Logger logger) {
        this.latLngRepository = latLngRepository;
        this.logger = logger;
    }

    // Method to add a new LatLng position to the database
    public void addNewLatLng(LatLng position) {
        // Log information about the added LatLng position
        logger.log(Level.INFO, "{0} was added into table LatLng", position);

        // Save the LatLng position using the injected LatLngRepository
        latLngRepository.save(position);
    }
}
