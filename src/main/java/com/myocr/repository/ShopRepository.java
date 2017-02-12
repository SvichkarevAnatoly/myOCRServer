package com.myocr.repository;

import com.myocr.entity.Shop;
import org.springframework.data.repository.CrudRepository;

public interface ShopRepository extends CrudRepository<Shop, Long> {
    // Collection<Shop> findByCityName(String name);
}
