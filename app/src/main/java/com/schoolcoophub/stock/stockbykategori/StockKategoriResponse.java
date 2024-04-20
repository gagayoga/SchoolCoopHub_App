package com.schoolcoophub.stock.stockbykategori;

import java.util.List;

public class StockKategoriResponse {

    private int status;
    private String message;
    private List<StockKategori> data;

    public StockKategoriResponse(int status, String message, List<StockKategori> data) {
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

    public List<StockKategori> getData() {
        return data;
    }

    public void setData(List<StockKategori> data) {
        this.data = data;
    }
}
