package com.myocr.repository;

import com.myocr.entity.CityShopReceiptItem;
import org.springframework.data.repository.CrudRepository;

public interface CityShopReceiptItemRepository extends CrudRepository<CityShopReceiptItem, Long> {
    CityShopReceiptItem findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(String receiptItemName, String cityName, String shopName);
}
