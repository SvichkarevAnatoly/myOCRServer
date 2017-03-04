package com.myocr.model.align;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

public class DataBaseFinderTest {
    @Test
    public void find() throws Exception {
        final List<String> receiptItems = Arrays.asList("aaa", "bbb", "ccc", "aab");
        final DataBaseFinder finder = new DataBaseFinder(receiptItems);
        final List<Match> matches = finder.find("aaa");

        assertThat(matches, hasSize(3));
        final Match bestMatch = matches.get(0);
        assertReflectionEquals(new Match("aaa", 3), bestMatch);
        assertReflectionEquals(new Match("aab", 1), matches.get(1));
        assertReflectionEquals(new Match("ccc", -3), matches.get(2));
    }

    @Test
    public void findGeneratedData() throws Exception {
        //final String expectedReceiptItem = "expected";
        final String expectedReceiptItem = randomAlphabetic(10).toLowerCase();

        final StringBuilder sb = new StringBuilder(expectedReceiptItem);
        sb.setCharAt(0, '-');
        final String expectedReceiptItem2 = sb.toString();

        final List<String> receiptItems = new ArrayList<>(500);
        for (int i = 1; i < 50000; i++) {
            receiptItems.add(randomAlphabetic(expectedReceiptItem.length()).toLowerCase());
        }
        receiptItems.add(expectedReceiptItem);
        receiptItems.add(expectedReceiptItem2);
        Collections.shuffle(receiptItems);

        final DataBaseFinder finder = new DataBaseFinder(receiptItems);
        final List<Match> matches = finder.find(expectedReceiptItem);

        assertThat(matches, hasSize(3));
        final Match bestMatch = matches.get(0);
        assertReflectionEquals(new Match(expectedReceiptItem, expectedReceiptItem.length()), bestMatch);
        assertReflectionEquals(new Match(expectedReceiptItem2, expectedReceiptItem2.length() - 2), matches.get(1));
    }
}