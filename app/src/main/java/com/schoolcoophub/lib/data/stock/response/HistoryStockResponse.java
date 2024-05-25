package com.schoolcoophub.lib.data.stock.response;

import com.google.gson.annotations.SerializedName;
import com.schoolcoophub.lib.data.stock.model.HistoryStocks;;

import java.util.List;

public class HistoryStockResponse {


    private int status;
    private String message;
    private List<HistoryStocks> data;

    public HistoryStockResponse(int status, String message, List<HistoryStocks> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

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

    public List<HistoryStocks> getData() {
        return data;
    }

    public void setData(List<HistoryStocks> data) {
        this.data = data;
    }

}

