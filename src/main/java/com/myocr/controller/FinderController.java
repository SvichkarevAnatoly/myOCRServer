package com.myocr.controller;

import com.myocr.controller.json.PriceDateReceiptItemResponse;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/find")
public class FinderController {

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Autowired
    private PriceRepository priceRepository;

    @GetMapping("/receiptItems")
    public List<String> findReceiptItemsLike(@RequestParam("q") String substring) {
        final List<ReceiptItem> receiptItems = receiptItemRepository.findByNameIgnoreCaseContaining(substring);
        return receiptItems.stream().map(ReceiptItem::getName).collect(Collectors.toList());
    }

    @GetMapping("/prices")
    List<PriceDateReceiptItemResponse> findReceiptItemsLikeInCityAndShop(
            @RequestParam String city, @RequestParam(required = false) String shop,
            @RequestParam("q") String receiptItemSubstring) {
        final Collection<Price> entities;
        if (shop != null) {
            entities = priceRepository
                    .findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingAndCityShopReceiptItemCityShopCityNameAndCityShopReceiptItemCityShopShopNameOrderByTimeDesc(
                            receiptItemSubstring, city, shop);
        } else {
            entities = priceRepository
                    .findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingAndCityShopReceiptItemCityShopCityNameOrderByTimeDesc(
                            receiptItemSubstring, city);
        }

        final List<PriceDateReceiptItemResponse> response = new ArrayList<>();
        for (Price entity : entities) {
            final String item = entity.getCityShopReceiptItem().getReceiptItem().getName();
            final int price = entity.getValue();
            final String date = TimeUtil.parse(entity.getTime());
            final PriceDateReceiptItemResponse pdrir = new PriceDateReceiptItemResponse(item, price, date);
            response.add(pdrir);
        }
        return response;
    }
}