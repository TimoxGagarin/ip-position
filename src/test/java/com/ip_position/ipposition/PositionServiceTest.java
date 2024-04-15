package com.ip_position.ipposition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.repositories.PositionRepository;
import com.ip_position.ipposition.services.PositionService;

class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    private Map<String, List<Position>> cacheMap;
    private Logger logger;

    private PositionService positionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cacheMap = new HashMap<>();
        logger = Logger.getLogger(PositionService.class.getName());
        positionService = new PositionService(positionRepository, cacheMap, logger);
    }

    @Test
    void addNewPosition_Success() {
        Position positionToAdd = new Position(1.28009, 103.851);

        when(positionRepository.findPosition(positionToAdd)).thenReturn(new ArrayList<>());
        when(positionRepository.save(positionToAdd)).thenReturn(positionToAdd);

        Position addedPosition = positionService.addNewPosition(positionToAdd);

        assertEquals(positionToAdd, addedPosition);
        verify(positionRepository, times(1)).findPosition(positionToAdd);
        verify(positionRepository, times(1)).save(positionToAdd);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void addNewPosition_hasEqualPosition_Success() {
        Position positionToAdd = new Position(1.28009, 103.851);

        when(positionRepository.findPosition(positionToAdd)).thenReturn(List.of(positionToAdd));
        Position addedPosition = positionService.addNewPosition(positionToAdd);

        assertEquals(positionToAdd, addedPosition);
        verify(positionRepository, times(1)).findPosition(positionToAdd);
        verify(positionRepository, never()).save(positionToAdd);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void findPositions_CacheHit() {
        Position positionToFind = new Position(1.28009, 103.851);
        List<Position> cachedPositions = new ArrayList<>();
        cachedPositions.add(new Position(1.28009, 103.851));

        String cacheKey = CacheConfig.POSITION_CACHE_START + positionToFind.toString();
        cacheMap.put(cacheKey, cachedPositions);

        List<Position> foundPositions = positionService.findPositions(positionToFind);

        assertEquals(cachedPositions, foundPositions);
        assertEquals(cachedPositions, cacheMap.get(cacheKey));
        verify(positionRepository, never()).findPosition(positionToFind);
    }

    @Test
    void findPositions_noCacheHit() {
        Position positionToFind = new Position(1.28009, 103.851);
        List<Position> cachedPositions = new ArrayList<>();
        cachedPositions.add(new Position(1.28009, 103.851));

        String cacheKey = CacheConfig.POSITION_CACHE_START + positionToFind.toString();

        List<Position> foundPositions = positionService.findPositions(positionToFind);

        assertNotEquals(cachedPositions, foundPositions);
        assertNotEquals(cachedPositions, cacheMap.get(cacheKey));
        verify(positionRepository, times(1)).findPosition(positionToFind);
    }

    @Test
    void deletePosition_Success() {
        Long positionId = 1L;
        when(positionRepository.hasReferences(positionId)).thenReturn(false);
        positionService.deletePosition(positionId);
        verify(positionRepository, times(1)).deleteById(positionId);
        assertTrue(cacheMap.isEmpty());
    }

    @Test
    void deletePosition_WithReferences_ExceptionThrown() {
        Long positionId = 1L;
        when(positionRepository.hasReferences(positionId)).thenReturn(true);
        verify(positionRepository, never()).deleteById(positionId);
        assertTrue(cacheMap.isEmpty());
    }
}