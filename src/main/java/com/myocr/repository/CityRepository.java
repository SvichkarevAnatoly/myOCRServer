package com.myocr.repository;

import com.myocr.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Long> {
    City findByName(String cityName);
}
