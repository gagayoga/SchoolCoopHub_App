package com.schoolcoophub.app.login;

import com.schoolcoophub.stock.historystock.HistoryStockResponse;
import com.schoolcoophub.stock.stockbykategori.StockKategori;
import com.schoolcoophub.stock.stockbykategori.StockKategoriResponse;
import com.schoolcoophub.stock.stockkeluar.StockKeluarRequest;
import com.schoolcoophub.stock.stockkeluar.StockKeluarResponse;
import com.schoolcoophub.stock.stockterbaru.Stock;
import com.schoolcoophub.stock.stockterbaru.StockResponse;
import com.schoolcoophub.app.login.LoginResponse;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserServices {

    @Headers({"Accept: application/json"})

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("stock/tampil/baru")
    Call<StockResponse> getLatestStock(@Header("Authorization") String token
    );

    @GET("stock/tampil/{namaKategori}")
    Call<StockKategoriResponse> getStockByCategory(
            @Header("Authorization") String token,
            @Path("namaKategori") String namaKategori
    );

    @POST("stock/keluar")
    Call<StockKeluarResponse> stockKeluar(@Header("Authorization") String token,
                                          @Body StockKeluarRequest stockKeluarRequest);

    @GET("stock/keluar")
    Call<HistoryStockResponse> getHistoryStock(
            @Header("Authorization") String token);
}
