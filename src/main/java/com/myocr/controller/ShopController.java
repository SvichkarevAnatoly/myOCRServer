package com.myocr.controller;

import com.myocr.entity.Shop;
import com.myocr.repository.ShopRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @RequestMapping(value = "/inCity/{cityName}", method = RequestMethod.GET)
    Collection<Shop> findShopsByCityName(@PathVariable String cityName) {
        return shopRepository.findByCityShops_City_Name(cityName);
    }
}
