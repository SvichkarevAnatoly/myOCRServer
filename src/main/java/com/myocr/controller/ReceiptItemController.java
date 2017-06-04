package com.myocr.controller;

import com.myocr.repository.ReceiptItemRepository;
import com.myocr.service.ReceiptItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/receiptItems")
public class ReceiptItemController {
    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @GetMapping("/{cityId}/{shopId}")
    List<String> getNamesInCityShop(@PathVariable long cityId, @PathVariable long shopId) {
        final ReceiptItemService service = new ReceiptItemService(receiptItemRepository);
        return service.getNamesInCityShop(cityId, shopId);
    }
}
