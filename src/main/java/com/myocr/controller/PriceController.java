package com.myocr.controller;

import com.myocr.controller.json.RequestPriceBody;
import com.myocr.controller.json.RequestReceiptPriceItem;
import com.myocr.entity.CityShop;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
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
    private final CityShopRepository cityShopRepository;
    private final ReceiptItemRepository receiptItemRepository;

    public PriceController(PriceRepository priceRepository,
                           CityShopRepository cityShopRepository,
                           ReceiptItemRepository receiptItemRepository) {
        this.priceRepository = priceRepository;
        this.cityShopRepository = cityShopRepository;
        this.receiptItemRepository = receiptItemRepository;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Iterable<Price> save(@RequestBody RequestPriceBody request) {
        final CityShop cityShop = cityShopRepository.findByCityNameAndShopName(
                request.getCityName(), request.getShopName());

        final Date now = Calendar.getInstance().getTime();
        final List<Price> prices = new ArrayList<>();
        for (RequestReceiptPriceItem requestItem : request.getItems()) {
            final ReceiptItem receiptItem = receiptItemRepository.findByName(requestItem.getName());
            final Price price = new Price(requestItem.getPrice(), now, receiptItem, cityShop);
            prices.add(price);
        }

        return priceRepository.save(prices);
    }
}
