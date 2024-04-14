package com.ip_position.ipposition.services;

import org.springframework.stereotype.Service;

import com.ip_position.ipposition.utils.RequestCounter;

@Service
public class RequestCountService {
    private final RequestCounter requestCounter;

    public RequestCountService(RequestCounter requestCounter) {
        this.requestCounter = requestCounter;
    }

    public int count() {
        return requestCounter.getCount();
    }
}
