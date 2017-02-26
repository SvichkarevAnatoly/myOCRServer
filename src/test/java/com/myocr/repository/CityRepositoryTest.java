package com.myocr.repository;

import com.myocr.Application;
import com.myocr.entity.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    private List<String> cityNames = Arrays.asList("Spb", "Nsk");

    @Before
    public void setup() throws Exception {
        // cityRepository.deleteAll();

        final List<City> cities = IntStream.range(0, cityNames.size())
                .mapToObj(i -> new City(cityNames.get(i))).collect(toList());
        cityRepository.save(cities);
    }

    @Test
    public void save() throws Exception {
        final Iterable<City> repositoryIterableCity = cityRepository.findAll();
        final List<String> repositoryCityNames = new ArrayList<>();

        for (City city : repositoryIterableCity) {
            repositoryCityNames.add(city.getName());
        }

        assertThat(repositoryCityNames, containsInAnyOrder(cityNames.toArray()));
    }
}
