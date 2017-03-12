package com.myocr.controller;

import com.google.gson.Gson;
import com.myocr.Application;
import com.myocr.controller.json.OcrReceiptResponse;
import com.myocr.model.align.Aligner;
import com.myocr.model.align.ReceiptItemMatches;
import com.myocr.model.align.SimpleAligner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class OcrControllerTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

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
    }

    @Test
    public void ocrImage() throws Exception {
        final ClassLoader classLoader = getClass().getClassLoader();

        final InputStream receiptItemsImageContent = classLoader.getResourceAsStream("images/products.jpg");
        final InputStream pricesImageContent = classLoader.getResourceAsStream("images/prices.jpg");

        final MockMultipartFile receiptItemsImage = new MockMultipartFile(
                "receiptItemsImage", "products.jpg", null, receiptItemsImageContent);
        final MockMultipartFile pricesImage = new MockMultipartFile(
                "pricesImage", "prices.jpg", null, pricesImageContent);

        final MvcResult result = mockMvc
                .perform(fileUpload("/ocr/Sbp/Auchan")
                        .file(receiptItemsImage)
                        .file(pricesImage))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$.itemMatches", hasSize(4)))
                .andExpect(jsonPath("$.itemMatches[0].matches", empty()))

                .andExpect(jsonPath("$.prices", hasSize(4)))
                .andExpect(jsonPath("$.prices[0].intValue", is(18714)))
                .andReturn();

        final OcrReceiptResponse response = new Gson()
                .fromJson(result.getResponse().getContentAsString(), OcrReceiptResponse.class);

        assertOcrReceiptResponse(response);
    }

    private void assertOcrReceiptResponse(OcrReceiptResponse response) {
        final String expectedItemSource = "ФИЛЕ Б/КОЖИ КУРИНОЕ ОХЛ. НА ПО187. 14*1 187. 14";

        final List<ReceiptItemMatches> itemMatchesList = response.getItemMatches();
        final ReceiptItemMatches itemMatches = itemMatchesList.get(0);
        final String itemSource = itemMatches.getSource();

        final Aligner aligner = new SimpleAligner();
        final int score = aligner.align(itemSource, expectedItemSource);

        final int expectedGreaterThanScore = expectedItemSource.length() / 3;
        assertThat(score, greaterThan(expectedGreaterThanScore));
    }
}
