package com.ip_position.ipposition.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class City {
    @Id
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sequence")
    private Long id;

    private String country;
    private String countryCode;
    private String region;
    private String regionName;
    private String cityName;
    private String zip;

    public City() {

    }

    public City(
            Long id,
            String country,
            String countryCode,
            String region,
            String regionName,
            String cityName,
            String zip) {
        this.id = id;
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.cityName = cityName;
        this.zip = zip;
    }

    public City(
            String country,
            String countryCode,
            String region,
            String regionName,
            String cityName,
            String zip) {
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.cityName = cityName;
        this.zip = zip;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "City(id=" + this.id +
                ", country=" + this.country +
                ", countryCode=" + this.countryCode +
                ", region=" + this.region +
                ", regionName=" + this.regionName +
                ", cityName=" + this.cityName +
                ", zip=" + this.zip + ")";
    }
}