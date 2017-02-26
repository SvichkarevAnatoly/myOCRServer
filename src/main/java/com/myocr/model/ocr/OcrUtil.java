package com.myocr.model.ocr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OcrUtil {
    public static List<String> parse(String ocrText) {
        final List<String> ocrLines = splitByNewLine(ocrText);
        deleteEmptyStrings(ocrLines);
        return ocrLines;
    }

    private static List<String> splitByNewLine(String text) {
        return new ArrayList<>(Arrays.asList(text.split("\\n")));
    }

    private static void deleteEmptyStrings(List<String> strings) {
        strings.removeIf(next -> next.trim().isEmpty());
    }
}
