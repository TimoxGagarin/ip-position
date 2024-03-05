package com.ip_position.ipposition.services;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.repositories.PositionRepository;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private Logger logger;

    public PositionService(PositionRepository positionRepository, Logger logger) {
        this.positionRepository = positionRepository;
        this.logger = logger;
    }

    public Position addNewPosition(Position position) {
        logger.log(Level.INFO, "{0} was added into table LatLng", position.toString().replaceAll("[\n\r]", "_"));

        Optional<Position> existingPosition = positionRepository.findPositionByLatLng(position.getLatitude(),
                position.getLongitude());

        if (!existingPosition.isPresent()) {
            positionRepository.save(position);
            return position;
        }
        return existingPosition.get();
    }

    public void deletePosition(Long positionId) {
        boolean hasReferences = positionRepository.hasReferences(positionId);

        if (hasReferences) {
            throw new IllegalStateException("position with id " + positionId + " does not exists or has references");
        }
        positionRepository.deleteById(positionId);
        logger.log(Level.INFO, "Position with id={0} was deleted from table Position", positionId);
    }
}
