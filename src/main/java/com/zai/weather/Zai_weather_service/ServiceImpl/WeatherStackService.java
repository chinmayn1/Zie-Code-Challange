package com.zai.weather.Zai_weather_service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.zai.weather.Zai_weather_service.Modal.WeatherResponse;
import com.zai.weather.Zai_weather_service.Service.WeatherService;

@Service
@Primary
public class WeatherStackService implements WeatherService {
	private final RestTemplate restTemplate;
	private final String weatherStackApiKey = "2332efd28f9fdbce96e5605a835fda74";
	private final String openWeatherApiKey = "f6d595b0a73046cf5b9c89cf79a2cb3c";

	@Autowired
	public WeatherStackService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public WeatherResponse getWeather(String city) {
//		String weatherStackUrl = "http://api.weatherstack.com/current?access_key=" + weatherStackApiKey + "&query="
//				+ city;
		String weatherStackUrl = "http://api.weatherstack.com/current?access_key=" + "INVALID_API_KEY" + "&query="
	            + city;
		try {
			// Try calling WeatherStack API first
			ResponseEntity<JsonNode> response = restTemplate.getForEntity(weatherStackUrl, JsonNode.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				JsonNode data = response.getBody();
				JsonNode currentNode = data.path("current");
				
				//return new WeatherResponse(data.path("current").path("wind_speed").asDouble(),
				//		data.path("current").path("temperature").asDouble());
				if (currentNode.has("wind_speed") && currentNode.has("temperature")) {
				    return new WeatherResponse(
				        currentNode.path("wind_speed").asDouble(),
				        currentNode.path("temperature").asDouble()
				    );
				} else {
				    // Return a default response or throw an exception
				    return getWeatherFromOpenWeatherMap(city); // Example default values
				}
			} else {
				// If WeatherStack response is invalid, fallback to OpenWeatherMap
				throw new RuntimeException("Error from WeatherStack, fallback to OpenWeatherMap.");
			}
		} catch (Exception e) {
			// If WeatherStack fails, try OpenWeatherMap
			return getWeatherFromOpenWeatherMap(city);
		}
	}

	private WeatherResponse getWeatherFromOpenWeatherMap(String city) {
		String openWeatherMapUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid="
				+ openWeatherApiKey + "&units=metric";
		try {
			// Call OpenWeatherMap API if WeatherStack fails
			ResponseEntity<JsonNode> response = restTemplate.getForEntity(openWeatherMapUrl, JsonNode.class);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				JsonNode data = response.getBody();
				return new WeatherResponse(data.path("wind").path("speed").asDouble(),
						data.path("main").path("temp").asDouble());
			} else {
				// If OpenWeatherMap fails, throw an error
				throw new RuntimeException("Error from OpenWeatherMap.");
			}
		} catch (Exception e) {
			// If OpenWeatherMap also fails, handle the error
			throw new RuntimeException("Both WeatherStack and OpenWeatherMap services failed: " + e.getMessage(), e);
		}
	}
}