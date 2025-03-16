package com.zai.weather.Zai_weather_service.Service;

import com.zai.weather.Zai_weather_service.Modal.WeatherResponse;

public interface WeatherService {
    public WeatherResponse getWeather(String city);

}
