package com.myocr.model.align;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class SimpleAlignerRealDataTest {
    private final String ocrChicken = "[114ЛЕ Б/К0ЖИ К9РИН0Е 0ХЛ. НА П0187. ММ 187. И";
    final String expectedChickenAlignment = "ФИ--ЛЕ Б/КОЖИ КУРИНОЕ ОХЛ. НА ПО187. 14*1 187. 14";

    @Test
    public void chickenWord() throws Exception {
        final String ocr = "К9РИН0Е";
        final String real = "КУРИНОЕ";
        final SimpleAligner alignment = new SimpleAligner();

        assertThat(alignment.align(ocr, real), is(3));

        System.out.println(alignment.getAlign1());
        System.out.println(alignment.getAlign2());

        alignment.printScoreMatrix();
        alignment.printBacktrack();

        assertThat(alignment.getAlign1(), is(ocr));
        assertThat(alignment.getAlign2(), is(real));
    }

    @Test
    public void chickenFull() throws Exception {
        final String real = "ФИЛЕ Б/КОЖИ КУРИНОЕ ОХЛ. НА ПО187. 14*1 187. 14";
        final SimpleAligner alignment = new SimpleAligner();

        System.out.println("score = " + alignment.align(ocrChicken, real));
        assertThat(alignment.align(ocrChicken, real), is(19));

        // alignment.printScoreMatrix();
        // alignment.printBacktrack();

        System.out.println(alignment.getAlign1());
        System.out.println(alignment.getAlign2());

        final String expectedAlignStr1 = "[114ЛЕ Б/К0ЖИ К9РИН0Е 0ХЛ. НА П0187. ММ-- 187. И-";

        assertThat(alignment.getAlign1(), is(expectedAlignStr1));
        assertThat(alignment.getAlign2(), is(expectedChickenAlignment));
    }

    @Test
    public void findChicken() throws Exception {
        int maxScore = Integer.MIN_VALUE;
        String maxScoreFoodName = "empty";

        final SimpleAligner alignment = new SimpleAligner();
        for (String foodName : TestRealData.food30Names) {
            final int score = alignment.align(ocrChicken, foodName);
            System.out.println("score = " + score);
            System.out.println(alignment.getAlign2());
            System.out.println(alignment.getAlign1());
            System.out.println();

            if (score > maxScore) {
                maxScore = score;
                maxScoreFoodName = foodName;
            }
        }

        System.out.println("--------------------------------------");
        final int score = alignment.align(ocrChicken, maxScoreFoodName);
        System.out.println("score = " + score);
        final String maxScoreAlignment = alignment.getAlign2();
        System.out.println(maxScoreAlignment);
        System.out.println(alignment.getAlign1());
        assertThat(maxScoreAlignment, is(expectedChickenAlignment));
    }
}
