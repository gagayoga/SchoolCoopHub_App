package com.schoolcoophub.lib.data.stock.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.schoolcoophub.R;
import com.schoolcoophub.lib.koneksi.ApiClient;
import com.schoolcoophub.lib.koneksi.UserServices;
import com.schoolcoophub.lib.data.stock.adapter.HistoryStockAdapter;
import com.schoolcoophub.lib.data.stock.response.HistoryStockResponse;
import com.schoolcoophub.lib.data.stock.model.HistoryStocks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryStock extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryStockAdapter adapter;
    private List<HistoryStocks> stockList;
    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefreshLayout;
    private SharedPreferences sharedPreferences;
    private String userToken, idUser;
    private ImageView logoRefresh, imageError;
    private LottieAnimationView loadingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_stock);

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryStock.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imageError = findViewById(R.id.imageError);
        loadingData = findViewById(R.id.loadingData);

        setupRecyclerView();
        fetchLatestStock();

        logoRefresh = findViewById(R.id.logoRefresh);
        logoRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setupRecyclerView();
                fetchLatestStock();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setup RecyclerView untuk menampilkan data stok dalam bentuk horizontal
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        stockList = new ArrayList<>();
        adapter = new HistoryStockAdapter(this, stockList);
        recyclerView.setAdapter(adapter);
    }

    // Lakukan permintaan API untuk mendapatkan data stok terbaru
    private void fetchLatestStock() {
        UserServices apiService = ApiClient.getUserServices(); // Mendapatkan instance dari UserServices dari ApiClient
        loadingData.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;

        Call<HistoryStockResponse> call = apiService.getHistoryStock(token); // Gunakan token tanpa "Bearer"

        call.enqueue(new Callback<HistoryStockResponse>() {
            @Override
            public void onResponse(@NonNull Call<HistoryStockResponse> call, @NonNull Response<HistoryStockResponse> response) {
                loadingData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    HistoryStockResponse stockResponse = response.body();
                    if (stockResponse.getStatus() == 200) {
                        List<HistoryStocks> fetchedStockList = stockResponse.getData();
                        stockList.addAll(fetchedStockList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(HistoryStock.this, "Failed to fetch data: " + stockResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(HistoryStock.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HistoryStockResponse> call, @NonNull Throwable t) {
                loadingData.setVisibility(View.GONE);
                imageError.setVisibility(View.VISIBLE);
                Toast.makeText(HistoryStock.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onRefresh(){
        setupRecyclerView();
        fetchLatestStock();
    }
}