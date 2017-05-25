package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.myocr.entity.Cities.Nsk;
import static com.myocr.entity.Cities.Spb;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CityControllerTest extends AbstractSpringTest {

    @Override
    public void setUp() throws Exception {
        generateCity(Spb);
        generateCity(Nsk);
    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get("/cities/all/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(Spb.name(), Nsk.name())));
    }
}
