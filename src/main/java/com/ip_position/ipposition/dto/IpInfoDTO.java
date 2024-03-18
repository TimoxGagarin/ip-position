package com.ip_position.ipposition.dto;

import com.ip_position.ipposition.entity.IpInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO representing IP information")
public class IpInfoDTO {

    private static final String OCTET_REGEX = "(25[0-5]|2[0-4]\\d|[0-1]?\\d\\d?)";
    private static final String IPV4_REGEX = "^" + OCTET_REGEX + "\\." + OCTET_REGEX + "\\." + OCTET_REGEX + "\\."
            + OCTET_REGEX + "$";

    private static final String GROUP_REGEX = "[0-9a-fA-F]{1,4}";
    private static final String IPV6_REGEX = "^" + GROUP_REGEX + ":(?:" + GROUP_REGEX + ":){6}" + GROUP_REGEX + "$";

    public static final String IP_REGEX = "(" + IPV4_REGEX + ")|(" + IPV6_REGEX + ")";

    @Pattern(regexp = IP_REGEX, message = "Invalid IP address format")
    @Schema(description = "DTO representing IP details")
    private String ip;

    @Valid
    @Schema(description = "DTO representing city details")
    private CityDTO city;

    @Valid
    @Schema(description = "DTO representing position details")
    private PositionDTO position;

    @NotBlank(message = "Time zone cannot be blank")
    @Schema(description = "Time zone information", example = "UTC")
    private String timeZone;

    @Valid
    @Schema(description = "DTO representing provider details")
    private ProviderDTO provider;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public PositionDTO getPosition() {
        return position;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public ProviderDTO getProvider() {
        return provider;
    }

    public void setProvider(ProviderDTO provider) {
        this.provider = provider;
    }

    @Schema(hidden = true)
    public IpInfo getIpInfo() {
        return new IpInfo(
                this.city.getCity(),
                this.position.getPosition(),
                this.timeZone,
                this.provider.getProvider(),
                this.ip);
    }
}