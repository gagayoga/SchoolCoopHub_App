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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.schoolcoophub.R;
import com.schoolcoophub.lib.data.stock.adapter.StockKategoriAdapter;
import com.schoolcoophub.lib.data.stock.adapter.StockKosongAdapter;
import com.schoolcoophub.lib.data.stock.interfacestock.StockUpdateListener;
import com.schoolcoophub.lib.data.stock.model.StockKategori;
import com.schoolcoophub.lib.data.stock.model.StockKosong;
import com.schoolcoophub.lib.data.stock.response.StockKosongResponse;
import com.schoolcoophub.lib.koneksi.ApiClient;
import com.schoolcoophub.lib.koneksi.UserServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockKosongView extends AppCompatActivity implements StockUpdateListener {

    private RecyclerView recyclerView;
    private StockKosongAdapter adapter;
    private List<StockKosong> stockList;
    private List<StockKosong> stockItemList = new ArrayList<>();
    private Toolbar toolbar;
    private TextView textTopBar, textNoData;
    private SharedPreferences sharedPreferences;
    private String userToken, idUser;
    private int stockID;
    private LottieAnimationView loadingData;
    private ImageView imageError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_kosong);

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        imageError = findViewById(R.id.imageError);
        loadingData = findViewById(R.id.loadingData);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat intent untuk kembali ke MainActivity (atau aktivitas sebelumnya)
                Intent intent = new Intent(StockKosongView.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Text top bar
        textTopBar = findViewById(R.id.textTopBar);
        textTopBar.setText("Stock Produk Kosong");

        setupRecyclerView();
        fetchStockKosong();

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Text data kosong
        textNoData = findViewById(R.id.textNoData);
    }

    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setup RecyclerView untuk menampilkan data stok dalam bentuk horizontal
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        stockList = new ArrayList<>();
        adapter = new StockKosongAdapter(this, stockList, this);
        recyclerView.setAdapter(adapter);
    }

    // Lakukan permintaan API untuk mendapatkan data stok terbaru
    private void fetchStockKosong() {
        UserServices apiService = ApiClient.getUserServices(); // Mendapatkan instance dari UserServices dari ApiClient
        loadingData.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;

        Call<StockKosongResponse> call = apiService.getStockKosong(token);

        call.enqueue(new Callback<StockKosongResponse>() {
            @Override
            public void onResponse(@NonNull Call<StockKosongResponse> call, @NonNull Response<StockKosongResponse> response) {
                loadingData.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    StockKosongResponse stockResponse = response.body();
                    if (stockResponse.getStatus() == 200) {
                        List<StockKosong> fetchedStockList = stockResponse.getData();
                        showRecyclerView();
                        stockList.clear();
                        stockList.addAll(fetchedStockList);
                        adapter.notifyDataSetChanged();
                    }else {
                        Toast.makeText(StockKosongView.this, "Failed to fetch data: " + stockResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (response.code() == 404) {
                    showNoDataMessage();
                    Toast.makeText(StockKosongView.this, "Data stock yang kosong null(0)", Toast.LENGTH_SHORT).show();
                }else{
                    String errorMessage = "Failed to fetch data";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(StockKosongView.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StockKosongResponse> call, @NonNull Throwable t) {
                loadingData.setVisibility(View.GONE);
                imageError.setVisibility(View.VISIBLE);
                Toast.makeText(StockKosongView.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        setupRecyclerView();
        fetchStockKosong();
    }

    private void showNoDataMessage() {
        recyclerView.setVisibility(View.GONE);
        textNoData.setVisibility(View.VISIBLE);
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        textNoData.setVisibility(View.GONE);
    }

    @Override
    public void onStockUpdated() {
        // Refresh the data
        getData();
    }
}