package db;


import model.text.OcrParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class DbStub {
    private static final String DB_FILE_NAME = "/south.txt";

    public List<String> getAllProducts() {
        String dbProductsText = "";
        try {
            InputStream inputStream = getClass().getResourceAsStream(DB_FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            dbProductsText = readText(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final OcrParser parser = new OcrParser(dbProductsText);
        return parser.parseProductList();
    }

    private String readText(BufferedReader reader) throws IOException {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        reader.close();
        return stringBuilder.toString();
    }
}
