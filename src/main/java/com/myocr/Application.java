package com.myocr;

import com.myocr.entity.City;
import com.myocr.repository.CityRepository;
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
    CommandLineRunner init(CityRepository cityRepository) {
        return (evt) -> Arrays.asList(
                "Saint-Petersburg,Moscow,Novosibirsk".split(","))
                .forEach(name -> cityRepository.save(new City(name)));
    }
}
