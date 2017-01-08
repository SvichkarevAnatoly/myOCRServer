package db;


import model.OcrParser;

import java.io.*;
import java.util.List;

public class DbStub {
    String path = "/home/anatoly/Documents/self/fan/OCR/myOCRServer/resource/";

    public List<String> getAllProducts() {
        String dbProductsText = "";
        try {
            dbProductsText = readText("south.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        final OcrParser parser = new OcrParser(dbProductsText);
        return parser.parseProductList();
    }

    private String readText(String fileName) throws IOException {
        final InputStream openInputStream = new FileInputStream(path + fileName);
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
