package com.ip_position.ipposition.entity;

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
    private LatLng position;

    private String timeZone;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    private String query;

    public IpInfo() {
    }

    public IpInfo(
            Long id,
            City city,
            LatLng position,
            String timeZone,
            Provider provider,
            String query) {
        this.id = id;
        this.city = city;
        this.position = position;
        this.timeZone = timeZone;
        this.provider = provider;
        this.query = query;
    }

    public IpInfo(
            City city,
            LatLng position,
            String timeZone,
            Provider provider,
            String query) {
        this.city = city;
        this.position = position;
        this.timeZone = timeZone;
        this.provider = provider;
        this.query = query;
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

    public LatLng getPosition() {
        return this.position;
    }

    public void setPosition(LatLng position) {
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

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "IpInfo(id=" + this.id +
                ", city=" + this.city +
                ", position=" + this.position +
                ", timezone=" + this.timeZone +
                ", provider=" + this.provider +
                ", query=" + this.query + ")";
    }
}