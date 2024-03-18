package com.ip_position.ipposition.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ip_position.ipposition.dto.IpInfoDTO;
import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Position;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.services.IpInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("ips-info")
public class IpInfoController {

    private final IpInfoService ipInfoService;

    public IpInfoController(IpInfoService ipInfoService) {
        this.ipInfoService = ipInfoService;
    }

    @Operation(summary = "Получить информацию об IP-адресе из внешнего API", responses = {
            @ApiResponse(description = "Информация об IP-адресе", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IpInfo.class)))
    })
    @GetMapping("external_api")
    public IpInfo getIpInfoAPI(
            @RequestParam(required = true) String ip) {
        return ipInfoService.getIpInfoFromAPI(ip);
    }

    @Operation(summary = "Получить информацию об IP-адресе из базы данных по параметрам", responses = {
            @ApiResponse(description = "Информация об IP-адресе", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IpInfo.class)))
    })
    @GetMapping("find")
    public ResponseEntity<List<IpInfo>> getIpInfoDB(
            @Parameter(description = "IP-адрес") @RequestParam(required = false) String ip,
            @Parameter(description = "Широта") @RequestParam(required = false) Double latitude,
            @Parameter(description = "Долгота") @RequestParam(required = false) Double longitude,
            @Parameter(description = "ID") @RequestParam(required = false) Long id,
            @Parameter(description = "Часовой пояс") @RequestParam(required = false) String timeZone,
            @Parameter(description = "Страна") @RequestParam(required = false) String country,
            @Parameter(description = "Код страны") @RequestParam(required = false) String countryCode,
            @Parameter(description = "Регион") @RequestParam(required = false) String region,
            @Parameter(description = "Название региона") @RequestParam(required = false) String regionName,
            @Parameter(description = "Название города") @RequestParam(required = false) String cityName,
            @Parameter(description = "Почтовый индекс") @RequestParam(required = false) String zip,
            @Parameter(description = "Поставщик интернет-услуг") @RequestParam(required = false) String internetServiceProvider,
            @Parameter(description = "Организация") @RequestParam(required = false) String organisation,
            @Parameter(description = "Наименование автономной системы") @RequestParam(required = false) String autonomousSystemName) {

        return ResponseEntity.ok(ipInfoService.findIpInfoFromDB(new IpInfo(
                id,
                new City(country,
                        countryCode,
                        region,
                        regionName,
                        cityName,
                        zip),
                new Position(
                        latitude,
                        longitude),
                timeZone,
                new Provider(internetServiceProvider,
                        organisation,
                        autonomousSystemName),
                ip)));
    }

    @Operation(summary = "Добавить новую информацию об IP-адресе")
    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewIpInfo(
            @Parameter(description = "Информация об IP-адресе для добавления", required = true) @Valid @RequestBody IpInfoDTO ipInfo) {
        ipInfoService.addNewIpInfo(ipInfo.getIpInfo());
    }

    @Operation(summary = "Удалить информацию об IP-адресе по ID")
    @DeleteMapping("delete")
    public void deleteIpInfo(
            @Parameter(description = "ID информации об IP-адресе для удаления", required = true) @RequestParam(required = true) @NonNull Long id) {
        ipInfoService.deleteIpInfo(id);
    }

    @Operation(summary = "Обновить информацию об IP-адресе по ID")
    @PutMapping("update")
    public void updateIpInfo(
            @Parameter(description = "ID информации об IP-адресе для обновления", required = true) @RequestParam(required = true) @NonNull Long id) {
        ipInfoService.updateIpInfo(id);
    }
}
