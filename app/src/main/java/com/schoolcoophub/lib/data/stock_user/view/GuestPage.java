package com.schoolcoophub.lib.data.stock_user.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.schoolcoophub.R;
import com.schoolcoophub.lib.koneksi.ApiClient;
import com.schoolcoophub.lib.koneksi.UserServices;
import com.schoolcoophub.authentikasi.LoginPage;
import com.schoolcoophub.lib.data.stock.model.Stock;
import com.schoolcoophub.lib.data.stock_user.adapter.StockGuestAdapter;
import com.schoolcoophub.lib.data.stock.response.StockResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestPage extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StockGuestAdapter stockAdapter;
    private ImageView imageLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_page);

        setupRecyclerView();
        fetchLatestStock();

        imageLogin = findViewById(R.id.imageLogin);
        imageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuestPage.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // Setup RecyclerView untuk menampilkan data stok dalam bentuk horizontal
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    // Lakukan permintaan API untuk mendapatkan data stok terbaru
    private void fetchLatestStock() {
        UserServices apiService = ApiClient.getUserServices();

        Call<StockResponse> call = apiService.getGuestStock();
        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                if (response.isSuccessful()) {
                    StockResponse stockResponse = response.body();
                    if (stockResponse != null && stockResponse.getStatus() == 200) {
                        List<Stock> stocks = stockResponse.getData();
                        // Tampilkan data stok dalam RecyclerView
                        stockAdapter = new StockGuestAdapter(GuestPage.this, stocks);
                        recyclerView.setAdapter(stockAdapter);
                    } else {
                        Toast.makeText(GuestPage.this, "Gagal mengambil data stok", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GuestPage.this, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                Toast.makeText(GuestPage.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData(){
        setupRecyclerView();
        fetchLatestStock();
    }
}