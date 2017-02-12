package com.myocr;

import com.myocr.entity.City;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.HashSet;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // save a couple of books
        final City cityA = new City("Moscow");
        final City cityB = new City("Novosibirsk");
        final City cityC = new City("Spb");

        shopRepository.save(new HashSet<Shop>() {{
            add(new Shop("Aushan", new HashSet<City>() {{
                add(cityA);
                add(cityB);
            }}));

            add(new Shop("Prisma", new HashSet<City>() {{
                add(cityA);
                add(cityC);
            }}));
        }});

        // fetch all books
        for (Shop shop : shopRepository.findAll()) {
            logger.info(shop.toString());
        }

        // save a couple of cities
        final Shop shopA = new Shop("Shop A");
        final Shop shopB = new Shop("Shop B");

        cityRepository.save(new HashSet<City>() {{
            add(new City("City A", new HashSet<Shop>() {{
                add(shopA);
                add(shopB);
            }}));

            add(new City("City B", new HashSet<Shop>() {{
                add(shopA);
                add(shopB);
            }}));
        }});

        // fetch all cities
        for (City city : cityRepository.findAll()) {
            logger.info(city.toString());
        }
    }

    /*@Bean
    CommandLineRunner init(CityRepository cityRepository, ShopRepository shopRepository) {
        return (evt) -> Arrays.asList(
                "Saint-Petersburg,Moscow,Novosibirsk".split(","))
                .forEach(cityName -> {
                            final City city = cityRepository.save(new City(cityName));

                            shopRepository.save(new Shop("Ashan", city));
                            shopRepository.save(new Shop("Prisma", city));
                        }
                );
    }*/
}
