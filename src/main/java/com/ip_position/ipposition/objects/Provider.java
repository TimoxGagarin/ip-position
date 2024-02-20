package com.ip_position.ipposition.objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Provider {
    // Primary key for the Provider entity
    @Id
    // Define a sequence generator for the id field
    @SequenceGenerator(name = "provider_sequence", sequenceName = "provider_sequence", allocationSize = 1)
    // Use the generated sequence for id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_sequence")
    private Long id;

    // Internet Service Provider name
    private String isp;

    // Organization name
    private String org;

    // AS (Autonomous System) name
    private String asName;

    // Default constructor for JPA entity compliance
    public Provider() {
    }

    // Constructor with all parameters including the id
    public Provider(
            Long id,
            String isp,
            String org,
            String asName) {
        this.id = id;
        this.isp = isp;
        this.org = org;
        this.asName = asName;
    }

    // Constructor without id for creating new instances
    public Provider(
            String isp,
            String org,
            String asName) {
        this.isp = isp;
        this.org = org;
        this.asName = asName;
    }

    // Getter for the id field
    public Long getId() {
        return this.id;
    }

    // Setter for the id field
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for the isp field
    public String getIsp() {
        return this.isp;
    }

    // Setter for the isp field
    public void setIsp(String isp) {
        this.isp = isp;
    }

    // Getter for the org field
    public String getOrg() {
        return this.org;
    }

    // Setter for the org field
    public void setOrg(String org) {
        this.org = org;
    }

    // Getter for the asName field
    public String getAsName() {
        return this.asName;
    }

    // Setter for the asName field
    public void setAsName(String asName) {
        this.asName = asName;
    }

    // Override toString method for better readability
    @Override
    public String toString() {
        return "Provider(id=" + this.id +
                ", isp=" + this.isp +
                ", org=" + this.org +
                ", asName=" + this.asName + ")";
    }
}
