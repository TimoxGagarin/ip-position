package com.ip_position.ipposition.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.services.ProviderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("providers")
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @Operation(summary = "Получить список провайдеров по параметрам", responses = {
            @ApiResponse(description = "Провайдер", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Provider.class)))
    })
    @GetMapping("find")
    public List<Provider> getIpInfoDB(
            @Parameter(description = "ID") @RequestParam(required = false) Long id,
            @Parameter(description = "Поставщик интернет-услуг") @RequestParam(required = false) String internetServiceProvider,
            @Parameter(description = "Организация") @RequestParam(required = false) String organisation,
            @Parameter(description = "Наименование автономной системы") @RequestParam(required = false) String autonomousSystemName) {
        return providerService.findProviders(new Provider(
                id,
                internetServiceProvider,
                organisation,
                autonomousSystemName));
    }

    @Operation(summary = "Получить города провайдера", responses = {
            @ApiResponse(description = "Список городов провайдера", content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class)))
    })
    @GetMapping("providers-by-city")
    public Set<City> getCitiesOfProvider(
            @Parameter(description = "Название провайдера", required = true) @RequestParam(required = true) String isp) {
        Provider provider = providerService.findProviders(new Provider(
                null,
                isp,
                null,
                null)).get(0);
        return (provider != null) ? provider.getCities() : new HashSet<>();
    }
}