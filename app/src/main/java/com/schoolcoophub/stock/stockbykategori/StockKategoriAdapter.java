package com.schoolcoophub.stock.stockbykategori;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolcoophub.R;
import com.schoolcoophub.StockByKategori;

import java.util.List;

public class StockKategoriAdapter extends RecyclerView.Adapter<StockKategoriAdapter.StockViewHolder> {

    private Context context;
    private List<StockKategori> stockList;

    public StockKategoriAdapter(Context context, List<StockKategori> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_card, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        StockKategori item = stockList.get(position);

        // Ambil Data
        String productName = item.getNamaBarang();
        int productHarga = item.getHargaBarang();

        // Mengonversi nama produk menjadi nama file gambar (asumsikan tanpa spasi dan huruf kecil)
        String imageName = productName.replaceAll("\\s+", "").toLowerCase();

        // Mendapatkan ID gambar dari drawable
        int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

        // Set image resource to ImageView
        if (imageResId != 0) {
            holder.imageView.setImageResource(imageResId);
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder_image); // Jika tidak ditemukan, gunakan placeholder
        }

        holder.namaBarangTextView.setText(item.getNamaBarang());
        holder.hargaBarangTextView.setText("Harga: Rp. " + item.getHargaBarang());
        holder.jumlahBarangTextView.setText("Jumlah: " + item.getJumlahBarang());

        holder.imageAdd1.setOnClickListener(v -> {
            int currentQuantity = item.getSelectedQuantity();
            int availableStock = item.getJumlahBarang();

            if (currentQuantity < availableStock) {
                // Jika kuantitas yang dipilih belum mencapai stok yang tersedia, tambahkan kuantitas
                item.setSelectedQuantity(currentQuantity + 1);
                notifyItemChanged(position); // Memperbarui tampilan item yang diubah

                // Memanggil calculateTotal di StockByKategori setelah perubahan
                if (context instanceof StockByKategori) {
                    ((StockByKategori) context).calculateTotal();
                }
            } else {
                // Jika kuantitas yang dipilih sudah mencapai stok yang tersedia, beri peringatan atau tindakan lainnya
                Toast.makeText(context, "Stok tidak mencukupi", Toast.LENGTH_SHORT).show();
            }
        });

        holder.imageMinus1.setOnClickListener(v -> {
            int currentQuantity = item.getSelectedQuantity();
            if (currentQuantity > 0) {
                item.setSelectedQuantity(currentQuantity - 1);
                notifyItemChanged(position); // Memperbarui tampilan item yang diubah

                // Memanggil calculateTotal di StockByKategori setelah perubahan
                if (context instanceof StockByKategori) {
                    ((StockByKategori) context).calculateTotal();
                }
            }
        });

        // Mengatur warna latar belakang berdasarkan kuantitas yang dipilih
        if (item.getSelectedQuantity() > 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected));
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.unselected));
        }

        // Mengatur nilai tvPaket1
        holder.tvPaket1.setText(String.valueOf(item.getSelectedQuantity()));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        TextView namaBarangTextView, hargaBarangTextView, jumlahBarangTextView, tvPaket1;
        ImageView imageView, imageAdd1, imageMinus1;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarangTextView = itemView.findViewById(R.id.stockNameTextView);
            hargaBarangTextView = itemView.findViewById(R.id.stockPriceTextView);
            jumlahBarangTextView = itemView.findViewById(R.id.stockQuantityTextView);
            tvPaket1 = itemView.findViewById(R.id.tvPaket1);
            imageView = itemView.findViewById(R.id.imageView);
            imageAdd1 = itemView.findViewById(R.id.imageAdd1);
            imageMinus1 = itemView.findViewById(R.id.imageMinus1);
        }
    }
}
