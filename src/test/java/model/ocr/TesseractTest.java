package model.ocr;

import org.bytedeco.javacpp.lept;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.bytedeco.javacpp.lept.pixRead;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TesseractTest {

    private final String rusLang = "rus";
    private final String rusEngLang = "rus+eng";

    private final String pathToImages = "/images/";

    private Tesseract tesseract;

    @BeforeEach
    void setUp() {
        tesseract = new Tesseract(rusLang);
    }

    @AfterEach
    void tearDown() {
        tesseract.release();
    }

    @Test
    void ocrProducts() {
        final String expectedOcrText = "№№ Б/К0ЖИ КЧРИН0Е 0ХЛ. НА П0187. 14“ 187. И\n" +
                "№№ Б/К0ЖИ КЧРИНОЕ 0ХЛ. НА П0201. 00*1 201. 00\n" +
                "ИЗД—ИЕ МАКАР0Н. СЕРПАНТИН Г РЧПП1З. 90*1 13. 90\n" +
                "ХЛЕБ_ДАРНИШ<ИИ НАРЕЗКА ЗБ0Г 14. 90*1 14. 90\n" +
                "\n";
        assertImageOcr(pathToImages + "products.jpg", expectedOcrText);
    }

    @Test
    void ocrPrices() {
        tesseract.setCharWhitelist("1234567890.");

        final String expectedOcrText = "187.14\n201. 00\n13. 90\n714. 90\n\n";
        assertImageOcr(pathToImages + "prices.jpg", expectedOcrText);
    }

    @Test
    void ocrCola() {
        tesseract = new Tesseract(rusEngLang);

        final String expectedOcrText = "Газированный\nнапиток\nсосмюих.\nFANTA, sPRlTE,\n00cA-COLA ZERO\n\n";
        assertImageOcr(pathToImages + "cola.jpg", expectedOcrText);
    }

    private void assertImageOcr(String imageFile, String expectedOcrText) {
        final String priceImageFile = getClass().getResource(imageFile).getFile();
        final lept.PIX image = pixRead(priceImageFile);

        final String priceText = tesseract.ocr(image);

        assertEquals(expectedOcrText, priceText);
    }
}