package com.myocr.repository;

import com.myocr.entity.CityShopReceiptItem;
import org.springframework.data.repository.CrudRepository;

public interface CityShopReceiptItemRepository extends CrudRepository<CityShopReceiptItem, Long> {
    CityShopReceiptItem findByReceiptItemNameAndCityShopCityIdAndCityShopShopId(String receiptItemName, long cityId, long shopId);

    CityShopReceiptItem findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(String receiptItemName, String cityName, String shopName);
}
