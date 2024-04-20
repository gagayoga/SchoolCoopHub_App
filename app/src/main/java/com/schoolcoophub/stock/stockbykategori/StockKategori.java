package com.schoolcoophub.stock.stockbykategori;

import java.util.List;

public class StockKategori {

    private int id;
    private String nama_barang;
    private int harga_barang;
    private int jumlah_barang;
    private int id_kategori;
    private int selectedQuantity;
    private String tanggal_upload;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public String getNamaBarang() {
        return nama_barang;
    }

    public int getHargaBarang() {
        return harga_barang;
    }

    public int getJumlahBarang() {
        return jumlah_barang;
    }

    public int getIdKategori() {
        return id_kategori;
    }

    public String getTanggalUpload() {
        return tanggal_upload;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public int getSelectedQuantity() {
        return selectedQuantity;
    }

    public void setSelectedQuantity(int selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }
}
