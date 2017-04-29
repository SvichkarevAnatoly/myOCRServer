package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final static Logger log = LoggerFactory.getLogger(OcrController.class);

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityShopRepository cityShopRepository;

    @RequestMapping(value = "/inCity/{cityName}", method = RequestMethod.GET)
    Collection<String> findShopsByCityName(@PathVariable String cityName) {
        log.info("Find shops in " + cityName);
        return shopRepository.findByCityShopsCityName(cityName)
                .stream().map(Shop::getName)
                .collect(Collectors.toList());
    }

    @PostMapping("add/{city}/{shop}")
    public void addNewShop(
            @PathVariable String city, @PathVariable String shop) {
        log.info("Add new shop " + shop + " in " + city);

        final CityShop savedCityShop = cityShopRepository.findByCityNameAndShopName(city, shop);
        if (savedCityShop != null) {
            log.info("Shop " + shop + " in " + city + " has already existed");
            return;
        }

        final Shop savedShop = shopRepository.save(new Shop(shop));
        final City savedCity = cityRepository.findByName(city);
        final CityShop cityShop = new CityShop(savedCity, savedShop);
        cityShopRepository.save(cityShop);
    }
}
