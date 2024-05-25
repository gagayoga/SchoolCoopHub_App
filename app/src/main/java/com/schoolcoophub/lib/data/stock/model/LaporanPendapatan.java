package com.schoolcoophub.lib.data.stock.model;

import com.google.gson.annotations.SerializedName;

public class LaporanPendapatan {
    @SerializedName("bulan")
    private String bulan;

    @SerializedName("tahun")
    private int tahun;

    @SerializedName("total_pendapatan")
    private String totalPendapatan;

    @SerializedName("total_terjual")
    private String totalBarang;

    @SerializedName("produk_penjualan_terbanyak")
    private String produkTerlaris;

    public String getBulan() {
        return bulan;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getTotalPendapatan() {
        return totalPendapatan;
    }

    public void setTotalPendapatan(String totalPendapatan) {
        this.totalPendapatan = totalPendapatan;
    }

    public String getTotalBarang() {
        return totalBarang;
    }

    public void setTotalBarang(String totalBarang) {
        this.totalBarang = totalBarang;
    }

    public String getProdukTerlaris() {
        return produkTerlaris;
    }

    public void setProdukTerlaris(String produkTerlaris) {
        this.produkTerlaris = produkTerlaris;
    }
}
