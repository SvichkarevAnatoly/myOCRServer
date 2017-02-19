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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    private List<String> cityNames = Arrays.asList("Spb", "Nsk");
    private List<String> shopNames = Arrays.asList("Auchan", "Prisma", "Karusel", "Megas");
    private City spb;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        spb = cityRepository.save(new City(cityNames.get(0)));
        final City nsk = cityRepository.save(new City(cityNames.get(1)));

        final Shop auchan = shopRepository.save(new Shop(shopNames.get(0)));
        final Shop prisma = shopRepository.save(new Shop(shopNames.get(1)));
        final Shop karusel = shopRepository.save(new Shop(shopNames.get(2)));
        final Shop megas = shopRepository.save(new Shop(shopNames.get(3)));

        final CityShop spbAuchan = CityShop.link(spb, auchan);
        CityShop.link(spb, prisma);
        CityShop.link(spb, karusel);
        CityShop.link(nsk, auchan);
        CityShop.link(nsk, megas);

        final Shop oneShop = shopRepository.findOne(1L);
        System.out.println(oneShop.getCityShops().size());

        final Collection<Shop> spbShops = shopRepository.findByCityShops_cityShopId_city_name("Spb");
        System.out.println(spbShops.size());

        // final Price price = new Price();
        // final PriceId priceId = new PriceId();
        // priceId.setCityShopId(spbAuchan.getCityShopId());
        // price.setPriceId(priceId);
        // priceRepository.save(price);
    }
}
