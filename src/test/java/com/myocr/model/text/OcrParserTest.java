package com.myocr.model.text;

import com.myocr.model.ocr.OcrParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OcrParserTest {

    private final String productsText = "product1\nproduct2";
    private final String pricesText = "price1\nprice2";

    private OcrParser parser;

    @BeforeEach
    void setUp() {
        parser = new OcrParser(productsText, pricesText);
    }

    @Test
    void parseProductList() {
        final List<String> products = parser.parseProductList();

        final List<String> expectedProducts = Arrays.asList("product1", "product2");
        assertEquals(expectedProducts, products);
    }

    @Test
    void parsePriceList() {
        final List<String> prices = parser.parsePriceList();

        final List<String> expectedPrices = Arrays.asList("price1", "price2");
        assertEquals(expectedPrices, prices);
    }
}