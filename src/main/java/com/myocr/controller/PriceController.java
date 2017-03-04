package com.myocr.controller;

import com.myocr.controller.json.SavePriceRequest;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {
    private final PriceRepository priceRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final CityShopRepository cityShopRepository;
    private final CityShopReceiptItemRepository cityShopReceiptItemRepository;

    public PriceController(PriceRepository priceRepository,
                           ReceiptItemRepository receiptItemRepository,
                           CityShopRepository cityShopRepository,
                           CityShopReceiptItemRepository cityShopReceiptItemRepository) {
        this.priceRepository = priceRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.cityShopRepository = cityShopRepository;
        this.cityShopReceiptItemRepository = cityShopReceiptItemRepository;
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Iterable<Price> save(@RequestBody SavePriceRequest request) {
        final Date now = new Date();
        final List<Price> prices = new ArrayList<>();
        for (SavePriceRequest.ReceiptPriceItem requestItem : request.getItems()) {
            final CityShopReceiptItem item = getCityShopReceiptItem(
                    requestItem.getName(), request.getCityName(), request.getShopName());
            final Price price = new Price(requestItem.getPrice(), now, item);
            prices.add(price);
        }

        return priceRepository.save(prices);
    }

    private CityShopReceiptItem getCityShopReceiptItem(String receiptItem, String city, String shop) {
        CityShopReceiptItem item = cityShopReceiptItemRepository
                .findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(receiptItem, city, shop);
        if (item == null) {
            final CityShop dbCityShop = cityShopRepository.findByCityNameAndShopName(city, shop);
            if (dbCityShop == null) {
                throw new IllegalArgumentException("Shop in city must exist!");
            }

            ReceiptItem dbItem = receiptItemRepository.findByName(receiptItem);
            if (dbItem == null) {
                dbItem = receiptItemRepository.save(new ReceiptItem(receiptItem));
            }

            item = cityShopReceiptItemRepository.save(new CityShopReceiptItem(dbItem, dbCityShop));
        }

        return item;
    }
}
