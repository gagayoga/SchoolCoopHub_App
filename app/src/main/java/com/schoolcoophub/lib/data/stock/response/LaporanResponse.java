package com.schoolcoophub.lib.data.stock.response;

import com.google.gson.annotations.SerializedName;
import com.schoolcoophub.lib.data.stock.model.LaporanPendapatan;
import com.schoolcoophub.lib.data.stock.model.LaporanPendapatan;

import java.util.List;

public class LaporanResponse {
    @SerializedName("status")
    private int status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<LaporanPendapatan> data;

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

    public List<LaporanPendapatan> getData() {
        return data;
    }

    public void setData(List<LaporanPendapatan> data) {
        this.data = data;
    }
}
