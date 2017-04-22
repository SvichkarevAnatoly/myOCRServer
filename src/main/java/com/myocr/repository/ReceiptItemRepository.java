package com.myocr.repository;

import com.myocr.entity.ReceiptItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReceiptItemRepository extends CrudRepository<ReceiptItem, Long> {
    ReceiptItem findByName(String name);

    ReceiptItem findByNameAndCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(String receiptItemName, String cityName, String shopName);

    List<ReceiptItem> findByCityShopReceiptItemsCityShopCityNameAndCityShopReceiptItemsCityShopShopName(String cityName, String shopName);
}
