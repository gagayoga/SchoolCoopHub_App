package com.schoolcoophub.lib.koneksi;

import com.schoolcoophub.lib.data.stock.response.LaporanPendapatanResponse;
import com.schoolcoophub.lib.data.stock.response.LaporanResponse;
import com.schoolcoophub.lib.data.stock.response.StockKosongResponse;
import com.schoolcoophub.lib.data.stock.response.UpdateStockResponse;
import com.schoolcoophub.lib.request.LoginRequest;
import com.schoolcoophub.lib.request.UpdateStockRequest;
import com.schoolcoophub.lib.response.LoginResponse;
import com.schoolcoophub.lib.data.stock.response.HistoryStockResponse;
import com.schoolcoophub.lib.data.stock.response.StockKategoriResponse;
import com.schoolcoophub.lib.request.StockKeluarRequest;
import com.schoolcoophub.lib.data.stock.response.StockKeluarResponse;
import com.schoolcoophub.lib.data.stock.response.StockResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

// Endpoint API
public interface UserServices {

    @Headers({"Accept: application/json"})

    @POST("login")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("stock/tampil/baru")
    Call<StockResponse> getLatestStock(@Header("Authorization") String token
    );

    @GET("guest-tampil-stock")
    Call<StockResponse> getGuestStock();

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

    @GET("stock/kosong")
    Call<StockKosongResponse> getStockKosong(
            @Header("Authorization") String token);

    @PATCH("update/stock/{idStock}")
    Call<UpdateStockResponse> updateStock(
            @Header("Authorization") String token,
            @Body UpdateStockRequest updateStockRequest,
            @Path("idStock") int idStock
    );

    @GET("laporan-penjualan")
    Call<LaporanResponse> getLaporanPenjualan(
            @Header("Authorization") String token);

    @GET("laporan-all-penjualan")
    Call<LaporanPendapatanResponse> getLaporanPendapatan(
            @Header("Authorization") String token);
}
