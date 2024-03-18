package com.ip_position.ipposition.dto;

import com.ip_position.ipposition.entity.Position;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Schema(description = "DTO representing position information")
public class PositionDTO {
    @Schema(description = "Position ID", example = "1")
    private Long id;

    @DecimalMin("-90.0")
    @DecimalMax("90.0")
    @Schema(description = "Latitude", example = "-27.4766", required = true)
    private Double latitude;

    @DecimalMin("-180.0")
    @DecimalMax("180.0")
    @Schema(description = "Longitude", example = "153.0166", required = true)
    private Double longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Schema(hidden = true)
    public Position getPosition() {
        return new Position(
                this.latitude,
                this.longitude);
    }
}
