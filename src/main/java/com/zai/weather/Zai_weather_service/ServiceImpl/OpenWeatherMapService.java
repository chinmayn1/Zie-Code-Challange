package com.zai.weather.Zai_weather_service.ServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.zai.weather.Zai_weather_service.Modal.WeatherResponse;
import com.zai.weather.Zai_weather_service.Service.WeatherService;

@Service
public class OpenWeatherMapService implements WeatherService {
    private final RestTemplate restTemplate;
    private final String apiKey = "YOUR_OPENWEATHERMAP_API_KEY";

    public OpenWeatherMapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public WeatherResponse getWeather(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            JsonNode data = response.getBody();
            return new WeatherResponse(
                data.path("wind").path("speed").asDouble(),
                data.path("main").path("temp").asDouble()
            );
        }
        throw new RuntimeException("OpenWeatherMap service failed");
    }
}