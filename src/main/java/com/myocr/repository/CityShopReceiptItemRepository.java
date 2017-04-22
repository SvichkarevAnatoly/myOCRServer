package com.myocr.repository;

import com.myocr.entity.CityShopReceiptItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CityShopReceiptItemRepository extends CrudRepository<CityShopReceiptItem, Long> {
    CityShopReceiptItem findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(String receiptItemName, String cityName, String shopName);

    Collection<CityShopReceiptItem> findByCityShopCityNameAndCityShopShopName(String cityName, String shopName);
}
