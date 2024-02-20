package com.ip_position.ipposition.repositories;

import org.springframework.stereotype.Repository;

import com.ip_position.ipposition.entity.IpInfo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface IpInfoRepository extends JpaRepository<IpInfo, Long> {
    @Query("SELECT r FROM IpInfo r WHERE r.query = ?1")
    Optional<IpInfo> findResponseByIP(String query);
}