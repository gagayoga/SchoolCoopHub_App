package com.schoolcoophub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.schoolcoophub.app.login.ApiClient;
import com.schoolcoophub.app.login.LogoutTask;
import com.schoolcoophub.app.login.UserServices;
import com.schoolcoophub.stock.stockterbaru.Stock;
import com.schoolcoophub.stock.stockterbaru.StockAdapter;
import com.schoolcoophub.stock.stockterbaru.StockResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StockAdapter stockAdapter;
    private LinearLayout menu1, menu2, menu3;
    private ImageView logout;

    private CardView cvHistory;

    private SharedPreferences sharedPreferences;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        if (userToken.isEmpty()) {
            Toast.makeText(this, "Token pengguna tidak tersedia. Silakan login kembali.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
            finish();
        } else {
            // Token tersedia, lanjutkan dengan melakukan permintaan API
            setupRecyclerView();
            fetchLatestStock();
        }

        menu1 = findViewById(R.id.kategori1);
        menu2 = findViewById(R.id.kategori2);
        menu3 = findViewById(R.id.kategori3);

        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StockByKategori.class);
                String idKategori = "1";
                String namaKategori = "School Atribute";
                intent.putExtra("namaKategori", namaKategori);
                intent.putExtra("idKategori", idKategori);
                startActivity(intent);
            }
        });

        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StockByKategori.class);
                String idKategori = "2";
                String namaKategori = "Drink";
                intent.putExtra("namaKategori", namaKategori);
                intent.putExtra("idKategori", idKategori);
                startActivity(intent);
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StockByKategori.class);
                String idKategori = "3";
                String namaKategori = "Other";
                intent.putExtra("namaKategori", namaKategori);
                intent.putExtra("idKategori", idKategori);
                startActivity(intent);
            }
        });

        logout = findViewById(R.id.imageLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogoutTask(MainActivity.this).execute();
            }
        });

        cvHistory = findViewById(R.id.cvHistory);
        cvHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryStock.class);
                startActivity(intent);
            }
        });
    }

    // Metode untuk mengambil token pengguna dari SharedPreferences
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Setup RecyclerView untuk menampilkan data stok dalam bentuk horizontal
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    // Lakukan permintaan API untuk mendapatkan data stok terbaru
    private void fetchLatestStock() {
        UserServices apiService = ApiClient.getUserServices(); // Mendapatkan instance dari UserServices dari ApiClient

        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;

        Call<StockResponse> call = apiService.getLatestStock(token);
        call.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                if (response.isSuccessful()) {
                    StockResponse stockResponse = response.body();
                    if (stockResponse != null && stockResponse.getStatus() == 200) {
                        List<Stock> stocks = stockResponse.getData();
                        // Tampilkan data stok dalam RecyclerView
                        stockAdapter = new StockAdapter(MainActivity.this, stocks);
                        recyclerView.setAdapter(stockAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "Gagal mengambil data stok", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
