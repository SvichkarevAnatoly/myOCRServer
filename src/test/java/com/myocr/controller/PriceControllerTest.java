package com.myocr.controller;

import com.myocr.Application;
import com.myocr.RepositoryUtil;
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
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.repository.ShopRepository;
import com.myocr.util.TimeUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
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
    private final static Logger log = LoggerFactory.getLogger(PriceControllerTest.class);

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
    private PriceRepository priceRepository;

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

    @After
    public void tearDown() throws Exception {
        RepositoryUtil.deleteAll(
                priceRepository,
                cityShopReceiptItemRepository,
                receiptItemRepository,
                cityShopRepository,
                cityRepository,
                shopRepository
        );
    }

    @Test
    public void save() throws Exception {
        final Date now = Calendar.getInstance().getTime();
        final Price pizzaPrice = new Price(1500, now, spbAuchanPizza);
        final Price pastaPrice = new Price(1000, now, spbAuchanPasta);

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
                .andExpect(jsonPath("$", is(2)));
    }

    @Test
    public void saveNotExistingReceiptItem() throws Exception {
        final List<SavePriceRequest.ReceiptPriceItem> items = Collections.singletonList(
                new SavePriceRequest.ReceiptPriceItem("chicken", 3000));

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), items));
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

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), items));
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

        final String expectedReceiptItem = "hot chicken";
        final ReceiptItem hotChicken = receiptItemRepository.findByName(expectedReceiptItem);
        assertNotNull(hotChicken);
        log.info(hotChicken.toString());
        assertEquals(expectedReceiptItem, hotChicken.getName());
        assertNotNull(hotChicken.getId());
    }

    @Test
    public void trySaveToLongString() throws Exception {
        final String tooLongReceiptItem = RandomStringUtils.randomAlphabetic(200);
        final List<SavePriceRequest.ReceiptPriceItem> items = Collections.singletonList(
                new SavePriceRequest.ReceiptPriceItem(tooLongReceiptItem, 3000));

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), items));
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

        final String savedPriceJson = json(
                new SavePriceRequest(spb.getName(), auchan.getName(), items));
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
        final Price pizzaPrice = new Price(1500, time, spbAuchanPizza);

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
                .andExpect(jsonPath("$", is(1)));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
