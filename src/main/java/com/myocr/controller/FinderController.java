package com.myocr.controller;

import com.myocr.controller.json.RequestReceipt;
import com.myocr.controller.json.ResponseFindResult;
import com.myocr.controller.json.ResponseMatch;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/find")
public class FinderController {
    final static Logger log = LoggerFactory.getLogger(FinderController.class);

    private final PriceRepository priceRepository;
    private final CityShopRepository cityShopRepository;
    private final ReceiptItemRepository receiptItemRepository;
    private final CityShopReceiptItemRepository cityShopReceiptItemRepository;

    public FinderController(PriceRepository priceRepository,
                            CityShopRepository cityShopRepository,
                            ReceiptItemRepository receiptItemRepository,
                            CityShopReceiptItemRepository cityShopReceiptItemRepository) {
        this.priceRepository = priceRepository;
        this.cityShopRepository = cityShopRepository;
        this.receiptItemRepository = receiptItemRepository;
        this.cityShopReceiptItemRepository = cityShopReceiptItemRepository;
    }

    @PostMapping("/receipt")
    public List<ResponseFindResult> findReceipt(@RequestBody RequestReceipt request) {
        final List<ResponseFindResult> results = new ArrayList<>();
        for (String item : request.getItems()) {
            final ResponseMatch match = new ResponseMatch(item, 100);
            final ResponseFindResult findResult = new ResponseFindResult(Collections.singletonList(match));
            results.add(findResult);
        }

        return results;
    }
}