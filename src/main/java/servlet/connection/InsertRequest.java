package servlet.connection;

import com.google.gson.annotations.SerializedName;
import db.ProductDataSet;

import java.util.List;

public class InsertRequest {

    @SerializedName("productDataSets")
    public List<ProductDataSet> productDataSets;
}
