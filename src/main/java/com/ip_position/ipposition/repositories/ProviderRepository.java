package com.ip_position.ipposition.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ip_position.ipposition.entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
    @Query("SELECT p FROM Provider p WHERE " +
            "(:#{#provider.id} IS NULL OR p.id = :#{#provider.id}) AND " +
            "(:#{#provider.internetServiceProvider} IS NULL OR p.internetServiceProvider = :#{#provider.internetServiceProvider}) AND "
            +
            "(:#{#provider.organisation} IS NULL OR p.organisation = :#{#provider.organisation}) AND " +
            "(:#{#provider.autonomusSystemName} IS NULL OR p.autonomusSystemName = :#{#provider.autonomusSystemName})")
    List<Provider> findProvider(@Param("provider") Provider provider);

    @Query("SELECT COUNT(r) > 0 FROM IpInfo r WHERE r.provider.id = ?1")
    boolean hasReferences(Long providerId);

    @Modifying
    @Query(value = "DELETE FROM city_provider_relation WHERE provider_id = ?1", nativeQuery = true)
    void clearRelations(Long providerId);
}