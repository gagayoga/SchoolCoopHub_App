package com.schoolcoophub.stock.stockkeluar;

import com.google.gson.annotations.SerializedName;

public class StockKeluarResponse {

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private StockKeluarData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StockKeluarData getData() {
        return data;
    }

    public void setData(StockKeluarData data) {
        this.data = data;
    }

    public static class StockKeluarData {

        @SerializedName("email")
        private String email;

        @SerializedName("name")
        private String name;

        @SerializedName("nama_barang")
        private String namaBarang;

        @SerializedName("harga_barang")
        private int hargaBarang;

        @SerializedName("stock_barang")
        private int stockBarang;

        @SerializedName("jumlah_barang")
        private String jumlahBarang;

        @SerializedName("total_harga")
        private String totalHarga;

        @SerializedName("tanggal_keluar")
        private String tanggalKeluar;

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

        public int getStockBarang() {
            return stockBarang;
        }

        public void setStockBarang(int stockBarang) {
            this.stockBarang = stockBarang;
        }

        public String getJumlahBarang() {
            return jumlahBarang;
        }

        public void setJumlahBarang(String jumlahBarang) {
            this.jumlahBarang = jumlahBarang;
        }

        public String getTotalHarga() {
            return totalHarga;
        }

        public void setTotalHarga(String totalHarga) {
            this.totalHarga = totalHarga;
        }

        public String getTanggalKeluar() {
            return tanggalKeluar;
        }

        public void setTanggalKeluar(String tanggalKeluar) {
            this.tanggalKeluar = tanggalKeluar;
        }
    }
}

