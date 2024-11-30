package kz.madizhaksykeldi.weatherapp.service;

import kz.madizhaksykeldi.weatherapp.dto.ForecastDto;
import kz.madizhaksykeldi.weatherapp.dto.WeatherDto;
import kz.madizhaksykeldi.weatherapp.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    @Value("${weather.api.key}")
    private String apiKey;

    @Cacheable(value = "currentWeather", key = "#cityName", unless = "#result == null", cacheManager = "cacheManager")
    public WeatherDto getCurrentWeather(String cityName) {
        String url = String.format("https://api.weatherapi.com/v1/current.json?key=%s&q=%s", apiKey, cityName);
        try {
            return restTemplate.getForObject(url, WeatherDto.class);
        } catch (HttpClientErrorException e) {
            throw new ApiException("Error fetching weather data: " + e.getMessage());
        }
    }

    @Cacheable(value = "weatherForecast", key = "#cityName + #days", unless = "#result == null", cacheManager = "cacheManager")
    public ForecastDto getWeatherForecast(String cityName, int days) {
        String url = String.format("https://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=%d", apiKey, cityName, days);
        try {
            return restTemplate.getForObject(url, ForecastDto.class);
        } catch (HttpClientErrorException e) {
            throw new ApiException("Error fetching weather forecast: " + e.getMessage());
        }
    }

}
