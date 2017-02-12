package com.myocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
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
