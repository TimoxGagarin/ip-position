package com.ip_position.ipposition.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.repositories.PositionRepository;

@Service
public class PositionService {

    private final PositionRepository positionRepository;
    private final Map<String, List<Position>> cacheMap;

    @Autowired
    private Logger logger;

    public PositionService(PositionRepository positionRepository, HashMap<String, List<Position>> cacheMap) {
        this.positionRepository = positionRepository;
        this.cacheMap = cacheMap;
    }

    public Position addNewPosition(Position position) {
        List<Position> positionList = positionRepository.findPosition(position);
        if (positionList.isEmpty()) {
            cacheMap.clear();
            logger.info(
                    String.format("Entity %s was added into table Position",
                            position.toString().replaceAll("[\n\r]", "_")));
            positionRepository.save(position);
            return position;
        }
        return positionList.get(0);
    }

    public List<Position> findPositions(Position position) {
        if (cacheMap.containsKey("findPositions_" + position.toString())) {
            logger.info(String.format("Cache findPositions_%s value:\n%s", "findPositions_" + position.toString(),
                    cacheMap.get("findPositions_" + position.toString()).toString()));
            return cacheMap.get("findPositions_" + position.toString());
        }

        List<Position> result = positionRepository.findPosition(position);
        cacheMap.put("findPositions_" + position.toString(), result);
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
