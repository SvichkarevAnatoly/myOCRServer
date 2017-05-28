package com.myocr.model.align;


public interface Aligner {
    int align(String str1, String str2);

    String getAlign1();

    String getAlign2();
}
