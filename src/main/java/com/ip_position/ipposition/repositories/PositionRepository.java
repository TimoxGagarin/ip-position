package com.ip_position.ipposition.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ip_position.ipposition.entity.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("SELECT p FROM Position p WHERE " +
            "(:#{#position.id} IS NULL OR p.id = :#{#position.id}) AND " +
            "(:#{#position.latitude} IS NULL OR p.latitude = :#{#position.latitude}) AND " +
            "(:#{#position.longitude} IS NULL OR p.longitude = :#{#position.longitude})")
    List<Position> findPosition(@Param("position") Position position);

    @Query("SELECT COUNT(r) > 0 FROM IpInfo r WHERE r.position.id = ?1")
    boolean hasReferences(Long positionId);
}
