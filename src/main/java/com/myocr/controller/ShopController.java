package com.myocr.controller;

import com.myocr.repository.CityRepository;
import com.myocr.repository.ShopRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final CityRepository cityRepository;
    private final ShopRepository shopRepository;

    public ShopController(CityRepository cityRepository, ShopRepository shopRepository) {
        this.cityRepository = cityRepository;
        this.shopRepository = shopRepository;
    }

    /*@RequestMapping(value = "/readShops/{cityName}", method = RequestMethod.GET)
    Collection<Shop> readShops(@PathVariable String cityName) {
        return shopRepository.findByCityName(cityName);
    }*/
}
