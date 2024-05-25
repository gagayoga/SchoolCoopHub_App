package com.schoolcoophub.lib.data.stock.model;
import com.google.gson.annotations.SerializedName;

public class HistoryStocks {
    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("nama_barang")
    private String namaBarang;

    @SerializedName("harga_barang")
    private int hargaBarang;

    @SerializedName("jumlah_barang")
    private int jumlahBarang;

    @SerializedName("total_harga")
    private int totalHarga;

    @SerializedName("status")
    private String status;

    @SerializedName("tanggal_pesan")
    private String tanggalPesan;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalPesan() {
        return tanggalPesan;
    }

    public void setTanggalPesan(String tanggalPesan) {
        this.tanggalPesan = tanggalPesan;
    }
}
