package com.myocr.controller;

import com.myocr.controller.json.PriceDateReceiptItemResponse;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.PriceRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receiptItems")
public class ReceiptItemController {
    private final CityShopReceiptItemRepository cityShopReceiptItemRepository;
    private final PriceRepository priceRepository;

    public ReceiptItemController(CityShopReceiptItemRepository cityShopReceiptItemRepository,
                                 PriceRepository priceRepository) {
        this.cityShopReceiptItemRepository = cityShopReceiptItemRepository;
        this.priceRepository = priceRepository;
    }

    @GetMapping("/{city}/{shop}")
    List<PriceDateReceiptItemResponse> findReceiptItems(@PathVariable String city, @PathVariable String shop) {
        final Collection<CityShopReceiptItem> cityShopReceiptItems = cityShopReceiptItemRepository.
                findByCityShopCityNameAndCityShopShopName(city, shop);

        final List<PriceDateReceiptItemResponse> response = new ArrayList<>();
        // todo: can be optimized
        for (CityShopReceiptItem item : cityShopReceiptItems) {
            final String receiptItem = item.getReceiptItem().getName();
            final Collection<Price> prices = priceRepository.findByCityShopReceiptItemId(item.getId());
            final List<PriceDateReceiptItemResponse> items = prices.stream()
                    .map(price -> new PriceDateReceiptItemResponse(
                            receiptItem, price.getValue(), price.getTime().toString()))
                    .collect(Collectors.toList());
            response.addAll(items);
        }

        return response;
    }
}
