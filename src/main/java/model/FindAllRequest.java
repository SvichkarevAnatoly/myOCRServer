package model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FindAllRequest {

    @SerializedName("products")
    public List<String> products;
}
