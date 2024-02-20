package com.ip_position.ipposition.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Provider {
    @Id
    @SequenceGenerator(name = "provider_sequence", sequenceName = "provider_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "provider_sequence")
    private Long id;

    private String isp;
    private String org;
    private String asName;

    public Provider() {
    }

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

    public Provider(
            String isp,
            String org,
            String asName) {
        this.isp = isp;
        this.org = org;
        this.asName = asName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsp() {
        return this.isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getAsName() {
        return this.asName;
    }

    public void setAsName(String asName) {
        this.asName = asName;
    }

    @Override
    public String toString() {
        return "Provider(id=" + this.id +
                ", isp=" + this.isp +
                ", org=" + this.org +
                ", asName=" + this.asName + ")";
    }
}
