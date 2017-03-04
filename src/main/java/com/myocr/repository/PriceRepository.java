package com.myocr.repository;

import com.myocr.entity.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PriceRepository extends CrudRepository<Price, Long> {
    Collection<Price> findByCityShopReceiptItemId(Long cityShopReceiptItemId);
}
