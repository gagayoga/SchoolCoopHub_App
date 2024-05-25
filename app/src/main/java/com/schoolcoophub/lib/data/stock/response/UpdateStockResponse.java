package com.schoolcoophub.lib.data.stock.response;

public class UpdateStockResponse {
    private int status;
    private String message;
    private StockData data;

    // Getters and setters

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

    public StockData getData() {
        return data;
    }

    public void setData(StockData data) {
        this.data = data;
    }

    public static class StockData {
        private int id;
        private String nama_barang;
        private int harga_barang;
        private int jumlah_barang;
        private int id_kategori;
        private String tanggal_upload;
        private String created_at;
        private String updated_at;

        // Getters and setters

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNamaBarang() {
            return nama_barang;
        }

        public void setNamaBarang(String nama_barang) {
            this.nama_barang = nama_barang;
        }

        public int getHargaBarang() {
            return harga_barang;
        }

        public void setHargaBarang(int harga_barang) {
            this.harga_barang = harga_barang;
        }

        public int getJumlahBarang() {
            return jumlah_barang;
        }

        public void setJumlahBarang(int jumlah_barang) {
            this.jumlah_barang = jumlah_barang;
        }

        public int getIdKategori() {
            return id_kategori;
        }

        public void setIdKategori(int id_kategori) {
            this.id_kategori = id_kategori;
        }

        public String getTanggalUpload() {
            return tanggal_upload;
        }

        public void setTanggalUpload(String tanggal_upload) {
            this.tanggal_upload = tanggal_upload;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}

