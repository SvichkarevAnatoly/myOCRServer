package com.myocr;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        final City city = new City("Novosibirsk");
        final Shop shop = new Shop("Auchan");

        final CityShop cityShop = new CityShop();
        cityShop.setCity(city);
        cityShop.setShop(shop);

        shop.getCityShops().add(cityShop);
        city.getCityShops().add(cityShop);

        System.out.println(shopRepository.count());

        cityRepository.save(city);
        shopRepository.save(shop);

        // test
        System.out.println(shopRepository.count());
        final Shop shop1 = shopRepository.findOne(1L);
        System.out.println(shop1.getCityShops().size());

        // update
        shop.getCityShops().remove(cityShop);
        shopRepository.save(shop);

        // test
        System.out.println(shopRepository.count());
        final Shop shop2 = shopRepository.findOne(1L);
        System.out.println(shop2.getCityShops().size());

        // test
        final City city1 = cityRepository.findOne(1L);
        System.out.println(city1.getCityShops().size());
    }
}
