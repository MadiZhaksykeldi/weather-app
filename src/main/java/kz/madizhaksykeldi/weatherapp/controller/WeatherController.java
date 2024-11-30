package kz.madizhaksykeldi.weatherapp.controller;

import kz.madizhaksykeldi.weatherapp.dto.ForecastDto;
import kz.madizhaksykeldi.weatherapp.dto.WeatherDto;
import kz.madizhaksykeldi.weatherapp.exception.ApiException;
import kz.madizhaksykeldi.weatherapp.model.City;
import kz.madizhaksykeldi.weatherapp.service.CityService;
import kz.madizhaksykeldi.weatherapp.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;
    private final CityService cityService;

    @GetMapping("/current/{cityId}")
    public ResponseEntity<WeatherDto> getCurrentWeather(@PathVariable Long cityId) {
        logger.info("Получен запрос на текущую погоду для города с ID: {}", cityId);
        City city = cityService.getCityById(cityId)
                .orElseThrow(() -> {
                    logger.error("Город с ID {} не найден", cityId);
                    return new ApiException("City not found");
                });
        logger.info("Город найден: {}", city.getName());
        WeatherDto weather = weatherService.getCurrentWeather(city.getName());
        logger.debug("Текущая погода для города {}: {}", city.getName(), weather);
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/forecast/{cityId}")
    public ResponseEntity<ForecastDto> getWeatherForecast(@PathVariable Long cityId,
                                                          @RequestParam int days) {
        logger.info("Получен запрос на прогноз погоды для города с ID: {} на {} дней", cityId, days);
        City city = cityService.getCityById(cityId)
                .orElseThrow(() -> {
                    logger.error("Город с ID {} не найден", cityId);
                    return new ApiException("City not found");
                });
        logger.info("Город найден: {}", city.getName());
        ForecastDto forecast = weatherService.getWeatherForecast(city.getName(), days);
        logger.debug("Прогноз погоды для города {} на {} дней: {}", city.getName(), days, forecast);
        return ResponseEntity.ok(forecast);
    }

}
