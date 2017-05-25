package com.myocr.repository;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.myocr.entity.Cities.Nsk;
import static com.myocr.entity.Cities.Spb;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class CityRepositoryTest extends AbstractSpringTest {

    @Override
    public void setUp() throws Exception {
        generateCity(Spb);
        generateCity(Nsk);
    }

    @Test
    public void save() throws Exception {
        final Iterable<City> repositoryIterableCity = cityRepository.findAll();
        final List<String> repositoryCityNames = new ArrayList<>();

        for (City city : repositoryIterableCity) {
            repositoryCityNames.add(city.getName());
        }

        assertThat(repositoryCityNames, containsInAnyOrder(Spb.name(), Nsk.name()));
    }

    @Test
    public void findByName() throws Exception {
        final City spb = cityRepository.findByName(Spb.name());

        assertThat(spb, is(notNullValue()));
        assertThat(spb.getName(), is(Spb.name()));
    }
}
