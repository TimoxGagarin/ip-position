package com.ip_position.ipposition.services;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ip_position.ipposition.entity.LatLng;
import com.ip_position.ipposition.repositories.LatLngRepository;

@Service
public class LatLngService {

    private final LatLngRepository latLngRepository;
    private Logger logger;

    public LatLngService(LatLngRepository latLngRepository, Logger logger) {
        this.latLngRepository = latLngRepository;
        this.logger = logger;
    }

    public void addNewLatLng(LatLng position) {
        logger.log(Level.INFO, "{0} was added into table LatLng", position);
        latLngRepository.save(position);
    }
}
