package com.myocr.repository;

import com.myocr.entity.Shop;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ShopRepository extends CrudRepository<Shop, Long> {
    Collection<Shop> findByCityShops_City_Name(String cityName);
}
