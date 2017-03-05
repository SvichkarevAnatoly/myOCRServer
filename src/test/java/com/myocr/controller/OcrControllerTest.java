package com.myocr.controller;

import com.myocr.Application;
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
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
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

        mockMvc.perform(fileUpload("/ocr/image?city=Sbp&shop=Auchan")
                .file(receiptItemsImage)
                .file(pricesImage))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))

                .andExpect(jsonPath("$.itemMatches", hasSize(4)))
                .andExpect(jsonPath("$.itemMatches[0].source",
                        is("№№ Б/К0ЖИ КЧРИН0Е 0ХЛ. НА П0187. 14“ 187. И")))
                .andExpect(jsonPath("$.itemMatches[1].source",
                        is("№№ Б/К0ЖИ КЧРИНОЕ 0ХЛ. НА П0201. 00*1 201. 00")))
                .andExpect(jsonPath("$.itemMatches[2].source",
                        is("ИЗД—ИЕ МАКАР0Н. СЕРПАНТИН Г РЧПП1З. 90*1 13. 90")))
                .andExpect(jsonPath("$.itemMatches[3].source",
                        is("ХЛЕБ_ДАРНИШ<ИИ НАРЕЗКА ЗБ0Г 14. 90*1 14. 90")))

                .andExpect(jsonPath("$.prices", hasSize(4)))
                .andExpect(jsonPath("$.prices[0]", is("187.14")))
                .andExpect(jsonPath("$.prices[1]", is("201. 00")))
                .andExpect(jsonPath("$.prices[2]", is("13. 90")))
                .andExpect(jsonPath("$.prices[3]", is("714. 90")));
    }
}
