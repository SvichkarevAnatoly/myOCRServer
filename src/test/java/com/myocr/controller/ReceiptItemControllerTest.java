package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import com.myocr.entity.Shop;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.myocr.entity.Cities.Nsk;
import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.ReceiptItems.Chicken;
import static com.myocr.entity.ReceiptItems.Lemon;
import static com.myocr.entity.ReceiptItems.Milk;
import static com.myocr.entity.ReceiptItems.Pizza;
import static com.myocr.entity.Shops.Auchan;
import static com.myocr.entity.Shops.Karusel;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ReceiptItemControllerTest extends AbstractSpringTest {

    @Before
    public void setup() throws Exception {
        generateReceiptItem(Lemon, Spb, Auchan);
        generateReceiptItem(Pizza, Spb, Karusel);
        generateReceiptItem(Milk, Nsk, Auchan);
        generateReceiptItem(Chicken, Spb, Auchan);
    }

    @Test
    public void getNamesInCityShop() throws Exception {
        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        mockMvc.perform(get("/receiptItems/" + spb.getId() + "/" + auchan.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(Lemon.name(), Chicken.name())));
    }
}