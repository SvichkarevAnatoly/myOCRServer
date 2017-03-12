package com.myocr.controller;

import com.myocr.Application;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.ShopRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class ShopControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private List<String> cityNames = Arrays.asList("Spb", "Nsk");
    private List<String> shopNames = Arrays.asList("Auchan", "Prisma", "Karusel", "Megas");

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        final City spb = cityRepository.save(new City(cityNames.get(0)));
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
    }

    @Test
    public void findShops() throws Exception {
        final Iterable<Shop> responseShops = shopRepository.findAll();
        final List<Shop> shops = new ArrayList<>();
        responseShops.forEach(shops::add); // lambda iterable to list

        mockMvc.perform(get("/shops/inCity/" + cityNames.get(0)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(shops.get(0).getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(shops.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(shops.get(1).getId().intValue())))
                .andExpect(jsonPath("$[1].name", is(shops.get(1).getName())))
                .andExpect(jsonPath("$[2].id", is(shops.get(2).getId().intValue())))
                .andExpect(jsonPath("$[2].name", is(shops.get(2).getName())));
    }

    @Test
    public void addNewShop() throws Exception {
        mockMvc.perform(post("/shops/add/Spb/Dixy"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
