package com.ip_position.ipposition.dto;

import com.ip_position.ipposition.entity.City;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO representing city information")
public class CityDTO {
    @Schema(description = "City ID", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Country", example = "Australia", required = true)
    private String country;

    @NotBlank
    @Schema(description = "Country Code", example = "AU", required = true)
    private String countryCode;

    @NotBlank
    @Schema(description = "Region", example = "QLD", required = true)
    private String region;

    @NotBlank
    @Schema(description = "Region Name", example = "Queensland", required = true)
    private String regionName;

    @NotBlank
    @Schema(description = "City Name", example = "South Brisbane", required = true)
    private String name;

    @NotBlank
    @Schema(description = "ZIP Code", example = "4101", required = true)
    private String zip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Schema(hidden = true)
    public City getCity() {
        return new City(
                this.country,
                this.countryCode,
                this.region,
                this.regionName,
                this.name,
                this.zip);
    }
}