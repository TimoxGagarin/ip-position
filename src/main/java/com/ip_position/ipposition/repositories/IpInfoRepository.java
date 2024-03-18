package com.ip_position.ipposition.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.entity.IpInfo;

@Repository
public interface IpInfoRepository extends JpaRepository<IpInfo, Long> {
    @Query("SELECT i FROM IpInfo i " +
            "JOIN i.city c " +
            "JOIN i.position p " +
            "JOIN i.provider pr " +
            "WHERE " +
            "(:#{#ipInfo.id} IS NULL OR i.id = :#{#ipInfo.id}) AND " +
            "(:#{#ipInfo.city != null ? #ipInfo.city.id : null} IS NULL OR c.id = :#{#ipInfo.city != null ? #ipInfo.city.id : null}) AND "
            +
            "(:#{#ipInfo.position != null ? #ipInfo.position.id : null} IS NULL OR p.id = :#{#ipInfo.position != null ? #ipInfo.position.id : null}) AND "
            +
            "(:#{#ipInfo.timeZone} IS NULL OR i.timeZone = :#{#ipInfo.timeZone}) AND " +
            "(:#{#ipInfo.provider != null ? #ipInfo.provider.id : null} IS NULL OR pr.id = :#{#ipInfo.provider != null ? #ipInfo.provider.id : null}) AND "
            +
            "(:#{#ipInfo.ip} IS NULL OR i.ip = :#{#ipInfo.ip}) AND " +
            "(:#{#ipInfo.city != null ? #ipInfo.city.country : null} IS NULL OR c.country = :#{#ipInfo.city != null ? #ipInfo.city.country : null}) AND "
            +
            "(:#{#ipInfo.city != null ? #ipInfo.city.countryCode : null} IS NULL OR c.countryCode = :#{#ipInfo.city != null ? #ipInfo.city.countryCode : null}) AND "
            +
            "(:#{#ipInfo.city != null ? #ipInfo.city.region : null} IS NULL OR c.region = :#{#ipInfo.city != null ? #ipInfo.city.region : null}) AND "
            +
            "(:#{#ipInfo.city != null ? #ipInfo.city.regionName : null} IS NULL OR c.regionName = :#{#ipInfo.city != null ? #ipInfo.city.regionName : null}) AND "
            +
            "(:#{#ipInfo.city != null ? #ipInfo.city.name : null} IS NULL OR c.name = :#{#ipInfo.city != null ? #ipInfo.city.name : null}) AND "
            +
            "(:#{#ipInfo.city != null ? #ipInfo.city.zip : null} IS NULL OR c.zip = :#{#ipInfo.city != null ? #ipInfo.city.zip : null}) AND "
            +
            "(:#{#ipInfo.provider != null ? #ipInfo.provider.internetServiceProvider : null} IS NULL OR pr.internetServiceProvider = :#{#ipInfo.provider != null ? #ipInfo.provider.internetServiceProvider : null}) AND "
            +
            "(:#{#ipInfo.provider != null ? #ipInfo.provider.organisation : null} IS NULL OR pr.organisation = :#{#ipInfo.provider != null ? #ipInfo.provider.organisation : null}) AND "
            +
            "(:#{#ipInfo.provider != null ? #ipInfo.provider.autonomusSystemName : null} IS NULL OR pr.autonomusSystemName = :#{#ipInfo.provider != null ? #ipInfo.provider.autonomusSystemName : null}) AND "
            +
            "(:#{#ipInfo.position != null ? #ipInfo.position.latitude : null} IS NULL OR p.latitude = :#{#ipInfo.position != null ? #ipInfo.position.latitude : null}) AND "
            +
            "(:#{#ipInfo.position != null ? #ipInfo.position.longitude : null} IS NULL OR p.longitude = :#{#ipInfo.position != null ? #ipInfo.position.longitude : null})")
    List<IpInfo> findIpInfo(@Param("ipInfo") IpInfo ipInfo);

}