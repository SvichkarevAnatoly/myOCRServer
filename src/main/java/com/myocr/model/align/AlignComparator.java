package com.myocr.model.align;

public interface AlignComparator {
    int compare(char c1, char c2);

    int getDeletion();
}
