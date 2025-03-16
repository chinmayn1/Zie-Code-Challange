package com.zai.weather.Zai_weather_service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zai.weather.Zai_weather_service.Modal.WeatherResponse;
import com.zai.weather.Zai_weather_service.Service.WeatherService;
import com.zai.weather.Zai_weather_service.ServiceImpl.WeatherFailoverService;
import com.zai.weather.Zai_weather_service.ServiceImpl.WeatherStackService;

/*
 * @RestController
 * 
 * @RequestMapping("/v1/weather") public class WeatherController { private final
 * WeatherFailoverService weatherFailoverService;
 * 
 * private WeatherService weatherService;
 * 
 * @Autowired public WeatherController(WeatherFailoverService
 * weatherFailoverService, WeatherService weatherService) {
 * this.weatherFailoverService = weatherFailoverService; this.weatherService =
 * weatherService; // Dependency injection of WeatherService
 * 
 * }
 * 
 * @GetMapping public ResponseEntity<WeatherResponse>
 * getWeather(@RequestParam(defaultValue = "melbourne") String city) { return
 * ResponseEntity.ok(weatherFailoverService.getWeather(city)); //return
 * ResponseEntity.ok(weatherService.getWeather(city)); } }
 */

@RestController
@RequestMapping("/v1/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;  // Spring injects an instance here
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam(defaultValue = "melbourne") String city) {
        return ResponseEntity.ok(weatherService.getWeather(city));  // Correctly using the instance
    }
}