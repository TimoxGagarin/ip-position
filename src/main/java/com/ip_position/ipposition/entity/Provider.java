package com.ip_position.ipposition.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "city_provider_relation", joinColumns = @JoinColumn(name = "provider_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
    private Set<City> cities = new HashSet<>();

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

    public Set<City> getCities() {
        return cities;
    }

    public void addCity(City city) {
        cities.add(city);
        city.getProviders().add(this);
    }

    public void removeCity(City city) {
        cities.remove(city);
        city.getProviders().remove(this);
    }

    @Override
    public String toString() {
        return "Provider(id=" + this.id +
                ", isp=" + this.isp +
                ", org=" + this.org +
                ", asName=" + this.asName + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Provider other) {
            return Objects.equals(this.isp, other.isp) &&
                    Objects.equals(this.org, other.org) &&
                    Objects.equals(this.asName, other.asName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isp, org, asName);
    }
}
