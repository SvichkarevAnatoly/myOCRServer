package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    List<String> getAll() {
        final Iterable<City> cities = cityRepository.findAll();
        final ArrayList<String> cityNames = new ArrayList<>();
        cities.forEach(city -> cityNames.add(city.getName()));
        return cityNames;
    }
}
