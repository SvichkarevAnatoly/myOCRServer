package com.myocr.controller;

import com.myocr.repository.ShopRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopRepository shopRepository;

    public ShopController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    /*@RequestMapping(value = "/inCity/{cityName}", method = RequestMethod.GET)
    Collection<Shop> findShopsByCityName(@PathVariable String cityName) {
        return shopRepository.findByCities_Name(cityName);
    }*/
}
