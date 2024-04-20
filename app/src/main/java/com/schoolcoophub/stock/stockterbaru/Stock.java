package com.schoolcoophub.stock.stockterbaru;

import com.google.gson.annotations.SerializedName;

public class Stock {
    @SerializedName("id")
    private int id;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("harga_barang")
    private int hargaBarang;

    @SerializedName("jumlah_barang")
    private int jumlahBarang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }
}
