package kz.madizhaksykeldi.weatherapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "City Controller", description = "CRUD operations on city")
public class CityController {

    private static final Logger logger = LoggerFactory.getLogger(CityController.class);
    private final CityService cityService;

    @Operation(summary = "Create city", description = "Created new city in database")
    @ApiResponse(responseCode = "200", description = "City successfully has been created",
            content = @Content(schema = @Schema(implementation = City.class)))
    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        logger.info("Request to create city: {}", city);
        City savedCity = cityService.saveCity(city);
        logger.info("City created: {}", savedCity);
        return ResponseEntity.ok(savedCity);
    }

    @Operation(summary = "Get city by ID", description = "Returns data about city by ID")
    @ApiResponse(responseCode = "200", description = "City found",
            content = @Content(schema = @Schema(implementation = City.class)))
    @ApiResponse(responseCode = "404", description = "City not found")
    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Long id) {
        logger.info("Request to get city by ID: {}", id);
        return cityService.getCityById(id)
                .map(city -> {
                    logger.info("City found: {}", city);
                    return ResponseEntity.ok(city);
                })
                .orElseGet(() -> {
                    logger.warn("City with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Get all cities", description = "Returns the list of all cities")
    @ApiResponse(responseCode = "200", description = "List of city successfully has been retrieved",
            content = @Content(schema = @Schema(implementation = City.class)))
    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        logger.info("Request to get all cities");
        List<City> cities = cityService.getAllCities();
        logger.info("Found cities: {}", cities.size());
        return ResponseEntity.ok(cities);
    }

    @Operation(summary = "Delete city by ID", description = "Deleting city by ID")
    @ApiResponse(responseCode = "204", description = "City successfully has been deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        logger.info("Request to delete city with ID: {}", id);
        cityService.deleteCity(id);
        logger.info("City with ID {} deleted", id);
        return ResponseEntity.noContent().build();
    }

}
