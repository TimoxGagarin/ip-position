package com.ip_position.ipposition.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class City {
    // Primary key for the City entity
    @Id
    // Define a sequence generator for the id field
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_sequence", allocationSize = 1)
    // Use the generated sequence for id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sequence")
    private Long id;

    // Country of the city
    private String country;

    // Country code of the city
    private String countryCode;

    // Region of the city
    private String region;

    // Region name of the city
    private String regionName;

    // Name of the city
    private String cityName;

    // ZIP code of the city
    private String zip;

    // Default constructor for JPA entity compliance
    public City() {
    }

    // Constructor with all parameters including the id
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

    // Constructor without id for creating new instances
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

    // Getter for the id field
    public Long getId() {
        return this.id;
    }

    // Setter for the id field
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for the country field
    public String getCountry() {
        return this.country;
    }

    // Setter for the country field
    public void setCountry(String country) {
        this.country = country;
    }

    // Getter for the countryCode field
    public String getCountryCode() {
        return this.countryCode;
    }

    // Setter for the countryCode field
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    // Getter for the region field
    public String getRegion() {
        return this.region;
    }

    // Setter for the region field
    public void setRegion(String region) {
        this.region = region;
    }

    // Getter for the regionName field
    public String getRegionName() {
        return this.regionName;
    }

    // Setter for the regionName field
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    // Getter for the cityName field
    public String getCityName() {
        return this.cityName;
    }

    // Setter for the cityName field
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // Getter for the zip field
    public String getZip() {
        return this.zip;
    }

    // Setter for the zip field
    public void setZip(String zip) {
        this.zip = zip;
    }

    // Override toString method for better readability
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