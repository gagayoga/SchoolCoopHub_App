package com.schoolcoophub.lib.data.stock.response;

import com.google.gson.annotations.SerializedName;

public class LaporanPendapatanResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("jumlah_bulan")
    private String jumlah_bulan;

    @SerializedName("total_pendapatan")
    private String total_pendapatan;

    @SerializedName("total_terjual")
    private String total_terjual;

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

    public String getJumlah_bulan() {
        return jumlah_bulan;
    }

    public void setJumlah_bulan(String jumlah_bulan) {
        this.jumlah_bulan = jumlah_bulan;
    }

    public String getTotal_pendapatan() {
        return total_pendapatan;
    }

    public void setTotal_pendapatan(String total_pendapatan) {
        this.total_pendapatan = total_pendapatan;
    }

    public String getTotal_terjual() {
        return total_terjual;
    }

    public void setTotal_terjual(String total_terjual) {
        this.total_terjual = total_terjual;
    }
}
