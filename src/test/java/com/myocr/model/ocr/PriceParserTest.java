package com.myocr.model.ocr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PriceParserTest {
    @Test
    public void parse15dot00() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("15.00");
        assertEquals(parsedPrice.getStringValue(), "15.00");
        assertTrue(parsedPrice.isParsed());
        assertEquals(parsedPrice.getIntValue(), 1500);
    }

    @Test
    public void parse15space00() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("15. 00");
        assertEquals(parsedPrice.getStringValue(), "15. 00");
        assertTrue(parsedPrice.isParsed());
        assertEquals(parsedPrice.getIntValue(), 1500);
    }

    @Test
    public void parse1G00() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("1G00");
        assertEquals(parsedPrice.getStringValue(), "1G00");
        assertFalse(parsedPrice.isParsed());
        assertEquals(parsedPrice.getIntValue(), 0);
    }

    @Test
    public void parse150dot0() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("150.0");
        assertFalse(parsedPrice.isParsed());
    }

    @Test
    public void parse15dot0dot0() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("15.0.0");
        assertFalse(parsedPrice.isParsed());
    }
}