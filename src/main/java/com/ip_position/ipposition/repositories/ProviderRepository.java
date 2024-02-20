package com.ip_position.ipposition.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ip_position.ipposition.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("SELECT p FROM Provider p WHERE p.isp = ?1")
    Optional<Provider> findProviderByIsp(String isp);
}