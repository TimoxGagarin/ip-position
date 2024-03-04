package com.ip_position.ipposition.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ip_position.ipposition.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("SELECT p FROM Position p WHERE p.latitude = ?1 AND p.longitude = ?2")
    Optional<Position> findPositionByLatLng(Double lat, Double lon);

    @Query("SELECT COUNT(r) > 0 FROM IpInfo r WHERE r.position.id = ?1")
    boolean hasReferences(Long positionId);
}
