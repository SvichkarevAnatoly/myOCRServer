package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ReceiptItemControllerTest extends AbstractControllerTest {

    @Before
    public void setup() throws Exception {
        final City spb = cityRepository.save(new City("Spb"));
        final City nsk = cityRepository.save(new City("Nsk"));

        final Shop auchan = new Shop("Auchan");
        final CityShop spbAuchan = CityShop.link(spb, auchan);
        shopRepository.save(auchan);

        final CityShop nskAuchan = CityShop.link(nsk, auchan);
        cityShopRepository.save(nskAuchan);

        final Shop karusel = new Shop("Karusel");
        final CityShop spbKarusel = CityShop.link(spb, karusel);
        shopRepository.save(karusel);

        final List<String> itemNames = Arrays.asList("item1", "item2", "item3", "item4");
        final List<ReceiptItem> items = itemNames.stream().map(ReceiptItem::new).collect(Collectors.toList());
        final Iterable<ReceiptItem> savedItems = receiptItemRepository.save(items);

        final List<CityShopReceiptItem> csItems = new ArrayList<>();
        for (ReceiptItem savedItem : savedItems) {
            switch (savedItem.getName()) {
                case "item2":
                    csItems.add(cityShopReceiptItemRepository.save(new CityShopReceiptItem(savedItem, spbKarusel)));
                    break;
                case "item3":
                    csItems.add(cityShopReceiptItemRepository.save(new CityShopReceiptItem(savedItem, nskAuchan)));
                    break;
                default:
                    csItems.add(cityShopReceiptItemRepository.save(new CityShopReceiptItem(savedItem, spbAuchan)));
                    break;
            }
        }
    }

    @Test
    public void findReceiptItemsLike() throws Exception {
        mockMvc.perform(get("/receiptItems?city=Spb&shop=Auchan"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder("item1", "item4")));
    }
}