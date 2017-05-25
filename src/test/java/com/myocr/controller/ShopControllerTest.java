package com.myocr.controller;

import com.myocr.entity.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

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
public class ShopControllerTest extends AbstractControllerTest {
    private static final String SPB = "Spb";
    private static final String NSK = "Nsk";

    private static final String AUCHAN = "Auchan";
    private static final String PRISMA = "Prisma";
    private static final String KARUSEL = "Karusel";
    private static final String MEGAS = "Megas";

    @Override
    public void setUp() throws Exception {
        generate(SPB, AUCHAN);
        generate(SPB, PRISMA);
        generate(SPB, KARUSEL);
        generate(NSK, AUCHAN);
        generate(NSK, MEGAS);
    }

    @Test
    public void findShops() throws Exception {
        mockMvc.perform(get("/shops/inCity/" + SPB))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$", containsInAnyOrder(AUCHAN, KARUSEL, PRISMA)));
    }

    @Test
    public void addNewShop() throws Exception {
        final Collection<Shop> beforeShopsInSpb = shopRepository.findByCityShopsCityName(SPB);

        mockMvc.perform(post("/shops/add/Spb/Dixy"))
                .andDo(print())
                .andExpect(status().isOk());

        final Collection<Shop> afterShopsInSpb = shopRepository.findByCityShopsCityName(SPB);
        assertThat(afterShopsInSpb.size() - beforeShopsInSpb.size(), is(1));
    }
}
