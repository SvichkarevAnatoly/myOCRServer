package com.myocr.controller;

import com.myocr.Application;
import com.myocr.controller.json.RequestPriceBody;
import com.myocr.controller.json.RequestReceiptPriceItem;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Price;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.repository.ShopRepository;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
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
    private ReceiptItemRepository receiptItemRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private City spb;
    private Shop auchan;
    private CityShop spbAuchan;

    private ReceiptItem pizza;
    private ReceiptItem pasta;

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
        spbAuchan = CityShop.link(spb, auchan);
        auchan = shopRepository.save(auchan);

        pizza = receiptItemRepository.save(new ReceiptItem("Pizza"));
        pasta = receiptItemRepository.save(new ReceiptItem("Pasta"));
    }

    @Test
    public void save() throws Exception {
        final Date now = Calendar.getInstance().getTime();
        final Price pizzaPrice = new Price("15.00", now, pizza, spbAuchan);
        final Price pastaPrice = new Price("10.00", now, pasta, spbAuchan);

        final List<RequestReceiptPriceItem> items = new ArrayList<>();
        final RequestReceiptPriceItem pizzaItem = new RequestReceiptPriceItem(pizza.getName(), pizzaPrice.getValue());
        items.add(pizzaItem);
        final RequestReceiptPriceItem pastaItem = new RequestReceiptPriceItem(pasta.getName(), pastaPrice.getValue());
        items.add(pastaItem);

        final String savedPriceJson = json(
                new RequestPriceBody(spb.getName(), auchan.getName(), items));

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
                .andExpect(jsonPath("$[0].receiptItem.name",
                        is(pizzaPrice.getReceiptItem().getName())))
                .andExpect(jsonPath("$[0].cityShop.city.name",
                        is(pizzaPrice.getCityShop().getCity().getName())))
                .andExpect(jsonPath("$[0].cityShop.shop.name",
                        is(pizzaPrice.getCityShop().getShop().getName())))

                // pasta
                .andExpect(jsonPath("$[1].value", is(pastaPrice.getValue())))
                .andExpect(jsonPath("$[0].time",
                        greaterThan(pastaPrice.getTime().getTime())))
                .andExpect(jsonPath("$[1].receiptItem.name",
                        is(pastaPrice.getReceiptItem().getName())))
                .andExpect(jsonPath("$[1].cityShop.city.name",
                        is(pastaPrice.getCityShop().getCity().getName())))
                .andExpect(jsonPath("$[1].cityShop.shop.name",
                        is(pastaPrice.getCityShop().getShop().getName())));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
