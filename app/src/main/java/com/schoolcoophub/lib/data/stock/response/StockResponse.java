package com.schoolcoophub.lib.data.stock.response;
import com.google.gson.annotations.SerializedName;
import com.schoolcoophub.lib.data.stock.model.Stock;

import java.util.List;
public class StockResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Stock> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Stock> getData() {
        return data;
    }

    public void setData(List<Stock> data) {
        this.data = data;
    }
}
