package com.myocr.service;

import com.myocr.entity.ReceiptItem;
import com.myocr.model.align.DataBaseFinder;
import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.repository.ReceiptItemRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ReceiptItemService {
    private final ReceiptItemRepository receiptItemRepository;

    public ReceiptItemService(ReceiptItemRepository receiptItemRepository) {
        this.receiptItemRepository = receiptItemRepository;
    }

    public List<ReceiptItemMatches> findReceipt(String city, String shop, List<String> ocrReceiptItems) {
        final List<String> receipts = getNamesInCityShop(city, shop);
        final DataBaseFinder finder = new DataBaseFinder(receipts);
        return finder.findAll(ocrReceiptItems);
    }

    public List<String> getNamesInCityShop(String city, String shop) {
        final List<ReceiptItem> receiptItems = receiptItemRepository.
                findByCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(
                        city, shop);
        return receiptItems.stream().map(ReceiptItem::getName)
                .collect(Collectors.toList());
    }
}
