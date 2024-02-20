package com.ip_position.ipposition.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ip_position.ipposition.objects.IpInfo;
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

    // Service to handle business logic related to IpInfo
    private final IpInfoService ipInfoService;

    // Constructor-based dependency injection
    public IpInfoController(IpInfoService ipInfoService) {
        this.ipInfoService = ipInfoService;
    }

    // GET endpoint to retrieve IpInfo based on IP address
    @GetMapping
    public List<IpInfo> getIpInfo(@RequestParam(required = false) String ip) {
        // If IP parameter is provided, return information for that IP
        if (ip != null && isValidIpAddress(ip))
            return List.of(ipInfoService.getIpInfo(ip));

        // If no specific IP provided, return information for all IPs
        return ipInfoService.getIpsInfo();
    }

    // Additional method for validating IP addresses
    private boolean isValidIpAddress(String ip) {
        // Implementation of IP address validation (may require more thorough checking)
        // In the example, it simply checks that the passed string looks like an IP
        // address
        return ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    }

    // POST endpoint to add new IpInfo
    @PostMapping
    public void addNewIpInfo(@RequestBody IpInfo ipInfo) {
        // Delegate to the service to add the new IpInfo
        ipInfoService.addNewIpInfo(ipInfo);
    }
}
