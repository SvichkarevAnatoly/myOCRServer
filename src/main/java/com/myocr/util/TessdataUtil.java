package com.myocr.util;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class TessdataUtil {
    private static final String TESSFOLDER = "tessdata";
    private static final String TESSDATA_EXTENTION = ".traineddata";

    private static final List<String> langs = Arrays.asList("eng", "rus");

    public static void extractTessdata() {
        for (String lang : langs) {
            final String fileName = lang + TESSDATA_EXTENTION;
            final String filePath = TESSFOLDER + File.separator + fileName;
            try {
                extractFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void extractFile(String resourcePath) throws IOException {
        File file = new File(resourcePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            InputStream inputStream = new ClassPathResource(resourcePath).getInputStream();
            FileUtils.copyInputStreamToFile(inputStream, file);
        }
    }
}
