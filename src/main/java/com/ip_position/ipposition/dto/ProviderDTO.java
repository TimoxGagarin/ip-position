package com.ip_position.ipposition.dto;

import com.ip_position.ipposition.entity.Provider;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO representing a provider")
public class ProviderDTO {
    @Schema(example = "1")
    private Long id;

    @NotBlank
    @Schema(example = "Cloudflare, Inc", required = true)
    private String internetServiceProvider;

    @Schema(example = "APNIC and Cloudflare DNS Resolver project", required = true)
    private String organisation;

    @NotBlank
    @Schema(example = "AS13335 Cloudflare, Inc.", required = true)
    private String autonomousSystemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInternetServiceProvider() {
        return internetServiceProvider;
    }

    public void setInternetServiceProvider(String internetServiceProvider) {
        this.internetServiceProvider = internetServiceProvider;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getAutonomousSystemName() {
        return autonomousSystemName;
    }

    public void setAutonomousSystemName(String autonomousSystemName) {
        this.autonomousSystemName = autonomousSystemName;
    }

    @Schema(hidden = true)
    public Provider getProvider() {
        return new Provider(
                this.internetServiceProvider,
                this.organisation,
                this.autonomousSystemName);
    }
}