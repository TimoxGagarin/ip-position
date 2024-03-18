package com.ip_position.ipposition.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT c FROM City c WHERE " +
            "(:#{#city.id} IS NULL OR c.id = :#{#city.id}) AND " +
            "(:#{#city.country} IS NULL OR c.country = :#{#city.country}) AND " +
            "(:#{#city.countryCode} IS NULL OR c.countryCode = :#{#city.countryCode}) AND " +
            "(:#{#city.region} IS NULL OR c.region = :#{#city.region}) AND " +
            "(:#{#city.regionName} IS NULL OR c.regionName = :#{#city.regionName}) AND " +
            "(:#{#city.name} IS NULL OR c.name = :#{#city.name}) AND " +
            "(:#{#city.zip} IS NULL OR c.zip = :#{#city.zip})")
    List<City> findCity(@Param("city") City city);

    @Query("SELECT COUNT(r) > 0 FROM IpInfo r WHERE r.city.id = ?1")
    boolean hasReferences(Long cityId);

    @Modifying
    @Query(value = "DELETE FROM city_provider_relation WHERE city_id = ?1", nativeQuery = true)
    void clearRelations(Long cityId);
}