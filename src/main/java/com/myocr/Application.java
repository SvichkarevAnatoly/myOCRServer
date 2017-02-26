package com.myocr;

import com.myocr.entity.City;
import com.myocr.repository.CityRepository;
import com.myocr.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Arrays;
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
        /*spb = cityRepository.save(new City(cityNames.get(0)));
        final City nsk = cityRepository.save(new City(cityNames.get(1)));

        final Shop auchan = new Shop(shopNames.get(0));
        final Shop prisma = new Shop(shopNames.get(1));
        final Shop karusel = new Shop(shopNames.get(2));
        final Shop megas = new Shop(shopNames.get(3));

        CityShop.link(spb, auchan);
        CityShop.link(spb, prisma);
        CityShop.link(spb, karusel);
        CityShop.link(nsk, auchan);
        CityShop.link(nsk, megas);

        shopRepository.save(Arrays.asList(auchan, prisma, karusel, megas));

        // cityRepository.deleteAll();*/
    }
}
