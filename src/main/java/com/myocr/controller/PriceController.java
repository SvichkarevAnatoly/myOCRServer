package com.myocr.controller;

import com.myocr.controller.json.SavePriceRequest;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.PriceRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;
    private final CityShopReceiptItemRepository cityShopReceiptItemRepository;

    public PriceController(PriceRepository priceRepository,
                           CityShopReceiptItemRepository cityShopReceiptItemRepository) {
        this.priceRepository = priceRepository;
        this.cityShopReceiptItemRepository = cityShopReceiptItemRepository;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Iterable<Price> save(@RequestBody SavePriceRequest request) {
        final Date now = Calendar.getInstance().getTime();
        final List<Price> prices = new ArrayList<>();
        for (SavePriceRequest.ReceiptPriceItem requestItem : request.getItems()) {
            final CityShopReceiptItem item = cityShopReceiptItemRepository
                    .findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(
                            requestItem.getName(), request.getCityName(), request.getShopName());

            final Price price = new Price(requestItem.getPrice(), now, item);
            prices.add(price);
        }

        return priceRepository.save(prices);
    }
}
