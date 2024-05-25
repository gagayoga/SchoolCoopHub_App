package com.schoolcoophub.lib.data.stock.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.schoolcoophub.R;
import com.schoolcoophub.lib.data.stock.adapter.LaporanPenjualanAdapter;
import com.schoolcoophub.lib.data.stock.adapter.StockKategoriAdapter;
import com.schoolcoophub.lib.data.stock.model.LaporanPendapatan;
import com.schoolcoophub.lib.data.stock.model.StockKategori;
import com.schoolcoophub.lib.data.stock.response.LaporanPendapatanResponse;
import com.schoolcoophub.lib.data.stock.response.LaporanResponse;
import com.schoolcoophub.lib.data.stock.response.LaporanResponse;
import com.schoolcoophub.lib.koneksi.ApiClient;
import com.schoolcoophub.lib.koneksi.UserServices;
import com.schoolcoophub.lib.request.LoginRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPenjualan extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LaporanPenjualanAdapter adapter;
    private List<LaporanPendapatan> stockList;
    private List<LaporanPendapatan> stockItemList = new ArrayList<>();
    private Toolbar toolbar;
    private MaterialButton buttonPesan;
    private TextView textTopBar, tvJumlahPorsi, tvTotalPrice, tvNamaBarang, txtTotalPendapatan, txtTotalBarang;
    private SharedPreferences sharedPreferences;
    private String userToken, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_penjualan);

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        toolbar = findViewById(R.id.toolbar);
        txtTotalPendapatan = findViewById(R.id.txtTotalPendapatan);
        txtTotalBarang = findViewById(R.id.txtTotalBarang);
        
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat intent untuk kembali ke MainActivity (atau aktivitas sebelumnya)
                Intent intent = new Intent(LaporanPenjualan.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        getData();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void getData() {
        setupRecyclerView();
        fetchLatestStock();
        loadDataPendapatan();
    }

    // Get token
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setup RecyclerView untuk menampilkan data stok dalam bentuk horizontal
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        stockList = new ArrayList<>();
        adapter = new LaporanPenjualanAdapter(this, stockList);
        recyclerView.setAdapter(adapter);
    }

    // Lakukan permintaan API untuk mendapatkan data stok terbaru
    private void fetchLatestStock() {
        UserServices apiService = ApiClient.getUserServices(); // Mendapatkan instance dari UserServices dari ApiClient

        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;

        Call<LaporanResponse> call = apiService.getLaporanPenjualan(token);

        call.enqueue(new Callback<LaporanResponse>() {
            @Override
            public void onResponse(@NonNull Call<LaporanResponse> call, @NonNull Response<LaporanResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LaporanResponse stockResponse = response.body();
                    if (stockResponse.getStatus() == 200) {
                        List<LaporanPendapatan> fetchedStockList = stockResponse.getData();
                        stockList.addAll(fetchedStockList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(LaporanPenjualan.this, "Failed to fetch data: " + stockResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Tangani kesalahan jika respons tidak berhasil atau body null
                    String errorMessage = "Failed to fetch data";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(LaporanPenjualan.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LaporanResponse> call, @NonNull Throwable t) {
                Toast.makeText(LaporanPenjualan.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadDataPendapatan(){
        UserServices apiService = ApiClient.getUserServices(); // Mendapatkan instance dari UserServices dari ApiClient

        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;
        
        Call<LaporanPendapatanResponse> LaporanPenjualanCall = ApiClient.getUserServices().getLaporanPendapatan(token);

        LaporanPenjualanCall.enqueue(new Callback<LaporanPendapatanResponse>() {
            @Override
            public void onResponse(Call<LaporanPendapatanResponse> call, Response<LaporanPendapatanResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    LaporanPendapatanResponse loginResponse = response.body();

                    String totalPendapatan = loginResponse.getTotal_pendapatan();
                    String totalBarang = loginResponse.getTotal_terjual();

                    txtTotalPendapatan.setText("Rp." + totalPendapatan);
                    txtTotalBarang.setText(totalBarang + " Barang");
                }else{
                    Toast.makeText(LaporanPenjualan.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LaporanPendapatanResponse> call, Throwable t) {
                Toast.makeText(LaporanPenjualan.this, "Periksa koneksi anda, gagal get data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}