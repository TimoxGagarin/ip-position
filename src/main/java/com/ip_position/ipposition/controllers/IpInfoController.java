package com.ip_position.ipposition.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.services.*;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/ip")
public class IpInfoController {

    private final IpInfoService ipInfoService;

    public IpInfoController(IpInfoService ipInfoService) {
        this.ipInfoService = ipInfoService;
    }

    @GetMapping
    public List<IpInfo> getIpInfo(@RequestParam(required = false) String ip) {
        if (ip != null && isValidIpAddress(ip))
            return List.of(ipInfoService.getIpInfo(ip));
        return ipInfoService.getIpsInfo();
    }

    private boolean isValidIpAddress(String ip) {
        return ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    }

    @PostMapping
    public void addNewIpInfo(@RequestBody IpInfo ipInfo) {
        ipInfoService.addNewIpInfo(ipInfo);
    }
}
