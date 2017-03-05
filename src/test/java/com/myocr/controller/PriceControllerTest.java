package com.myocr.controller;

import com.myocr.Application;
import com.myocr.controller.json.SavePriceRequest;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.repository.ShopRepository;
import com.myocr.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class PriceControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CityShopRepository cityShopRepository;

    @Autowired
    private CityShopReceiptItemRepository cityShopReceiptItemRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private City spb;
    private Shop auchan;

    private ReceiptItem pizza;
    private CityShopReceiptItem spbAuchanPizza;

    private ReceiptItem pasta;
    private CityShopReceiptItem spbAuchanPasta;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        shopRepository.deleteAll();
        cityRepository.deleteAll();
        cityShopRepository.deleteAll();
        receiptItemRepository.deleteAll();

        spb = cityRepository.save(new City("Spb"));
        auchan = new Shop("Auchan");
        final CityShop spbAuchan = CityShop.link(spb, auchan);
        auchan = shopRepository.save(auchan);

        pizza = receiptItemRepository.save(new ReceiptItem("Pizza"));
        spbAuchanPizza = cityShopReceiptItemRepository.save(new CityShopReceiptItem(pizza, spbAuchan));

        pasta = receiptItemRepository.save(new ReceiptItem("Pasta"));
        spbAuchanPasta = cityShopReceiptItemRepository.save(new CityShopReceiptItem(pasta, spbAuchan));
    }

    @Test
    public void save() throws Exception {
        final Date now = Calendar.getInstance().getTime();
        final Price pizzaPrice = new Price("15.00", now, spbAuchanPizza);
        final Price pastaPrice = new Price("10.00", now, spbAuchanPasta);

        final List<SavePriceRequest.ReceiptPriceItem> items = new ArrayList<>();
        final SavePriceRequest.ReceiptPriceItem pizzaItem = new SavePriceRequest.ReceiptPriceItem(pizza.getName(), pizzaPrice.getValue());
        items.add(pizzaItem);
        final SavePriceRequest.ReceiptPriceItem pastaItem = new SavePriceRequest.ReceiptPriceItem(pasta.getName(), pastaPrice.getValue());
        items.add(pastaItem);

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), items));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))

                // pizza
                .andExpect(jsonPath("$[0].value", is(pizzaPrice.getValue())))
                .andExpect(jsonPath("$[0].time",
                        greaterThan(pizzaPrice.getTime().getTime())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.receiptItem.name",
                        is(pizzaPrice.getCityShopReceiptItem().getReceiptItem().getName())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.cityShop.city.name",
                        is(pizzaPrice.getCityShopReceiptItem().getCityShop().getCity().getName())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.cityShop.shop.name",
                        is(pizzaPrice.getCityShopReceiptItem().getCityShop().getShop().getName())))

                // pasta
                .andExpect(jsonPath("$[1].value", is(pastaPrice.getValue())))
                .andExpect(jsonPath("$[1].time",
                        greaterThan(pastaPrice.getTime().getTime())))
                .andExpect(jsonPath("$[1].cityShopReceiptItem.receiptItem.name",
                        is(pastaPrice.getCityShopReceiptItem().getReceiptItem().getName())))
                .andExpect(jsonPath("$[1].cityShopReceiptItem.cityShop.city.name",
                        is(pastaPrice.getCityShopReceiptItem().getCityShop().getCity().getName())))
                .andExpect(jsonPath("$[1].cityShopReceiptItem.cityShop.shop.name",
                        is(pastaPrice.getCityShopReceiptItem().getCityShop().getShop().getName())));
    }

    @Test
    public void saveNotExistingReceiptItem() throws Exception {
        final Date now = new Date();
        final List<SavePriceRequest.ReceiptPriceItem> items = Collections.singletonList(
                new SavePriceRequest.ReceiptPriceItem("chicken", "30.00"));

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), items));
        System.out.println(savedPriceJson);

        assertThat(receiptItemRepository.count(), is(2L));
        assertThat(cityShopReceiptItemRepository.count(), is(2L));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].value", is("30.00")))
                .andExpect(jsonPath("$[0].time", greaterThan(now.getTime())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.receiptItem.name", is("chicken")));

        assertThat(receiptItemRepository.count(), is(3L));
        assertThat(cityShopReceiptItemRepository.count(), is(3L));
    }

    @Test
    public void saveWithDate() throws Exception {
        final String timeString = "31-08-1982 10:20:56";

        final Date time = TimeUtil.parse(timeString);
        final Price pizzaPrice = new Price("15.00", time, spbAuchanPizza);

        final List<SavePriceRequest.ReceiptPriceItem> items = new ArrayList<>();
        final SavePriceRequest.ReceiptPriceItem pizzaItem = new SavePriceRequest.ReceiptPriceItem(
                pizza.getName(), pizzaPrice.getValue());
        items.add(pizzaItem);

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), timeString, items));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(1)))

                // pizza
                .andExpect(jsonPath("$[0].value", is(pizzaPrice.getValue())))
                .andExpect(jsonPath("$[0].time",
                        is(TimeUtil.parse(timeString).getTime())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.receiptItem.name",
                        is(pizzaPrice.getCityShopReceiptItem().getReceiptItem().getName())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.cityShop.city.name",
                        is(pizzaPrice.getCityShopReceiptItem().getCityShop().getCity().getName())))
                .andExpect(jsonPath("$[0].cityShopReceiptItem.cityShop.shop.name",
                        is(pizzaPrice.getCityShopReceiptItem().getCityShop().getShop().getName())));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
