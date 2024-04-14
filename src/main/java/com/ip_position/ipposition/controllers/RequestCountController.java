package com.ip_position.ipposition.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ip_position.ipposition.services.RequestCountService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("counter")
public class RequestCountController {
    RequestCountService requestCountService;

    public RequestCountController(RequestCountService requestCountService) {
        this.requestCountService = requestCountService;
    }

    @Operation(summary = "Получить количество обращений к сервису IpInfoService")
    @GetMapping("count")
    public int count() {
        return requestCountService.count();
    }
}
