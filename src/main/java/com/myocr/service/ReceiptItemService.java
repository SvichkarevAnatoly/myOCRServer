package com.myocr.service;

import com.myocr.entity.ReceiptItem;
import com.myocr.model.align.DataBaseFinder;
import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.repository.ReceiptItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReceiptItemService {
    final static Logger log = LoggerFactory.getLogger(ReceiptItemService.class);

    private final ReceiptItemRepository receiptItemRepository;

    public ReceiptItemService(ReceiptItemRepository receiptItemRepository) {
        this.receiptItemRepository = receiptItemRepository;
    }

    public List<ReceiptItemMatches> findReceipt(String city, String shop, List<String> ocrReceiptItems) {
        final List<ReceiptItem> receiptItems = receiptItemRepository.
                findByCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(
                        city, shop);

        log.info(Arrays.toString(receiptItems.toArray()));

        final List<String> receipts = receiptItems.stream().map(ReceiptItem::getName).collect(Collectors.toList());
        final DataBaseFinder finder = new DataBaseFinder(receipts);
        return finder.findAll(ocrReceiptItems);
    }
}
