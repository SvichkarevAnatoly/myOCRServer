package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import com.myocr.entity.City;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;

import static com.myocr.entity.Cities.Nsk;
import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.ReceiptItems.Cheese;
import static com.myocr.entity.ReceiptItems.Chicken;
import static com.myocr.entity.ReceiptItems.Milk;
import static com.myocr.entity.ReceiptItems.Pizza;
import static com.myocr.entity.Shops.Auchan;
import static com.myocr.entity.Shops.Karusel;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class FinderControllerTest extends AbstractSpringTest {

    @Override
    public void setUp() throws Exception {
        final CityShopReceiptItem chicken = generateReceiptItem(Chicken, Spb, Auchan);
        final CityShopReceiptItem milk = generateReceiptItem(Milk, Spb, Karusel);
        final CityShopReceiptItem pizza = generateReceiptItem(Pizza, Nsk, Auchan);
        final CityShopReceiptItem cheese = generateReceiptItem(Cheese, Spb, Auchan);

        final Calendar cal = Calendar.getInstance();
        generatePrice(chicken, 100, cal.getTime());
        cal.add(Calendar.DATE, 1);
        generatePrice(milk, 200, cal.getTime());
        cal.add(Calendar.DATE, 1);
        generatePrice(pizza, 300, cal.getTime());
        cal.add(Calendar.DATE, 1);
        generatePrice(cheese, 400, cal.getTime());
    }

    @Test
    public void findReceiptItemsLike() throws Exception {
        mockMvc.perform(get("/find/receiptItems?q=ch"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(Chicken.name(), Cheese.name())));
    }

    @Test
    public void findReceiptItemsLikeInCity() throws Exception {
        final Calendar cal = Calendar.getInstance();
        final Date today = cal.getTime();
        cal.add(Calendar.DATE, 3);
        final Date after3Day = cal.getTime();

        final City spb = cityRepository.findByName(Spb.name());
        mockMvc.perform(get("/find/prices?cityId=" + spb.getId() + "&q=ch"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].item", is(Cheese.name())))
                .andExpect(jsonPath("$[0].price", is(400)))
                .andExpect(jsonPath("$[0].date", is(TimeUtil.parse(after3Day))))

                .andExpect(jsonPath("$[1].item", is(Chicken.name())))
                .andExpect(jsonPath("$[1].price", is(100)))
                .andExpect(jsonPath("$[1].date", is(TimeUtil.parse(today))));
    }

    @Test
    public void findReceiptItemsLikeInCityAndShop() throws Exception {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        final Date nextDay = cal.getTime();

        final City spb = cityRepository.findByName(Spb.name());
        final Shop karusel = shopRepository.findByName(Karusel.name());
        mockMvc.perform(get("/find/prices?cityId=" + spb.getId() + "&shopId=" + karusel.getId() + "&q=mi"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].item", is(Milk.name())))
                .andExpect(jsonPath("$[0].price", is(200)))
                .andExpect(jsonPath("$[0].date", is(TimeUtil.parse(nextDay))));
    }

    @Test
    public void findReceiptItemsInCity() throws Exception {
        final Calendar cal = Calendar.getInstance();
        final Date today = cal.getTime();
        cal.add(Calendar.DATE, 1);
        final Date nextDay = cal.getTime();
        cal.add(Calendar.DATE, 2);
        final Date next3Day = cal.getTime();

        final City spb = cityRepository.findByName(Spb.name());
        mockMvc.perform(get("/find/prices?cityId=" + spb.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].item", is(Cheese.name())))
                .andExpect(jsonPath("$[0].price", is(400)))
                .andExpect(jsonPath("$[0].date", is(TimeUtil.parse(next3Day))))

                .andExpect(jsonPath("$[1].item", is(Milk.name())))
                .andExpect(jsonPath("$[1].price", is(200)))
                .andExpect(jsonPath("$[1].date", is(TimeUtil.parse(nextDay))))

                .andExpect(jsonPath("$[2].item", is(Chicken.name())))
                .andExpect(jsonPath("$[2].price", is(100)))
                .andExpect(jsonPath("$[2].date", is(TimeUtil.parse(today))));
    }

    @Test
    public void findReceiptItemsEmptyCityButNotEmptyShop() throws Exception {
        final Shop karusel = shopRepository.findByName(Karusel.name());
        mockMvc.perform(get("/find/prices?shopId=" + karusel.getId() + "&q=mi"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", empty()));
    }
}