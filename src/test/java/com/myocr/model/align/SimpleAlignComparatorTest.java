package com.myocr.model.align;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleAlignComparatorTest {
    @Test
    public void match() throws Exception {
        AlignComparator comparator = new SimpleAlignComparator();
        final int result = comparator.compare('a', 'a');
        assertEquals(1, result);
    }

    @Test
    public void mismatch() throws Exception {
        AlignComparator comparator = new SimpleAlignComparator();
        final int result = comparator.compare('a', 'b');
        assertEquals(-1, result);
    }

    @Test
    public void matchIgnoringCase() throws Exception {
        AlignComparator comparator = new SimpleAlignComparator();
        final int result = comparator.compare('a', 'A');
        assertEquals(1, result);
    }

    @Test
    public void getDeletion() throws Exception {
        AlignComparator comparator = new SimpleAlignComparator();
        assertEquals(1, comparator.getDeletion());
    }
}