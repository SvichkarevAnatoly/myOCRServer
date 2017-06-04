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
import java.util.Collections;
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
    List<PriceDateReceiptItemResponse> findReceiptItemsLike(
            @RequestParam(value = "q", required = false) String receiptItemSubstring,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long shopId) {
        final Iterable<Price> entities = getEntities(receiptItemSubstring, cityId, shopId);

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

    // get prices by query
    private Iterable<Price> getEntities(String receiptItemSubstring, Long cityId, Long shopId) {
        if (cityId == null && shopId == null) {
            if (receiptItemSubstring == null) {
                return priceRepository.findAll();
            } else {
                return priceRepository
                        .findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingOrderByTimeDesc(receiptItemSubstring);
            }
        }

        if (receiptItemSubstring == null) {
            receiptItemSubstring = "";
        }

        if (shopId != null) {
            if (cityId == null) {
                return Collections.emptyList();
            }
            return priceRepository
                    .findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingAndCityShopReceiptItemCityShopCityIdAndCityShopReceiptItemCityShopShopIdOrderByTimeDesc(
                            receiptItemSubstring, cityId, shopId);
        } else {
            return priceRepository
                    .findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingAndCityShopReceiptItemCityShopCityIdOrderByTimeDesc(
                            receiptItemSubstring, cityId);
        }
    }
}