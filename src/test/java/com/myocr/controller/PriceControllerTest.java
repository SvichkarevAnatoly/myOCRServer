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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    }

    @Test
    public void save() throws Exception {
        final Price price = new Price("15.00", pizza, spbAuchan);

        final RequestReceiptPriceItem item = new RequestReceiptPriceItem(pizza.getName(), price.getValue());
        final List<RequestReceiptPriceItem> items = new ArrayList<>();
        items.add(item);

        final String savedPriceJson = json(
                new RequestPriceBody(spb.getName(), auchan.getName(), items));

        mockMvc.perform(post("/prices/save/")
                .contentType(contentType).content(savedPriceJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].value", is(price.getValue())))
                .andExpect(jsonPath("$[0].receiptItem.name",
                        is(price.getReceiptItem().getName())))
                .andExpect(jsonPath("$[0].cityShop.city.name",
                        is(price.getCityShop().getCity().getName())))
                .andExpect(jsonPath("$[0].cityShop.shop.name",
                        is(price.getCityShop().getShop().getName())));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
