package com.myocr;

import com.myocr.entity.Cities;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.ReceiptItems;
import com.myocr.entity.Shop;
import com.myocr.entity.Shops;
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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class AbstractSpringTest {
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

    protected City generateCity(Cities city) {
        return generateCity(city.name());
    }

    private City generateCity(String city) {
        final City savedCity = cityRepository.findByName(city);
        if (savedCity != null) {
            return savedCity;
        }
        return cityRepository.save(new City(city));
    }

    protected CityShop generateShop(Cities city, Shops shop) {
        return generateShop(city.name(), shop.name());
    }

    private CityShop generateShop(String city, String shop) {
        CityShop savedCityShop = cityShopRepository.findByCityNameAndShopName(city, shop);
        if (savedCityShop != null) {
            return savedCityShop;
        }

        City savedCity = generateCity(city);

        Shop savedShop = shopRepository.findByName(shop);
        if (savedShop == null) {
            savedShop = shopRepository.save(new Shop(shop));
        }

        savedCityShop = new CityShop(savedCity, savedShop);
        return cityShopRepository.save(savedCityShop);
    }

    protected CityShopReceiptItem generateReceiptItem(ReceiptItems item, Cities city, Shops shop) {
        return generateReceiptItem(item.name(), city.name(), shop.name());
    }

    private CityShopReceiptItem generateReceiptItem(String item, String city, String shop) {
        CityShopReceiptItem savedCityShopItem = cityShopReceiptItemRepository
                .findByReceiptItemNameAndCityShopCityNameAndCityShopShopName(item, city, shop);
        if (savedCityShopItem != null) {
            return savedCityShopItem;
        }

        final CityShop savedCityShop = generateShop(city, shop);
        ReceiptItem savedItem = receiptItemRepository.findByName(item);
        if (savedItem == null) {
            savedItem = receiptItemRepository.save(new ReceiptItem(item));
        }

        return cityShopReceiptItemRepository.save(new CityShopReceiptItem(savedItem, savedCityShop));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
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

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                mappingJackson2HttpMessageConverter);
    }

    @SafeVarargs
    private static void deleteAllRepositoryData(CrudRepository<?, Long>... repositories) {
        Arrays.asList(repositories).forEach(CrudRepository::deleteAll);
    }
}
