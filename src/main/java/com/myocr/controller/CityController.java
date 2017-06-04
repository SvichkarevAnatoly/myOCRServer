package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    Iterable<City> getAll() {
        return cityRepository.findAll();
    }
}
