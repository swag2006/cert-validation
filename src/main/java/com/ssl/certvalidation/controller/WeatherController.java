package com.ssl.certvalidation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class WeatherController {

    private final RestTemplate restTemplate;

    public WeatherController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(@RequestParam String zip) {
        String apiUrl = "https://weather-api.com/forecast";
        String apiKey = "your_api_key"; // Replace with your actual API key

        // Build URI with zip code parameter
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("zip", zip)
                .queryParam("apiKey", apiKey);

        // Make API call and return response
        return restTemplate.getForEntity(builder.toUriString(), String.class);
    }
}
