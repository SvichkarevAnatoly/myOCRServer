package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import com.myocr.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static com.myocr.entity.Cities.Nsk;
import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.Cities.Tomsk;
import static com.myocr.entity.Shops.Auchan;
import static com.myocr.entity.Shops.Karusel;
import static com.myocr.entity.Shops.Megas;
import static com.myocr.entity.Shops.Prisma;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ShopControllerTest extends AbstractSpringTest {

    @Override
    public void setUp() throws Exception {
        generateShop(Spb, Auchan);
        generateShop(Spb, Prisma);
        generateShop(Spb, Karusel);
        generateShop(Nsk, Auchan);
        generateShop(Nsk, Megas);

        generateCity(Tomsk);
    }

    @Test
    public void findShops() throws Exception {
        final City spb = cityRepository.findByName(Spb.name());
        mockMvc.perform(get("/shops/inCity/" + spb.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", greaterThan(0)))
                .andExpect(jsonPath("$[0].name", is(Auchan.name())))
                .andExpect(jsonPath("$[1].id", greaterThan(0)))
                .andExpect(jsonPath("$[1].name", is(Prisma.name())))
                .andExpect(jsonPath("$[2].id", greaterThan(0)))
                .andExpect(jsonPath("$[2].name", is(Karusel.name())));
    }

    @Test
    public void addNewShop() throws Exception {
        final City tomsk = cityRepository.findByName(Tomsk.name());
        mockMvc.perform(put("/shops/add/" + tomsk.getId()).content(Megas.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(true)));

        assertNotNull(cityShopRepository.findByCityNameAndShopName(Tomsk.name(), Megas.name()));
    }

    @Test
    public void addExistedShop() throws Exception {
        final City spb = cityRepository.findByName(Spb.name());
        mockMvc.perform(put("/shops/add/" + spb.getId()).content(Auchan.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(false)));

        assertNotNull(cityShopRepository.findByCityNameAndShopName(Spb.name(), Auchan.name()));
        final Collection<Shop> afterShopsInSpb = shopRepository.findByCityShopsCityId(spb.getId());
        assertEquals(afterShopsInSpb.size(), 3);
    }

    @Test
    public void addEndSpacedShop() throws Exception {
        final City tomsk = cityRepository.findByName(Tomsk.name());
        mockMvc.perform(put("/shops/add/" + tomsk.getId()).content(Karusel.name() + " "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(true)));

        assertNotNull(cityShopRepository.findByCityNameAndShopName(Tomsk.name(), Karusel.name()));
        assertNull(cityShopRepository.findByCityNameAndShopName(Tomsk.name(), Karusel.name() + " "));

        final Collection<Shop> afterShops = shopRepository.findByCityShopsCityId(tomsk.getId());
        assertEquals(afterShops.size(), 1);
    }
}
