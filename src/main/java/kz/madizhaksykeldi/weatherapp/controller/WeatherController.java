package kz.madizhaksykeldi.weatherapp.controller;

import kz.madizhaksykeldi.weatherapp.dto.ForecastDto;
import kz.madizhaksykeldi.weatherapp.dto.WeatherDto;
import kz.madizhaksykeldi.weatherapp.exception.ApiException;
import kz.madizhaksykeldi.weatherapp.model.City;
import kz.madizhaksykeldi.weatherapp.service.CityService;
import kz.madizhaksykeldi.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/forecast/{cityId}")
    public ResponseEntity<ForecastDto> getWeatherForecast(@PathVariable Long cityId,
                                                          @RequestParam int days) {
        City city = cityService.getCityById(cityId)
                .orElseThrow(() -> new ApiException("City not found"));
        return ResponseEntity.ok(weatherService.getWeatherForecast(city.getName(), days));
    }

}
