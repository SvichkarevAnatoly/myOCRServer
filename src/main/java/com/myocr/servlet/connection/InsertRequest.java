package com.myocr.servlet.connection;

import com.google.gson.annotations.SerializedName;
import com.myocr.model.pojo.ProductDataSet;

import java.util.List;

public class InsertRequest {

    @SerializedName("productDataSets")
    public List<ProductDataSet> productDataSets;
}
