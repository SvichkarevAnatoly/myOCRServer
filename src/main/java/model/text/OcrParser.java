package model.text;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OcrParser {
    private String productsText;
    private String pricesText;

    public OcrParser(String productsText) {
        this(productsText, "");
    }

    public OcrParser(String productsText, String pricesText) {
        this.productsText = productsText;
        this.pricesText = pricesText;
    }

    public void setPricesText(String pricesText) {
        this.pricesText = pricesText;
    }

    public List<String> parseProductList() {
        final List<String> products = splitByNewLine(productsText);
        deleteEmptyStrings(products);
        return products;
    }

    public List<String> parsePriceList() {
        final List<String> prices = splitByNewLine(pricesText);
        deleteEmptyStrings(prices);
        return prices;
    }

    private List<String> splitByNewLine(String text) {
        return new ArrayList<>(Arrays.asList(text.split("\\n")));
    }

    private void deleteEmptyStrings(List<String> strings) {
        strings.removeIf(next -> next.trim().isEmpty());
    }
}
