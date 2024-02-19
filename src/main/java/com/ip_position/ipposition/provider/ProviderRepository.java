package com.ip_position.ipposition.provider;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    // Custom query method using JPQL to find a Provider by its isp (Internet
    // Service Provider) name
    @Query("SELECT p FROM Provider p WHERE p.isp = ?1")
    Optional<Provider> findProviderByIsp(String isp);
}