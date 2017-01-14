package db;


import model.text.OcrParser;

import java.io.*;
import java.util.List;

public class DbStub {
    public List<String> getAllProducts() {
        String dbProductsText = "";
        try {
            final String dbFileName = "south.txt";
            final String dbFilePath = getClass().getResource(dbFileName).getFile();
            dbProductsText = readText(dbFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final OcrParser parser = new OcrParser(dbProductsText);
        return parser.parseProductList();
    }

    private String readText(String filePath) throws IOException {
        final InputStream openInputStream = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(openInputStream));

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(ls);
        }
        openInputStream.close();
        reader.close();
        return stringBuilder.toString();
    }
}
