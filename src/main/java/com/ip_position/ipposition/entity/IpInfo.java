package com.ip_position.ipposition.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class IpInfo {
    @Id
    @SequenceGenerator(name = "ipinfo_sequence", sequenceName = "ipinfo_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipinfo_sequence")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    private String timeZone;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private String ip;

    public IpInfo() {
    }

    public IpInfo(
            Long id,
            City city,
            Position position,
            String timeZone,
            Provider provider,
            String ip) {
        this.id = id;
        this.city = city;
        this.position = position;
        this.timeZone = timeZone;
        this.provider = provider;
        this.ip = ip;
    }

    public IpInfo(
            City city,
            Position position,
            String timeZone,
            Provider provider,
            String ip) {
        this.city = city;
        this.position = position;
        this.timeZone = timeZone;
        this.provider = provider;
        this.ip = ip;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTimeZone() {
        return this.timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Provider getProvider() {
        return this.provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "IpInfo(id=" + this.id +
                ", city=" + this.city +
                ", position=" + this.position +
                ", timezone=" + this.timeZone +
                ", provider=" + this.provider +
                ", ip=" + this.ip + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof IpInfo other) {
            return Objects.equals(this.city, other.city) &&
                    Objects.equals(this.provider, other.provider) &&
                    Objects.equals(this.position, other.position) &&
                    Objects.equals(this.timeZone, other.timeZone) &&
                    Objects.equals(this.ip, other.ip);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, provider, position, timeZone, ip);
    }
}