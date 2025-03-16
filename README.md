# Zai Code Challenge - Weather Service

## Instructions to Build and Run Locally

### 1. Clone the Repository or Download the ZIP  
If you have the project in a ZIP file, extract it:  
```sh
unzip zai-weather-service.zip
cd zai-weather-service
```
If using GitHub:
```sh
git clone <repo-url>
cd zai-weather-service
```
### 2. Set Up API Keys
Open WeatherStackService.java and replace placeholders with actual API keys:
```java
private final String weatherStackApiKey = "YOUR_WEATHERSTACK_API_KEY";
private final String openWeatherApiKey = "YOUR_OPENWEATHERMAP_API_KEY";
```
### 3. Build the Project
Ensure you have Java 17+ and Maven installed. Then, run:
```sh
mvn clean install
```
This will compile the project and create a JAR file inside the target/ folder.
### 4. Run the Application
Start the Spring Boot application:
```sh
mvn spring-boot:run
```
or
```sh
java -jar target/zai-weather-service-0.0.1-SNAPSHOT.jar
```
### 5. Test the API
Once the application is running, test it using curl or Postman:
```sh
curl "http://localhost:8080/v1/weather?city=melbourne"
```
Expected JSON response:
```
{
  "wind_speed": 20,
  "temperature_degrees": 29
}
```

## Trade-offs & Future Improvements
### Trade-offs Made
### 1. Failover Handling: 
-- Used a simple try-catch for failover instead of a circuit breaker (like Resilience4j).
### 2. Minimal Dependencies:
-- No external libraries apart from Spring Boot & Jackson (for JSON parsing).
### 3. Simplicity over complexity:
-- Used RestTemplate (deprecated but simple); a better approach would be WebClient

## What Could Be Improved?
### 1. Introduce Circuit Breaker (Resilience4j) to prevent cascading failures.
### 2. Better Caching (Redis) or in memory cache to reduce API calls and improve performance.
```@Cacheable(value = "weatherCache", key = "#city", unless = "#result == null", cacheManager = "cacheManager")```
### 3. Dockerize the App for containerized deployment.
### 4. Support Multiple Cities instead of hardcoding Melbourne.
