package com.schoolcoophub.lib.request;

import com.google.gson.annotations.SerializedName;

public class UpdateStockRequest {
    @SerializedName("jumlah_barang")
    private int jumlahBarang;

    public UpdateStockRequest(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }
}
