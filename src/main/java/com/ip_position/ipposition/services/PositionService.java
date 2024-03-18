package com.ip_position.ipposition.services;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ip_position.ipposition.configs.CacheConfig;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.repositories.PositionRepository;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final Map<String, List<Position>> cacheMap;
    private final Logger logger;

    public PositionService(PositionRepository positionRepository, Map<String, List<Position>> cacheMap, Logger logger) {
        this.positionRepository = positionRepository;
        this.cacheMap = cacheMap;
        this.logger = logger;
    }

    public Position addNewPosition(Position position) {
        List<Position> positionList = positionRepository.findPosition(position);
        if (positionList.isEmpty()) {
            cacheMap.clear();
            positionRepository.save(position);
            return position;
        }
        return positionList.get(0);
    }

    public List<Position> findPositions(Position position) {
        String cacheKey = CacheConfig.POSITION_CACHE_START + position.toString();
        if (cacheMap.containsKey(cacheKey)) {
            logger.info(() -> String.format("Cache %s value:%n%s", cacheKey, cacheMap.get(cacheKey).toString()));
            return cacheMap.get(cacheKey);
        }

        List<Position> result = positionRepository.findPosition(position);
        cacheMap.put(cacheKey, result);
        return result;
    }

    public void deletePosition(@NonNull Long positionId) {
        boolean hasReferences = positionRepository.hasReferences(positionId);

        if (hasReferences) {
            throw new IllegalStateException("Position with id " + positionId + " does not exists or has references");
        }

        cacheMap.clear();
        positionRepository.deleteById(positionId);
    }
}
