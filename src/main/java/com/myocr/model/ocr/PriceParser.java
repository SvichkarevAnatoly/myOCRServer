package com.myocr.model.ocr;

import java.util.ArrayList;
import java.util.List;

public class PriceParser {
    public static List<ParsedPrice> parse(List<String> ocrPrices) {
        final ArrayList<ParsedPrice> parsedPrices = new ArrayList<>();
        for (String ocrPrice : ocrPrices) {
            parsedPrices.add(parse(ocrPrice));
        }

        return parsedPrices;
    }

    public static ParsedPrice parse(String ocrPrice) {
        // remove spaces
        final String ocrPriceWithoutSpaces = ocrPrice.replaceAll("\\s+", "");

        // try parse with dot
        final ParsedPrice notParsedPrice = new ParsedPrice(ocrPrice);
        try {
            Float.parseFloat(ocrPriceWithoutSpaces);
        } catch (NumberFormatException nfe) {
            return notParsedPrice;
        }

        // check where dot position
        final int lastDot = ocrPriceWithoutSpaces.lastIndexOf('.');
        if (!(lastDot == (ocrPriceWithoutSpaces.length() - 3))) {
            return notParsedPrice;
        }

        // try parse to int without dot
        final String ocrPriceWithoutDots = ocrPriceWithoutSpaces.replace(".", "");
        try {
            final int parsedInt = Integer.parseInt(ocrPriceWithoutDots);
            return new ParsedPrice(ocrPrice, parsedInt);
        } catch (NumberFormatException nfe) {
            return notParsedPrice;
        }
    }
}
