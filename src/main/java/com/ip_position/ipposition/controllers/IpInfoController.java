package com.ip_position.ipposition.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.ip_position.ipposition.entity.City;
import com.ip_position.ipposition.entity.IpInfo;
import com.ip_position.ipposition.entity.Provider;
import com.ip_position.ipposition.services.*;

import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/ip")
public class IpInfoController {

    private final IpInfoService ipInfoService;

    public IpInfoController(IpInfoService ipInfoService) {
        this.ipInfoService = ipInfoService;
    }

    @GetMapping("get/all")
    public List<IpInfo> getIpsInfo() {
        return ipInfoService.getIpsInfo();
    }

    @GetMapping("get/external_api")
    public IpInfo getIpInfoAPI(@RequestParam(required = true) String ip) {
        return ipInfoService.getIpInfoFromAPI(ip);
    }

    @GetMapping("get/db")
    public IpInfo getIpInfoDB(@RequestParam(required = true) String ip) {
        return ipInfoService.getIpInfoFromDB(ip);
    }

    @GetMapping("get/providers")
    public Set<Provider> getCitiesOfProvider(@RequestParam(required = true) String cityName) {
        return ipInfoService.getProvidersOfCity(cityName);
    }

    @GetMapping("get/cities")
    public Set<City> getProvidersOfCity(@RequestParam(required = true) String providerIsp) {
        return ipInfoService.getCitiesOfProvider(providerIsp);
    }

    @PostMapping("post")
    public void addNewIpInfo(@RequestBody IpInfo ipInfo) {
        ipInfoService.addNewIpInfo(ipInfo);
    }

    @DeleteMapping("delete")
    public void deleteIpInfo(@RequestParam(required = true) Long id) {
        ipInfoService.deleteIpInfo(id);
    }

    @PutMapping("put")
    public void updateIpInfo(@RequestParam(required = true) Long id) {
        ipInfoService.updateIpInfo(id);
    }
}
