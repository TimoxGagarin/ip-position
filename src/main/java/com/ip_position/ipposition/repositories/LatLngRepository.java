package com.ip_position.ipposition.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ip_position.ipposition.entity.LatLng;

public interface LatLngRepository extends JpaRepository<LatLng, Long> {
    @Query("SELECT p FROM LatLng p WHERE p.latitude = ?1 AND p.longitude = ?2")
    Optional<LatLng> findPositionByLatLng(Double lat, Double lon);
}
