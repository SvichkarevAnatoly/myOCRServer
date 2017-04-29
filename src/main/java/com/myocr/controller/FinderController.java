package com.myocr.controller;

import com.myocr.entity.ReceiptItem;
import com.myocr.repository.ReceiptItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/find")
public class FinderController {

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @GetMapping("/receiptItems")
    public List<String> findReceiptItemLike(@RequestParam("q") String substring) {
        final List<ReceiptItem> receiptItems = receiptItemRepository.findByNameIgnoreCaseContaining(substring);
        return receiptItems.stream().map(ReceiptItem::getName).collect(Collectors.toList());
    }
}