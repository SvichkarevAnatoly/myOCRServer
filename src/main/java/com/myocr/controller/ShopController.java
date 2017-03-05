package com.myocr.controller;

import com.myocr.entity.Shop;
import com.myocr.repository.ShopRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @RequestMapping(value = "/inCity/{cityName}", method = RequestMethod.GET)
    Collection<String> findShopsByCityName(@PathVariable String cityName) {
        return shopRepository.findByCityShopsCityName(cityName)
                .stream().map(Shop::getName)
                .collect(Collectors.toList());
    }
}
