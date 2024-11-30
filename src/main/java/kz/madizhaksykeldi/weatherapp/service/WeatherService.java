package kz.madizhaksykeldi.weatherapp.service;

import kz.madizhaksykeldi.weatherapp.dto.WeatherDto;
import kz.madizhaksykeldi.weatherapp.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherDto getCurrentWeather(String cityName) {
        String url = String.format("https://api.weatherapi.com/v1/current.json?key=%s&q=%s", apiKey, cityName);
        try {
            return restTemplate.getForObject(url, WeatherDto.class);
        } catch (HttpClientErrorException e) {
            throw new ApiException("Error fetching weather data: " + e.getMessage());
        }
    }

}
