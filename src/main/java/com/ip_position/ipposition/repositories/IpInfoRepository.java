package com.ip_position.ipposition.repositories;

import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.objects.IpInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// Repository annotation indicating that this interface is a Spring Data repository
@Repository
public interface IpInfoRepository extends JpaRepository<IpInfo, Long> {
    // Custom query method using JPQL to find IpInfo by IP address
    @Query("SELECT r FROM IpInfo r WHERE r.query = ?1")
    Optional<IpInfo> findResponseByIP(String query);
}