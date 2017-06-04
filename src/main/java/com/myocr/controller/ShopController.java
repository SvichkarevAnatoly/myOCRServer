package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.ShopRepository;
import com.myocr.util.TextFieldUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final static Logger log = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityShopRepository cityShopRepository;

    @RequestMapping(value = "/inCity/{cityId}", method = RequestMethod.GET)
    Collection<Shop> findShopsByCityName(@PathVariable long cityId) {
        log.info("Find shops in cityId=" + cityId);
        return shopRepository.findByCityShopsCityId(cityId);
    }

    @PutMapping("add/{cityId}")
    public boolean addNewShop(@PathVariable long cityId, @RequestBody String shop) {
        final City savedCity = cityRepository.findOne(cityId);
        log.info("Add new shop " + shop + " in city " + savedCity.getName());

        if (!isValid(savedCity.getName(), shop)) {
            return false;
        }

        String preparedShop = TextFieldUtil.prepare(shop);
        Shop savedShop = shopRepository.findByName(preparedShop);
        if (savedShop == null) {
            savedShop = shopRepository.save(new Shop(preparedShop));
        }

        final CityShop cityShop = new CityShop(savedCity, savedShop);
        cityShopRepository.save(cityShop);
        return true;
    }

    private boolean isValid(String city, String shop) {
        if (!TextFieldUtil.isValid(shop)) {
            return false;
        }

        final CityShop savedCityShop = cityShopRepository.findByCityNameAndShopName(city, shop);
        if (savedCityShop != null) {
            log.info("Shop " + shop + " in city " + city + " has already existed");
            return false;
        }
        return true;
    }
}
