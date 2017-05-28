package com.myocr.model.align;

public class SimpleAlignComparator implements AlignComparator {
    private final int match;
    private final int mismatch;
    private final int deletion;

    public SimpleAlignComparator() {
        this(1, 1, 1);
    }

    public SimpleAlignComparator(int deletion, int mismatch, int match) {
        this.deletion = deletion;
        this.mismatch = mismatch;
        this.match = match;
    }

    @Override
    public int compare(char c1, char c2) {
        return Character.toLowerCase(c1) == Character.toLowerCase(c2) ? match : -mismatch;
    }

    @Override
    public int getDeletion() {
        return deletion;
    }
}
