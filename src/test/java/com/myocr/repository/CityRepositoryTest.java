package com.myocr.repository;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class CityRepositoryTest extends AbstractSpringTest {

    private List<String> cityNames = Arrays.asList("Spb", "Nsk");

    @Override
    public void setUp() throws Exception {
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

    @Test
    public void findByName() throws Exception {
        final String cityName = "Spb";
        final City spb = cityRepository.findByName(cityName);

        assertThat(spb, is(notNullValue()));
        assertThat(spb.getName(), is(cityName));
    }
}
