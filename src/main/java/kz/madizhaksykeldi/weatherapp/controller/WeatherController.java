package kz.madizhaksykeldi.weatherapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Weather Controller", description = "Getting information about weather")
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;
    private final CityService cityService;

    @Operation(summary = "Get current weather", description = "Returns current weather of city by ID")
    @ApiResponse(responseCode = "200", description = "Current weather has been retrieved",
            content = @Content(schema = @Schema(implementation = WeatherDto.class)))
    @ApiResponse(responseCode = "404", description = "City not found")
    @GetMapping("/current/{cityId}")
    public ResponseEntity<WeatherDto> getCurrentWeather(@PathVariable Long cityId) {
        logger.info("Request for current weather of city with ID: {}", cityId);
        City city = cityService.getCityById(cityId)
                .orElseThrow(() -> {
                    logger.error("City with iD {} not found", cityId);
                    return new ApiException("City not found");
                });
        logger.info("City found: {}", city.getName());
        WeatherDto weather = weatherService.getCurrentWeather(city.getName());
        logger.debug("Current weather of city {}: {}", city.getName(), weather);
        return ResponseEntity.ok(weather);
    }

    @GetMapping("/forecast/{cityId}")
    public ResponseEntity<ForecastDto> getWeatherForecast(@PathVariable Long cityId,
                                                          @RequestParam int days) {
        logger.info("Request for weather forecast of city with ID: {} for {} days", cityId, days);
        City city = cityService.getCityById(cityId)
                .orElseThrow(() -> {
                    logger.error("City with ID {} not found", cityId);
                    return new ApiException("City not found");
                });
        logger.info("City found: {}", city.getName());
        ForecastDto forecast = weatherService.getWeatherForecast(city.getName(), days);
        logger.debug("Weather forecast for city{} for {} days: {}", city.getName(), days, forecast);
        return ResponseEntity.ok(forecast);
    }

}
