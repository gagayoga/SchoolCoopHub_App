package com.schoolcoophub.stock.stockkeluar;

import com.google.gson.annotations.SerializedName;

public class StockKeluarRequest {

    @SerializedName("id_user")
    private String userId;

    @SerializedName("id_stock")
    private String stockId;

    @SerializedName("jumlah")
    private String jumlah;

    @SerializedName("total_harga")
    private String totalHarga;

    @SerializedName("status")
    private String status;

    public StockKeluarRequest(String userId, String stockId, String jumlah, String totalHarga, String status) {
        this.userId = userId;
        this.stockId = stockId;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

