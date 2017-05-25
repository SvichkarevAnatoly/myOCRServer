package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CityControllerTest extends AbstractSpringTest {

    private List<String> cityNames = Arrays.asList("Spb", "Nsk");

    @Override
    public void setUp() throws Exception {
        cityRepository.save(new City(cityNames.get(0)));
        cityRepository.save(new City(cityNames.get(1)));
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("/cities/all/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is(cityNames.get(0))))
                .andExpect(jsonPath("$[1]", is(cityNames.get(1))));
    }
}
