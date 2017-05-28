package com.myocr.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(value = Parameterized.class)
public class TextFieldUtilPrepareTest {
    @Parameter
    public String receiptItem;

    @Parameter(value = 1)
    public String expectedPreparedReceiptItem;

    @Parameters(name = "{index}: prepare(\"{0}\") = \"{1}\"")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Hello", "Hello"},
                {"Hello world", "Hello world"},
                {"Hello  ", "Hello"},
                {"  Hello", "Hello"},
                {"  Hello world  ", "Hello world"},
                {"Hello   world", "Hello world"},
                {"Hello   world,   Anatoly", "Hello world, Anatoly"},
                {"Hello\tworld", "Hello world"},
                {"Hello\t\tworld", "Hello world"},
                {"Hello\nworld", "Hello world"},
                {"Hello\n\t  \n \tworld", "Hello world"},
        });
    }

    @Test
    public void prepare() {
        assertEquals(expectedPreparedReceiptItem,
                TextFieldUtil.prepare(receiptItem));
    }
}