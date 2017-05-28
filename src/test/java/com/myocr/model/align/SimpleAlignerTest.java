package com.myocr.model.align;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;

public class SimpleAlignerTest {
    @Test
    public void scoreNucleotides() throws Exception {
        final Aligner aligner = new SimpleAligner(1, 1);
        assertThat(aligner.align("ACT", "ATT"), is(1));
    }

    @Test
    public void alignNucleotides() throws Exception {
        final Aligner aligner = new SimpleAligner(2, 1);
        final String v = "ACT";
        final String w = "ATT";
        aligner.align(v, w);
        assertThat(aligner.getAlign1(), is(v));
        assertThat(aligner.getAlign2(), is(w));
    }

    @Test
    public void scoreEnglishWords() throws Exception {
        final Aligner aligner = new SimpleAligner(1, 1);
        final String v = "PLEASANTLY";
        final String w = "MEANLY";
        assertThat(aligner.align(v, w), is(0));
    }

    @Test
    public void alignEnglishWords() throws Exception {
        final Aligner aligner = new SimpleAligner();
        final String v = "PLEASANTLY";
        final String w = "MEANLY";
        aligner.align(v, w);
        assertThat(aligner.getAlign1(), is(v));
        assertThat(aligner.getAlign2(), is("M-EA--N-LY"));
    }

    @Test
    public void alignIgnoringCase() throws Exception {
        SimpleAligner aligner = new SimpleAligner();
        int score = aligner.align("align", "ALIGN");
        assertEquals(5, score);
        assertEquals("align", aligner.getAlign1());
        assertEquals("ALIGN", aligner.getAlign2());
    }
}