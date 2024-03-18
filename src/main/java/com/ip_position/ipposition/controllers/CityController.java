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
import com.ip_position.ipposition.services.CityService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("cities")
public class CityController {
    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(summary = "Получить города по параметрам", responses = {
            @ApiResponse(description = "Город", content = @Content(mediaType = "application/json", schema = @Schema(implementation = City.class)))
    })
    @GetMapping("find")
    public List<City> findCity(
            @Parameter(description = "ID") @RequestParam(required = false) Long id,
            @Parameter(description = "Страна") @RequestParam(required = false) String country,
            @Parameter(description = "Код страны") @RequestParam(required = false) String countryCode,
            @Parameter(description = "Регион") @RequestParam(required = false) String region,
            @Parameter(description = "Название региона") @RequestParam(required = false) String regionName,
            @Parameter(description = "Название города") @RequestParam(required = false) String cityName,
            @Parameter(description = "Почтовый индекс") @RequestParam(required = false) String zip) {
        return cityService.findCities(new City(
                id,
                country,
                countryCode,
                region,
                regionName,
                cityName,
                zip));
    }

    @Operation(summary = "Получить провайдеров города", responses = {
            @ApiResponse(description = "Список провайдеров города", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Provider.class)))
    })
    @GetMapping("providers-by-city")
    public Set<Provider> getProvidersOfCity(
            @Parameter(description = "Название города", required = true) @RequestParam(required = true) String cityName) {
        City city = cityService.findCities(new City(
                null,
                null,
                null,
                null,
                cityName,
                null)).get(0);
        return (city != null) ? city.getProviders() : new HashSet<>();
    }
}
