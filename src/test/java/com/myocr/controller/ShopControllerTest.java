package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Shop;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShopControllerTest extends AbstractControllerTest {
    private List<String> cityNames = Arrays.asList("Spb", "Nsk");
    private List<String> shopNames = Arrays.asList("Auchan", "Prisma", "Karusel", "Megas");

    @Override
    public void setUp() throws Exception {
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
                .andExpect(jsonPath("$[0]", is(shops.get(0).getName())))
                .andExpect(jsonPath("$[1]", is(shops.get(1).getName())))
                .andExpect(jsonPath("$[2]", is(shops.get(2).getName())));
    }

    @Test
    public void addNewShop() throws Exception {
        final Collection<Shop> beforeShopsInSpb = shopRepository.findByCityShopsCityName("Spb");

        mockMvc.perform(post("/shops/add/Spb/Dixy"))
                .andDo(print())
                .andExpect(status().isOk());

        final Collection<Shop> afterShopsInSpb = shopRepository.findByCityShopsCityName("Spb");
        assertThat(afterShopsInSpb.size() - beforeShopsInSpb.size(), is(1));
    }
}
