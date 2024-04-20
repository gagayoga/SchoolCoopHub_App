package com.schoolcoophub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.schoolcoophub.app.login.ApiClient;
import com.schoolcoophub.app.login.UserServices;
import com.schoolcoophub.stock.historystock.HistoryStockAdapter;
import com.schoolcoophub.stock.historystock.HistoryStockResponse;
import com.schoolcoophub.stock.historystock.HistoryStocks;
import com.schoolcoophub.stock.stockbykategori.StockKategori;
import com.schoolcoophub.stock.stockbykategori.StockKategoriAdapter;

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

    private SharedPreferences sharedPreferences;
    private String userToken, idUser;

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

        setupRecyclerView();
        fetchLatestStock();
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

        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;

        Call<HistoryStockResponse> call = apiService.getHistoryStock(token); // Gunakan token tanpa "Bearer"

        call.enqueue(new Callback<HistoryStockResponse>() {
            @Override
            public void onResponse(@NonNull Call<HistoryStockResponse> call, @NonNull Response<HistoryStockResponse> response) {
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
                Toast.makeText(HistoryStock.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}