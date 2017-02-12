package com.myocr;

import com.myocr.entity.City;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.ShopRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(CityRepository cityRepository, ShopRepository shopRepository) {
        return (evt) -> Arrays.asList(
                "Saint-Petersburg,Moscow,Novosibirsk".split(","))
                .forEach(cityName -> {
                            final City city = cityRepository.save(new City(cityName));

                            shopRepository.save(new Shop("Ashan", city));
                            shopRepository.save(new Shop("Prizma", city));
                        }
                );
    }
}
