package com.myocr.repository;

import com.myocr.entity.Price;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;

public interface PriceRepository extends CrudRepository<Price, Long> {
    Price findByCityShopReceiptItemIdAndValueAndTime(long itemId, int value, Date time);

    Collection<Price> findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingOrderByTimeDesc(String receiptItemSubstring);

    Collection<Price> findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingAndCityShopReceiptItemCityShopCityIdOrderByTimeDesc(String receiptItemSubstring, long cityId);

    Collection<Price> findByCityShopReceiptItemReceiptItemNameIgnoreCaseContainingAndCityShopReceiptItemCityShopCityIdAndCityShopReceiptItemCityShopShopIdOrderByTimeDesc(String receiptItemSubstring, long city, long shop);
}
