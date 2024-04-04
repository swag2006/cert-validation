package com.ssl.certvalidation.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ssl.certvalidation.model.Weather;

@Service
public class WeatherService {

    private final String weatherApiUrl;
    private final RestTemplate restTemplate;    // Or OkHttpClient for custom SSL config

    public WeatherService(String weatherApiUrl, RestTemplateBuilder restTemplateBuilder) {
        this.weatherApiUrl = weatherApiUrl;
        this.restTemplate = restTemplateBuilder.build();
    }

    public Weather getWeather() {
        ResponseEntity<Weather> response = restTemplate.getForEntity(weatherApiUrl, Weather.class);
        return response.getBody();
    }
}