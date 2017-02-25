package com.myocr.repository;

import com.myocr.entity.ReceiptItem;
import org.springframework.data.repository.CrudRepository;

public interface ReceiptItemRepository extends CrudRepository<ReceiptItem, Long> {
    ReceiptItem findByName(String name);
}
