package com.ip_position.ipposition.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class City {
    @Id
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_sequence")
    @Schema(example = "1")
    private Long id;

    @Schema(example = "Australia", required = true)
    private String country;

    @Schema(example = "AU", required = true)
    private String countryCode;

    @Schema(example = "QLD", required = true)
    private String region;

    @Schema(example = "Queensland", required = true)
    private String regionName;

    @Schema(example = "South Brisbane", required = true)
    private String name;

    @Schema(example = "4101", required = true)
    private String zip;

    @JsonIgnore
    @ManyToMany(mappedBy = "cities")
    private Set<Provider> providers = new HashSet<>();

    public City() {

    }

    public City(
            Long id,
            String country,
            String countryCode,
            String region,
            String regionName,
            String name,
            String zip) {
        this.id = id;
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.name = name;
        this.zip = zip;
    }

    public City(
            String country,
            String countryCode,
            String region,
            String regionName,
            String name,
            String zip) {
        this.country = country;
        this.countryCode = countryCode;
        this.region = region;
        this.regionName = regionName;
        this.name = name;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Set<Provider> getProviders() {
        return providers;
    }

    public void addProvider(Provider provider) {
        providers.add(provider);
        provider.getCities().add(this);
    }

    public void removeProvider(Provider provider) {
        providers.remove(provider);
        provider.getCities().remove(this);
    }

    @Override
    public String toString() {
        return "City(id=" + this.id +
                ", country=" + this.country +
                ", countryCode=" + this.countryCode +
                ", region=" + this.region +
                ", regionName=" + this.regionName +
                ", name=" + this.name +
                ", zip=" + this.zip + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof City))
            return false;

        City other = (City) obj;
        return Objects.equals(this.country, other.country) &&
                Objects.equals(this.countryCode, other.countryCode) &&
                Objects.equals(this.region, other.region) &&
                Objects.equals(this.regionName, other.regionName) &&
                Objects.equals(this.name, other.name) &&
                Objects.equals(this.zip, other.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, countryCode, region, regionName, name, zip);
    }
}