package com.myocr.controller;

import com.myocr.Application;
import com.myocr.controller.json.ReceiptRequest;
import com.myocr.entity.City;
import com.myocr.entity.CityShop;
import com.myocr.entity.CityShopReceiptItem;
import com.myocr.entity.ReceiptItem;
import com.myocr.entity.Shop;
import com.myocr.repository.CityRepository;
import com.myocr.repository.CityShopReceiptItemRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
    private CityShopReceiptItemRepository cityShopReceiptItemRepository;

    @Autowired
    private ReceiptItemRepository receiptItemRepository;

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

        City spb = cityRepository.save(new City("Spb"));
        Shop auchan = new Shop("Auchan");
        final CityShop spbAuchan = CityShop.link(spb, auchan);
        shopRepository.save(auchan);

        final List<String> itemNames = Arrays.asList("item1", "item2", "item3", "i34534");
        final List<ReceiptItem> items = itemNames.stream().map(ReceiptItem::new).collect(Collectors.toList());
        final Iterable<ReceiptItem> savedItems = receiptItemRepository.save(items);

        for (ReceiptItem savedItem : savedItems) {
            cityShopReceiptItemRepository.save(new CityShopReceiptItem(savedItem, spbAuchan));
        }
    }

    @Test
    public void findReceipt() throws Exception {
        final List<String> items = Arrays.asList("item1", "item2");
        final ReceiptRequest receiptRequest = new ReceiptRequest("Spb", "Auchan", items);
        final String jsonRequestReceipt = json(receiptRequest);

        mockMvc.perform(post("/find/receipt/")
                .contentType(contentType).content(jsonRequestReceipt))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].source", is("item1")))
                .andExpect(jsonPath("$[0].matches", hasSize(3)))
                .andExpect(jsonPath("$[0].matches[0].match", is("item1")))
                .andExpect(jsonPath("$[0].matches[0].score", is(5)))
                .andExpect(jsonPath("$[0].matches[1].score", is(3)))
                .andExpect(jsonPath("$[0].matches[2].score", is(3)))

                .andExpect(jsonPath("$[1].source", is("item2")))
                .andExpect(jsonPath("$[1].matches", hasSize(3)))
                .andExpect(jsonPath("$[1].matches[0].match", is("item2")))
                .andExpect(jsonPath("$[1].matches[0].score", is(5)))
                .andExpect(jsonPath("$[0].matches[1].score", is(3)))
                .andExpect(jsonPath("$[0].matches[2].score", is(3)));
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
