package com.schoolcoophub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.schoolcoophub.app.login.ApiClient;
import com.schoolcoophub.app.login.LoginRequest;
import com.schoolcoophub.app.login.LoginResponse;
import com.schoolcoophub.app.login.LoginResponseErrors;
import com.schoolcoophub.app.login.UserServices;
import com.schoolcoophub.stock.stockbykategori.StockKategori;
import com.schoolcoophub.stock.stockbykategori.StockKategoriAdapter;
import com.schoolcoophub.stock.stockbykategori.StockKategoriResponse;
import com.schoolcoophub.stock.stockkeluar.DataPengeluaranStock;
import com.schoolcoophub.stock.stockkeluar.StockKeluarRequest;
import com.schoolcoophub.stock.stockkeluar.StockKeluarResponse;
import com.schoolcoophub.stock.stockterbaru.Stock;
import com.schoolcoophub.stock.stockterbaru.StockAdapter;
import com.schoolcoophub.stock.stockterbaru.StockResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockByKategori extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StockKategoriAdapter adapter;
    private List<StockKategori> stockList;
    private List<StockKategori> stockItemList = new ArrayList<>();
    private Toolbar toolbar;
    private MaterialButton buttonPesan;
    private TextView textTopBar, tvJumlahPorsi, tvTotalPrice, tvNamaBarang;

    private SharedPreferences sharedPreferences;
    private String userToken, idUser;
    private int items, totalHarga;
    private int stockID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_by_kategori);

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();
        idUser = getIdUser();

        toolbar = findViewById(R.id.toolbar);
        tvJumlahPorsi = findViewById(R.id.tvJumlahPorsi);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvNamaBarang = findViewById(R.id.tvNamaBarang);
        textTopBar = findViewById(R.id.textTopBar);

        // Ambil namaKategori dari Intent
        String namaKategori = getIntent().getStringExtra("namaKategori");
        textTopBar.setText(namaKategori);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat intent untuk kembali ke MainActivity (atau aktivitas sebelumnya)
                Intent intent = new Intent(StockByKategori.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        setupRecyclerView();
        fetchLatestStock();

        // Implementasi logika untuk menghitung jumlah item yang dipilih dan harga total
        calculateTotal();

        // Setiap kali item dipilih di RecyclerView, panggil calculateTotal() lagi
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                calculateTotal();
            }
        });

        buttonPesan = findViewById(R.id.btnCheckout);
        buttonPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postStockKeluar();
            }
        });
    }

    // Post data untuk melakukan transakski
    public void postStockKeluar(){
        String userID, idStock,jumlahBarang, total, statusStock;
        idStock = Integer.toString(stockID);
        jumlahBarang = Integer.toString(items);
        total = Integer.toString(totalHarga);
        statusStock = "Terkirim";
        userID = idUser;
        String token = "Bearer " + userToken;

        StockKeluarRequest stockKeluarRequest = new StockKeluarRequest(idUser, idStock, jumlahBarang, total, statusStock);

        retrofit2.Call<StockKeluarResponse> stockKeluarResponseCall = ApiClient.getUserServices().stockKeluar(token,stockKeluarRequest);

        stockKeluarResponseCall.enqueue(new Callback<StockKeluarResponse>() {
            @Override
            public void onResponse(retrofit2.Call<StockKeluarResponse> call, Response<StockKeluarResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    StockKeluarResponse responseKeluar = response.body();

                    String namaBarang = responseKeluar.getData().getNamaBarang();
                    int hargaBarang = responseKeluar.getData().getHargaBarang();
                    int jumlahBarang = Integer.parseInt(responseKeluar.getData().getJumlahBarang());
                    int totalHarga = Integer.parseInt(responseKeluar.getData().getTotalHarga());
                    //String status = responseKeluar.getData().getS();

                    // Membuat pesan untuk ditampilkan dalam alert
                    StringBuilder messageBuilder = new StringBuilder();
                    messageBuilder.append("Data pengeluaran stok berhasil disimpan\n\n");
                    messageBuilder.append("Nama Barang: ").append(namaBarang).append("\n");
                    messageBuilder.append("Harga Barang: Rp ").append(hargaBarang).append("\n");
                    messageBuilder.append("Jumlah Barang: ").append(jumlahBarang).append("\n");
                    messageBuilder.append("Total Harga: Rp ").append(totalHarga).append("\n");
                    //messageBuilder.append("Status: ").append(status);

                    // Menampilkan pesan dalam alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockByKategori.this);
                    builder.setTitle("Transaksi Berhasil");
                    builder.setMessage(messageBuilder.toString());
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Clear Text
                            tvNamaBarang.setText("");
                            tvTotalPrice.setText("Rp 0");
                            tvJumlahPorsi.setText("0 items");
                            dialog.dismiss();

                            refreshStockData();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    Gson gson = new Gson();
                    Toast.makeText(StockByKategori.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StockKeluarResponse> call, Throwable t) {
                Toast.makeText(StockByKategori.this, "Login failed, please cek koneksi internet Anda.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Get token
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    // Refresh Data
    private void refreshStockData() {
        stockList.clear(); // Kosongkan daftar stok yang sudah ada
        fetchLatestStock(); // Ambil data stok terbaru dari server
    }

    // Get id user
    private String getIdUser() {
        return sharedPreferences.getString("id_user", "");
    }

    // Setup RecyclerView untuk menampilkan data stok dalam bentuk horizontal
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        stockList = new ArrayList<>();
        adapter = new StockKategoriAdapter(this, stockList);
        recyclerView.setAdapter(adapter);
    }

    // Lakukan permintaan API untuk mendapatkan data stok terbaru
    private void fetchLatestStock() {
        UserServices apiService = ApiClient.getUserServices(); // Mendapatkan instance dari UserServices dari ApiClient

        // Gunakan token pengguna yang telah diambil
        String token = "Bearer " + userToken;

        // Ambil namaKategori dari Intent
        String namaKategori = getIntent().getStringExtra("idKategori");

        Call<StockKategoriResponse> call = apiService.getStockByCategory(token, namaKategori); // Gunakan token tanpa "Bearer"

        call.enqueue(new Callback<StockKategoriResponse>() {
            @Override
            public void onResponse(@NonNull Call<StockKategoriResponse> call, @NonNull Response<StockKategoriResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    StockKategoriResponse stockResponse = response.body();
                    if (stockResponse.getStatus() == 200) {
                        List<StockKategori> fetchedStockList = stockResponse.getData();
                        stockList.addAll(fetchedStockList);
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(StockByKategori.this, "Failed to fetch data: " + stockResponse.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(StockByKategori.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<StockKategoriResponse> call, @NonNull Throwable t) {
                Toast.makeText(StockByKategori.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // hitung jumlah harga otomatis
    public void calculateTotal() {
        int totalItems = 0;
        int totalPrice = 0;
        String namaBarang = "";

        // Iterasi melalui list item pada adapter untuk menghitung total item dan harga
        for (StockKategori item : stockList) {
            int quantity = item.getSelectedQuantity();
            //Log.d("CalculateTotal", "Item: " + item.getNamaBarang() + ", Quantity: " + quantity);

            if (quantity > 0) {
                totalItems += quantity;
                totalPrice += (item.getHargaBarang() * quantity);
                namaBarang = item.getNamaBarang();
                items = totalItems;
                totalHarga = totalPrice;
                stockID = item.getId();
            }
        }

        //Log.d("CalculateTotal", "Total Items: " + totalItems);
        //Log.d("CalculateTotal", "Total Price: " + totalPrice);

        // Set text pada TextView tvJumlahPorsi dan tvTotalPrice
        tvJumlahPorsi.setText(totalItems + " items");
        tvTotalPrice.setText("Rp " + totalPrice);
        tvNamaBarang.setText(namaBarang);
    }

}