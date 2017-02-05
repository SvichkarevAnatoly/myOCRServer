package servlet.connection;

import com.google.gson.annotations.SerializedName;

public class OcrResponse {
    @SerializedName("ocrText")
    public String ocrText;

    public OcrResponse(String ocrText) {
        this.ocrText = ocrText;
    }
}
