package com.ip_position.ipposition.latlng;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class LatLng {

    // Primary key for the LatLng entity
    @Id
    // Define a sequence generator for the id field
    @SequenceGenerator(name = "latlng_sequence", sequenceName = "latlng_sequence", allocationSize = 1)
    // Use the generated sequence for id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "latlng_sequence")
    private Long id;

    // Latitude value
    private Double latitude;

    // Longitude value
    private Double longitude;

    // Default constructor for JPA entity compliance
    public LatLng() {
    }

    // Constructor with all parameters including the id
    public LatLng(Long id, Double latitude, Double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Constructor without id for creating new instances
    public LatLng(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getter for the id field
    public Long getId() {
        return this.id;
    }

    // Setter for the id field
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for the latitude field
    public Double getLatitude() {
        return this.latitude;
    }

    // Setter for the latitude field
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    // Getter for the longitude field
    public Double getLongitude() {
        return this.longitude;
    }

    // Setter for the longitude field
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    // Override toString method for better readability
    @Override
    public String toString() {
        return "LatLng(id=" + this.id +
                ", lat=" + this.latitude +
                ", lon=" + this.longitude + ")";
    }
}