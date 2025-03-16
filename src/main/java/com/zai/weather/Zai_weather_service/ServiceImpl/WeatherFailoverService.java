package com.zai.weather.Zai_weather_service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.zai.weather.Zai_weather_service.Modal.WeatherResponse;
import com.zai.weather.Zai_weather_service.Service.WeatherService;


@Service
public class WeatherFailoverService {
    private final WeatherStackService weatherStackService;
    private final OpenWeatherMapService openWeatherMapService;
    
    @Autowired
    public WeatherFailoverService(WeatherStackService weatherStackService, OpenWeatherMapService openWeatherMapService) {
        this.weatherStackService = weatherStackService;
        this.openWeatherMapService = openWeatherMapService;
    }

    @Cacheable(value = "weatherCache", key = "#city", unless = "#result == null", cacheManager = "cacheManager")
    public WeatherResponse getWeather(String city) {
        try {
            return weatherStackService.getWeather(city);
        } catch (Exception e1) {
            try {
                return openWeatherMapService.getWeather(city);
            } catch (Exception e2) {
                throw new RuntimeException("All weather providers failed.");
            }
        }
    }
}