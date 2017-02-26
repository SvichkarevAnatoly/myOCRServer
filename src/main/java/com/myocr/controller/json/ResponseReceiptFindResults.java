package com.myocr.controller.json;

import java.util.ArrayList;
import java.util.List;

public class ResponseReceiptFindResults {
    private List<ResponseFindResult> responseFindResults = new ArrayList<>();

    public ResponseReceiptFindResults() {
    }

    public ResponseReceiptFindResults(List<ResponseFindResult> responseFindResults) {
        this.responseFindResults = responseFindResults;
    }

    public List<ResponseFindResult> getResponseFindResults() {
        return responseFindResults;
    }

    public void setResponseFindResults(List<ResponseFindResult> responseFindResults) {
        this.responseFindResults = responseFindResults;
    }
}
