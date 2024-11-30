package kz.madizhaksykeldi.weatherapp.controller;

import kz.madizhaksykeldi.weatherapp.model.City;
import kz.madizhaksykeldi.weatherapp.service.CityService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
@RequiredArgsConstructor
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);
    private final CityService cityService;

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        logger.info("Запрос на создание города: {}", city);
        City savedCity = cityService.saveCity(city);
        logger.info("Город создан: {}", savedCity);
        return ResponseEntity.ok(savedCity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        logger.info("Запрос на получение города с ID: {}", id);
        return cityService.getCityById(id)
                .map(city -> {
                    logger.info("Город найден: {}", city);
                    return ResponseEntity.ok(city);
                })
                .orElseGet(() -> {
                    logger.warn("Город с ID {} не найден", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        logger.info("Запрос на получение всех городов");
        List<City> cities = cityService.getAllCities();
        logger.info("Найдено городов: {}", cities.size());
        return ResponseEntity.ok(cities);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        logger.info("Запрос на удаление города с ID: {}", id);
        cityService.deleteCity(id);
        logger.info("Город с ID {} удалён", id);
        return ResponseEntity.noContent().build();
    }

}
