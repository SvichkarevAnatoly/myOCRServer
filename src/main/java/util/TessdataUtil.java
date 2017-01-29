package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class TessdataUtil {
    private static final String TESSFOLDER = "tessdata";
    private static final String TESSDATA_EXTENTION = ".traineddata";
    private static final String JAR_ROOT = "/";

    private static final List<String> langs = Arrays.asList("eng", "rus");

    public static void extractTessdata() {
        for (String lang : langs) {
            final String fileName = lang + TESSDATA_EXTENTION;
            final String filePath = TESSFOLDER + File.separator + fileName;
            try {
                extractFile(JAR_ROOT + filePath, filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void extractFile(String resoursePath, String outPath) throws IOException {
        File file = new File(outPath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            InputStream inputStream = file.getClass().getResourceAsStream(resoursePath);
            Files.copy(inputStream, file.getAbsoluteFile().toPath());
        }
    }
}
