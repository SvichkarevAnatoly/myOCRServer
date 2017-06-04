package com.myocr.controller;

import com.myocr.AbstractSpringTest;
import com.myocr.controller.json.SavePriceRequest;
import com.myocr.entity.City;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.util.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.myocr.entity.Cities.Spb;
import static com.myocr.entity.ReceiptItems.Lemon;
import static com.myocr.entity.ReceiptItems.Pizza;
import static com.myocr.entity.Shops.Auchan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class PriceControllerTest extends AbstractSpringTest {
    private final static Logger log = LoggerFactory.getLogger(PriceControllerTest.class);

    private CityShopReceiptItem pizza;
    private CityShopReceiptItem lemon;

    @Before
    public void setup() throws Exception {
        pizza = generateReceiptItem(Pizza, Spb, Auchan);
        lemon = generateReceiptItem(Lemon, Spb, Auchan);
    }

    @Test
    public void save() throws Exception {
        final Date now = Calendar.getInstance().getTime();
        final Price pizzaPrice = new Price(pizza, 1500, now);
        final Price lemonPrice = new Price(lemon, 1000, now);

        final List<SavePriceRequest.ReceiptPriceItem> items = new ArrayList<>();
        final SavePriceRequest.ReceiptPriceItem pizzaItem = new SavePriceRequest.ReceiptPriceItem(Pizza.name(), pizzaPrice.getValue());
        items.add(pizzaItem);
        final SavePriceRequest.ReceiptPriceItem lemonItem = new SavePriceRequest.ReceiptPriceItem(Lemon.name(), lemonPrice.getValue());
        items.add(lemonItem);

        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final String savedPriceJson = json(
                new SavePriceRequest(spb.getId(), auchan.getId(), items));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(2)));
    }

    @Test
    public void saveNotExistingReceiptItem() throws Exception {
        final List<SavePriceRequest.ReceiptPriceItem> items = Collections.singletonList(
                new SavePriceRequest.ReceiptPriceItem("chicken", 3000));

        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final String savedPriceJson = json(
                new SavePriceRequest(spb.getId(), auchan.getId(), items));
        log.info(savedPriceJson);

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(1)));

        assertThat(receiptItemRepository.count(), is(3L));
        assertThat(cityShopReceiptItemRepository.count(), is(3L));
    }

    @Test
    public void saveReceiptItemWithManySpaces() throws Exception {
        final List<SavePriceRequest.ReceiptPriceItem> items = Collections.singletonList(
                new SavePriceRequest.ReceiptPriceItem("  hot \n \t chicken \t\n ", 3000));

        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final String savedPriceJson = json(
                new SavePriceRequest(spb.getId(), auchan.getId(), items));
        log.info(savedPriceJson);

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(0)));

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));

        final String expectedReceiptItem = "hot chicken";
        assertNull(receiptItemRepository.findByName(expectedReceiptItem));
    }

    @Test
    public void trySaveToLongString() throws Exception {
        final String tooLongReceiptItem = RandomStringUtils.randomAlphabetic(200);
        final List<SavePriceRequest.ReceiptPriceItem> items = Collections.singletonList(
                new SavePriceRequest.ReceiptPriceItem(tooLongReceiptItem, 3000));

        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final String savedPriceJson = json(
                new SavePriceRequest(spb.getId(), auchan.getId(), items));
        log.info(savedPriceJson);

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(0)));

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));
    }

    @Test
    public void trySaveSeveralButOneNotValid() throws Exception {
        final String notValidReceiptItem = "\t";
        final String validReceiptItem = "hot chicken";

        final List<SavePriceRequest.ReceiptPriceItem> items = new ArrayList<>();
        items.add(new SavePriceRequest.ReceiptPriceItem(notValidReceiptItem, 3000));
        items.add(new SavePriceRequest.ReceiptPriceItem(validReceiptItem, 200));

        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final String savedPriceJson = json(
                new SavePriceRequest(spb.getId(), auchan.getId(), items));
        log.info(savedPriceJson);

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(1)));

        assertThat(receiptItemRepository.count(), is(3L));
        assertThat(cityShopReceiptItemRepository.count(), is(3L));

        final ReceiptItem validItem = receiptItemRepository.findByName(validReceiptItem);
        assertNotNull(validItem);
        log.info(validItem.toString());
        assertEquals(validReceiptItem, validItem.getName());
        assertNotNull(validItem.getId());
    }

    @Test
    public void saveWithDate() throws Exception {
        final String timeString = "31-08-1982 10:20:56";

        final Date time = TimeUtil.parse(timeString);
        final Price pizzaPrice = new Price(pizza, 1500, time);

        final List<SavePriceRequest.ReceiptPriceItem> items = new ArrayList<>();
        final SavePriceRequest.ReceiptPriceItem pizzaItem = new SavePriceRequest.ReceiptPriceItem(
                Pizza.name(), pizzaPrice.getValue());
        items.add(pizzaItem);

        final City spb = cityRepository.findByName(Spb.name());
        final Shop auchan = shopRepository.findByName(Auchan.name());
        final String savedPriceJson = json(
                new SavePriceRequest(spb.getId(), auchan.getId(), timeString, items));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", is(1)));
    }
}
