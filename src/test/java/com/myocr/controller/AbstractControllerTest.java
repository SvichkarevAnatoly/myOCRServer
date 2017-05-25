package com.myocr.controller;

import com.myocr.Application;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopReceiptItemRepository;
import com.myocr.repository.CityShopRepository;
import com.myocr.repository.PriceRepository;
import com.myocr.repository.ReceiptItemRepository;
import com.myocr.repository.ShopRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class AbstractControllerTest {
    protected MockMvc mockMvc;

    @Autowired
    protected CityRepository cityRepository;

    @Autowired
    protected ShopRepository shopRepository;

    @Autowired
    protected CityShopRepository cityShopRepository;

    @Autowired
    protected ReceiptItemRepository receiptItemRepository;

    @Autowired
    protected CityShopReceiptItemRepository cityShopReceiptItemRepository;

    @Autowired
    protected PriceRepository priceRepository;

    @Before
    public void abstractSetUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();

        setUp();
    }

    @After
    public void abstractTearDown() throws Exception {
        tearDown();

        deleteAllRepositoryData(
                priceRepository,
                cityShopReceiptItemRepository,
                receiptItemRepository,
                cityShopRepository,
                cityRepository,
                shopRepository);
    }

    protected CityShop generate(String city, String shop) {
        City savedCity = cityRepository.findByName(city);
        if (savedCity == null) {
            savedCity = cityRepository.save(new City(city));
        }

        Shop savedShop = shopRepository.findByName(shop);
        if (savedShop == null) {
            savedShop = shopRepository.save(new Shop(shop));
        }

        final CityShop savedCityShop = new CityShop(savedCity, savedShop);
        return cityShopRepository.save(savedCityShop);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @SafeVarargs
    private static void deleteAllRepositoryData(CrudRepository<?, Long>... repositories) {
        Arrays.asList(repositories).forEach(CrudRepository::deleteAll);
    }
}
