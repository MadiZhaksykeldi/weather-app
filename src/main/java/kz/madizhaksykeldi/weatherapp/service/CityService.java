package kz.madizhaksykeldi.weatherapp.service;

import kz.madizhaksykeldi.weatherapp.model.City;
import kz.madizhaksykeldi.weatherapp.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

}

