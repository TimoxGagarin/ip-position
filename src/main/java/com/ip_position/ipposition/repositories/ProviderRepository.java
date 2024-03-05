package com.ip_position.ipposition.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ip_position.ipposition.entity.Provider;
import org.springframework.data.repository.query.Param;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("SELECT p FROM Provider p WHERE p.internetServiceProvider = ?1")
    Optional<Provider> findProviderByInternetServiceProvider(String internetServiceProvider);

    @Query("SELECT p FROM Provider p WHERE p.internetServiceProvider = :#{#provider.internetServiceProvider} " +
            "AND p.organisation = :#{#provider.organisation} " +
            "AND p.autonomusSystemName = :#{#provider.autonomusSystemName}")
    Optional<Provider> findProviderByAll(@Param("provider") Provider provider);

    @Query("SELECT COUNT(r) > 0 FROM IpInfo r WHERE r.provider.id = ?1")
    boolean hasReferences(Long providerId);

    @Modifying
    @Query(value = "DELETE FROM city_provider_relation WHERE provider_id = ?1", nativeQuery = true)
    void clearRelations(Long providerId);
}