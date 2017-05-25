package com.myocr.controller;

import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.util.TimeUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class FinderControllerTest extends AbstractControllerTest {

    @Override
    public void setUp() throws Exception {
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

        final List<String> itemNames = Arrays.asList("item1", "item2", "item3", "i34534");
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

        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < csItems.size(); i++) {
            final CityShopReceiptItem csItem = csItems.get(i);
            priceRepository.save(new Price(100 * (i + 1), cal.getTime(), csItem));
            cal.add(Calendar.DATE, 1);
        }
    }

    @Test
    public void findReceiptItemsLike() throws Exception {
        mockMvc.perform(get("/find/receiptItems?q=ite"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$", containsInAnyOrder("item1", "item2", "item3")));
    }

    @Test
    public void findReceiptItemsLikeInCity() throws Exception {
        final Calendar cal = Calendar.getInstance();
        final Date today = cal.getTime();
        cal.add(Calendar.DATE, 1);
        final Date nextDay = cal.getTime();

        mockMvc.perform(get("/find/prices?city=Spb&q=ite"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].item", is("item2")))
                .andExpect(jsonPath("$[0].price", is(200)))
                .andExpect(jsonPath("$[0].date", is(TimeUtil.parse(nextDay))))

                .andExpect(jsonPath("$[1].item", is("item1")))
                .andExpect(jsonPath("$[1].price", is(100)))
                .andExpect(jsonPath("$[1].date", is(TimeUtil.parse(today))));
    }

    @Test
    public void findReceiptItemsLikeInCityAndShop() throws Exception {
        final Calendar cal = Calendar.getInstance();
        final Date today = cal.getTime();

        mockMvc.perform(get("/find/prices?city=Spb&shop=Auchan&q=ite"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].item", is("item1")))
                .andExpect(jsonPath("$[0].price", is(100)))
                .andExpect(jsonPath("$[0].date", is(TimeUtil.parse(today))));
    }

    @Test
    public void findReceiptItemsInCity() throws Exception {
        final Calendar cal = Calendar.getInstance();
        final Date today = cal.getTime();
        cal.add(Calendar.DATE, 1);
        final Date nextDay = cal.getTime();
        cal.add(Calendar.DATE, 2);
        final Date next3Day = cal.getTime();

        mockMvc.perform(get("/find/prices?city=Spb"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].item", is("i34534")))
                .andExpect(jsonPath("$[0].price", is(400)))
                .andExpect(jsonPath("$[0].date", is(TimeUtil.parse(next3Day))))

                .andExpect(jsonPath("$[1].item", is("item2")))
                .andExpect(jsonPath("$[1].price", is(200)))
                .andExpect(jsonPath("$[1].date", is(TimeUtil.parse(nextDay))))

                .andExpect(jsonPath("$[2].item", is("item1")))
                .andExpect(jsonPath("$[2].price", is(100)))
                .andExpect(jsonPath("$[2].date", is(TimeUtil.parse(today))));
    }
}