package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static com.myocr.entity.Cities.Nsk;
import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.Shops.Auchan;
import static com.myocr.entity.Shops.Karusel;
import static com.myocr.entity.Shops.Megas;
import static com.myocr.entity.Shops.Prisma;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
        final Collection<Shop> beforeShopsInSpb = shopRepository.findByCityShopsCityName(Spb.name());

        mockMvc.perform(post("/shops/add/" + Spb + "/Dixy"))
                .andDo(print())
                .andExpect(status().isOk());

        final Collection<Shop> afterShopsInSpb = shopRepository.findByCityShopsCityName(Spb.name());
        assertThat(afterShopsInSpb.size() - beforeShopsInSpb.size(), is(1));
    }
}
