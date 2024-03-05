package com.ip_position.ipposition.repositories;

import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.entity.IpInfo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface IpInfoRepository extends JpaRepository<IpInfo, Long> {
    @Query("SELECT r FROM IpInfo r WHERE r.ip = ?1")
    Optional<IpInfo> findResponseByIP(String ip);

    @Query("SELECT r FROM IpInfo r WHERE r.city.id = ?1")
    List<IpInfo> findAllByCityId(Long cityId);

    @Query("SELECT r FROM IpInfo r WHERE r.provider.id = ?1")
    List<IpInfo> findAllByProviderId(Long providerId);

    @Query("SELECT r FROM IpInfo r WHERE r.position.id = ?1")
    List<IpInfo> findAllByPositionId(Long positionId);
}