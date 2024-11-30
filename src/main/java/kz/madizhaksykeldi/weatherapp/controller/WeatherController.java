package kz.madizhaksykeldi.weatherapp.controller;

import kz.madizhaksykeldi.weatherapp.dto.WeatherDto;
import kz.madizhaksykeldi.weatherapp.exception.ApiException;
import kz.madizhaksykeldi.weatherapp.model.City;
import kz.madizhaksykeldi.weatherapp.service.CityService;
import kz.madizhaksykeldi.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;
    private final CityService cityService;

    @GetMapping("/current/{cityId}")
    public ResponseEntity<WeatherDto> getCurrentWeather(@PathVariable Long cityId) {
        City city = cityService.getCityById(cityId)
                .orElseThrow(() -> new ApiException("City not found"));
        return ResponseEntity.ok(weatherService.getCurrentWeather(city.getName()));
    }

}
