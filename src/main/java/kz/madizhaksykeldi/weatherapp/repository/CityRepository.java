package kz.madizhaksykeldi.weatherapp.repository;

import kz.madizhaksykeldi.weatherapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}

