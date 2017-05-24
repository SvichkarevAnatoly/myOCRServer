package com.myocr.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static com.myocr.util.ReceiptItemUtil.isValid;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReceiptItemUtilValidateTest {
    @Test
    public void validateNormal() throws Exception {
        assertTrue(isValid("Valid receipt item"));
    }

    @Test
    public void validateRandomNormal() throws Exception {
        for (int i = 0; i < 100; i++) {
            final String receiptItem = RandomStringUtils.randomAlphabetic(25);
            assertTrue(isValid(receiptItem));
        }
    }

    @Test
    public void validateLong() throws Exception {
        final String longReceiptItem = RandomStringUtils.randomAlphabetic(200);
        assertFalse(isValid(longReceiptItem));
    }

    @Test
    public void validateMix() throws Exception {
        for (int i = 0; i < 1000; i++) {
            final String receiptItem = RandomStringUtils.randomAlphabetic(4, 200);
            assertEquals(receiptItem.length() < 100, isValid(receiptItem));
        }
    }

    @Test
    public void validateNull() throws Exception {
        assertFalse(isValid(null));
    }

    @Test
    public void validateEmptyString() throws Exception {
        assertFalse(isValid(""));
    }

    @Test
    public void validateNotEmptyStringButNoSense() throws Exception {
        assertFalse(isValid("\t"));
    }
}