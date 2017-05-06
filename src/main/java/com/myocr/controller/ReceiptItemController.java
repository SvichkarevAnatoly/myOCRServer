package com.myocr.controller;

import com.myocr.repository.ReceiptItemRepository;
import com.myocr.service.ReceiptItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/receiptItems")
public class ReceiptItemController {
    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @GetMapping
    List<String> getNamesInCityShop(@RequestParam String city,
                                    @RequestParam String shop) {
        final ReceiptItemService service = new ReceiptItemService(receiptItemRepository);
        return service.getNamesInCityShop(city, shop);
    }
}
