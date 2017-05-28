package com.myocr.controller;

import com.myocr.AbstractSpringTest;
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
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(get("/shops/inCity/" + Spb))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$", containsInAnyOrder(Auchan.name(), Karusel.name(), Prisma.name())));
    }

    @Test
    public void addNewShop() throws Exception {
        mockMvc.perform(post("/shops/add/" + Tomsk + "/" + Megas))
                .andDo(print())
                .andExpect(status().isOk());

        assertNotNull(cityShopRepository.findByCityNameAndShopName(Tomsk.name(), Megas.name()));

        final Collection<Shop> shopsInTomsk = shopRepository.findByCityShopsCityName(Tomsk.name());
        assertEquals(shopsInTomsk.size(), 1);
    }

    @Test
    public void addExistedShop() throws Exception {
        mockMvc.perform(post("/shops/add/" + Spb + "/" + Auchan))
                .andDo(print())
                .andExpect(status().isOk());

        final Collection<Shop> afterShopsInSpb = shopRepository.findByCityShopsCityName(Spb.name());
        assertEquals(afterShopsInSpb.size(), 3);
    }

    @Test
    public void addEndSpacedShop() throws Exception {
        mockMvc.perform(post("/shops/add/" + Tomsk + "/" + Karusel + " "))
                .andDo(print())
                .andExpect(status().isOk());

        assertNotNull(cityShopRepository.findByCityNameAndShopName(Tomsk.name(), Karusel.name()));
        assertNull(cityShopRepository.findByCityNameAndShopName(Tomsk.name(), Karusel.name() + " "));

        final Collection<Shop> afterShops = shopRepository.findByCityShopsCityName(Tomsk.name());
        assertEquals(afterShops.size(), 1);
    }
}
