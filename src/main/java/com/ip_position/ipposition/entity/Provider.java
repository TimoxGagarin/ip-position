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

    private String internetServiceProvider;
    private String organisation;
    private String autonomusSystemName;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "city_provider_relation", joinColumns = @JoinColumn(name = "provider_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
    private Set<City> cities = new HashSet<>();

    public Provider() {
    }

    public Provider(
            Long id,
            String internetServiceProvider,
            String organisation,
            String autonomusSystemName) {
        this.id = id;
        this.internetServiceProvider = internetServiceProvider;
        this.organisation = organisation;
        this.autonomusSystemName = autonomusSystemName;
    }

    public Provider(
            String internetServiceProvider,
            String organisation,
            String autonomusSystemName) {
        this.internetServiceProvider = internetServiceProvider;
        this.organisation = organisation;
        this.autonomusSystemName = autonomusSystemName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternetServiceProvider() {
        return this.internetServiceProvider;
    }

    public void setInternetServiceProvider(String internetServiceProvider) {
        this.internetServiceProvider = internetServiceProvider;
    }

    public String getOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getAutonomusSystemName() {
        return this.autonomusSystemName;
    }

    public void setAutonomusSystemName(String autonomusSystemName) {
        this.autonomusSystemName = autonomusSystemName;
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
                ", isp=" + this.internetServiceProvider +
                ", org=" + this.organisation +
                ", asName=" + this.autonomusSystemName + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Provider other) {
            return Objects.equals(this.internetServiceProvider, other.internetServiceProvider) &&
                    Objects.equals(this.organisation, other.organisation) &&
                    Objects.equals(this.autonomusSystemName, other.autonomusSystemName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internetServiceProvider, organisation, autonomusSystemName);
    }
}
