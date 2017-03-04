package com.myocr.controller;

import com.myocr.controller.json.ReceiptRequest;
import com.myocr.entity.ReceiptItem;
import com.myocr.model.align.DataBaseFinder;
import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.repository.ReceiptItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/find")
public class FinderController {
    final static Logger log = LoggerFactory.getLogger(FinderController.class);

    private final ReceiptItemRepository receiptItemRepository;

    public FinderController(ReceiptItemRepository receiptItemRepository) {
        this.receiptItemRepository = receiptItemRepository;
    }

    @PostMapping("/receipt")
    public List<ReceiptItemMatches> findReceipt(@RequestBody ReceiptRequest request) {
        final List<ReceiptItem> receiptItems = receiptItemRepository.
                findByCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(
                        request.getCityName(), request.getShopName());

        final List<String> receipts = receiptItems.stream().map(ReceiptItem::getName).collect(Collectors.toList());
        final DataBaseFinder finder = new DataBaseFinder(receipts);
        return finder.findAll(request.getItems());
    }
}