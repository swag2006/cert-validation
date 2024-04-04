package com.ssl.certvalidation.model;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Weather {

    private final String weatherApiUrl;
    private final RestTemplate restTemplate; // Or OkHttpClient for custom SSL config

    public Weather(String weatherApiUrl, RestTemplateBuilder restTemplateBuilder) {
        this.weatherApiUrl = weatherApiUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Weather getWeather() {
        ResponseEntity<Weather> response = restTemplate.getForEntity(weatherApiUrl, Weather.class);
        return response.getBody();
    }
}