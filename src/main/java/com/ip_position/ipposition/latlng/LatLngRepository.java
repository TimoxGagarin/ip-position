package com.ip_position.ipposition.latlng;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LatLngRepository extends JpaRepository<LatLng, Long> {
    // Custom query method using JPQL to find a LatLng position by its latitude and
    // longitude
    @Query("SELECT p FROM LatLng p WHERE p.latitude = ?1 AND p.longitude = ?2")
    Optional<LatLng> findPositionByLatLng(Double lat, Double lon);
}
