package com.ip_position.ipposition.objects;

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
    // Primary key for the IpInfo entity
    @Id
    // Define a sequence generator for the id field
    @SequenceGenerator(name = "ipinfo_sequence", sequenceName = "ipinfo_sequence", allocationSize = 1)
    // Use the generated sequence for id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ipinfo_sequence")
    private Long id;

    // Many-to-One relationship with City entity
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    // Many-to-One relationship with LatLng entity
    @ManyToOne
    @JoinColumn(name = "position_id")
    private LatLng position;

    // Time zone information
    private String timeZone;

    // Many-to-One relationship with Provider entity
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;

    // IP address query
    private String query;

    // Default constructor for JPA entity compliance
    public IpInfo() {
    }

    // Constructor with all parameters including the id
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

    // Constructor without id for creating new instances
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

    // Getter for the id field
    public Long getId() {
        return this.id;
    }

    // Setter for the id field
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for the city field
    public City getCity() {
        return this.city;
    }

    // Setter for the city field
    public void setCity(City city) {
        this.city = city;
    }

    // Getter for the position field
    public LatLng getPosition() {
        return this.position;
    }

    // Setter for the position field
    public void setPosition(LatLng position) {
        this.position = position;
    }

    // Getter for the timeZone field
    public String getTimeZone() {
        return this.timeZone;
    }

    // Setter for the timeZone field
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    // Getter for the provider field
    public Provider getProvider() {
        return this.provider;
    }

    // Setter for the provider field
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    // Getter for the query field
    public String getQuery() {
        return this.query;
    }

    // Setter for the query field
    public void setQuery(String query) {
        this.query = query;
    }

    // Override toString method for better readability
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