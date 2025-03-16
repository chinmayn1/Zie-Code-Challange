package com.zai.weather.Zai_weather_service.Modal;

public class WeatherResponse {
	private double windSpeed;
	private double temperatureDegrees;
	/* Constructor, Getters and Setters */

	public WeatherResponse(double windSpeed, double temperatureDegrees) {
		super();
		this.windSpeed = windSpeed;
		this.temperatureDegrees = temperatureDegrees;
	}

	public double getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public double getTemperatureDegrees() {
		return temperatureDegrees;
	}

	public void setTemperatureDegrees(double temperatureDegrees) {
		this.temperatureDegrees = temperatureDegrees;
	}

}
