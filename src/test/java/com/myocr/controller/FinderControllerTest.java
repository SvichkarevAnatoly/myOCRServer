package com.myocr.controller;

import com.myocr.Application;
import com.myocr.RepositoryUtil;
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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class FinderControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private CityShopRepository cityShopRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

    @Autowired
    private CityShopReceiptItemRepository cityShopReceiptItemRepository;

    @Autowired
    private PriceRepository priceRepository;

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