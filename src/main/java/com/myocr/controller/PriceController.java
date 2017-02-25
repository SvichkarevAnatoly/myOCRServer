package com.myocr.controller;

import com.myocr.controller.json.RequestPriceBody;
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
    public Price save(@RequestBody RequestPriceBody request) {
        final CityShop cityShop = cityShopRepository.findByCityNameAndShopName(
                request.getCityName(), request.getShopName());

        final ReceiptItem receiptItem = receiptItemRepository.findByName(request.getReceiptItemName());

        final Price price = new Price(request.getPriceValue(), receiptItem, cityShop);
        return priceRepository.save(price);
    }
}
