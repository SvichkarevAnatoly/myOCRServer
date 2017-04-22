package com.myocr.controller;

import com.myocr.controller.json.ReceiptRequest;
import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.service.ReceiptItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        final ReceiptItemService service = new ReceiptItemService(receiptItemRepository);
        return service.findReceipt(request.getCityName(), request.getShopName(), request.getItems());
    }
}