package com.myocr.repository;

import com.myocr.entity.CityShop;
import org.springframework.data.repository.CrudRepository;

public interface CityShopRepository extends CrudRepository<CityShop, Long> {
    CityShop findByCityNameAndShopName(String cityId, String shopId);

    CityShop findByCityIdAndShopId(long cityId, long shopId);
}
