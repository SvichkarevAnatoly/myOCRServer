package com.myocr.model.ocr;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class PriceParserTest {
    @Test
    public void parse15dot00() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("15.00");
        assertThat(parsedPrice.getStringValue(), is("15.00"));
        assertThat(parsedPrice.getIntValue(), is(1500));
    }

    @Test
    public void parse15space00() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("15. 00");
        assertThat(parsedPrice.getStringValue(), is("15. 00"));
        assertThat(parsedPrice.getIntValue(), is(1500));
    }

    @Test
    public void parse1G00() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("1G00");
        assertThat(parsedPrice.getStringValue(), is("1G00"));
        assertThat(parsedPrice.getIntValue(), is(nullValue()));
    }

    @Test
    public void parse150dot0() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("150.0");
        assertThat(parsedPrice.getIntValue(), is(nullValue()));
    }

    @Test
    public void parse15dot0dot0() throws Exception {
        final ParsedPrice parsedPrice = PriceParser.parse("15.0.0");
        assertThat(parsedPrice.getIntValue(), is(nullValue()));
    }
}