package com.schoolcoophub.lib.data.stock.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.schoolcoophub.R;
import com.schoolcoophub.lib.data.stock.interfacestock.StockUpdateListener;
import com.schoolcoophub.lib.data.stock.model.StockKategori;
import com.schoolcoophub.lib.data.stock.model.StockKosong;
import com.schoolcoophub.lib.data.stock.model.StockKosong;
import com.schoolcoophub.lib.data.stock.response.StockKeluarResponse;
import com.schoolcoophub.lib.data.stock.response.UpdateStockResponse;
import com.schoolcoophub.lib.data.stock.view.StockByKategori;
import com.schoolcoophub.lib.koneksi.ApiClient;
import com.schoolcoophub.lib.koneksi.UserServices;
import com.schoolcoophub.lib.request.StockKeluarRequest;
import com.schoolcoophub.lib.request.UpdateStockRequest;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockKosongAdapter extends RecyclerView.Adapter<StockKosongAdapter.StockKeluarViewHolder> {

    private Context context;
    private List<StockKosong> stockList;
    private UserServices userServices;
    private String userToken;
    private SharedPreferences sharedPreferences;
    private StockUpdateListener stockUpdateListener;

    public StockKosongAdapter(Context context, List<StockKosong> stockList, StockUpdateListener stockUpdateListener) {
        this.context = context;
        this.stockList = stockList;
        this.stockUpdateListener = stockUpdateListener;
        userServices = ApiClient.getUserServices();

        // Token
        sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();
    }

    @NonNull
    @Override
    public StockKeluarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_kosong, parent, false);
        return new StockKeluarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockKeluarViewHolder holder, int position) {
        StockKosong stock = stockList.get(position);
        holder.bind(stock);
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    public class StockKeluarViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imageView;
        private TextView judulProduct, harga;
        private MaterialButton buttonUpdateStock;

        public StockKeluarViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            judulProduct = itemView.findViewById(R.id.judulProduct);
            buttonUpdateStock = itemView.findViewById(R.id.buttonUpdateStock);

            buttonUpdateStock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showUpdateDialog(getAdapterPosition());
                }
            });
        }

        public void bind(StockKosong stock) {
            // Mendapatkan nama produk
            String productName = stock.getNamaBarang();

            // Mengonversi nama produk menjadi nama file gambar (asumsikan tanpa spasi dan huruf kecil)
            String imageName = productName.replaceAll("\\s+", "").toLowerCase();

            // Mendapatkan ID gambar dari drawable
            int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

            // Set image resource to ImageView
            if (imageResId != 0) {
                imageView.setImageResource(imageResId);
            } else {
                imageView.setImageResource(R.drawable.placeholder); // Jika tidak ditemukan, gunakan placeholder
            }

            judulProduct.setText(productName);
        }
    }

    private void showUpdateDialog(int position) {
        StockKosong stock = stockList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_stock, null);
        builder.setView(view);

        EditText editJumlahBarang = view.findViewById(R.id.editJumlahBarang);
        MaterialButton buttonSubmit = view.findViewById(R.id.buttonSubmit);

        AlertDialog alertDialog = builder.create();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jumlahBarang = Integer.parseInt(editJumlahBarang.getText().toString().trim());
                updateStock(stock.getNamaBarang(), stock.getId(), jumlahBarang);
                alertDialog.dismiss();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void updateStock(String namaBarang, int id, int jumlahBarang) {
        // Menyiapkan token dengan prefix "Bearer "
        String token = "Bearer " + userToken;

        // Membuat request body
        UpdateStockRequest updateStockRequest = new UpdateStockRequest(jumlahBarang);

        // Membuat call untuk API
        Call<UpdateStockResponse> updateStockResponse = ApiClient.getUserServices().updateStock(token, updateStockRequest, id);

        // Melakukan enqueue untuk call tersebut
        updateStockResponse.enqueue(new Callback<UpdateStockResponse>() {
            @Override
            public void onResponse(Call<UpdateStockResponse> call, Response<UpdateStockResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdateStockResponse stockResponse = response.body();

                    if (stockResponse.getStatus() == 200) {
                        // Membuat pesan untuk ditampilkan dalam alert dialog
                        StringBuilder messageBuilder = new StringBuilder();
                        messageBuilder.append("Stok berhasil diperbarui\n\n");
                        messageBuilder.append("Nama Barang: ").append(namaBarang).append("\n");
                        messageBuilder.append("Jumlah Barang: ").append(jumlahBarang).append("\n");

                        // Menampilkan pesan dalam alert dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Update Berhasil");
                        builder.setMessage(messageBuilder.toString());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                stockUpdateListener.onStockUpdated();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.setCancelable(false);
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                    } else {
                        Toast.makeText(context, "Gagal memperbarui stok", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateStockResponse> call, Throwable t) {
                Toast.makeText(context, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error","Terjadi kesalahan: " + t.getMessage());
            }
        });
    }
}
