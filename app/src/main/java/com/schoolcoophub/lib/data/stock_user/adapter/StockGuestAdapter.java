package com.schoolcoophub.lib.data.stock_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolcoophub.R;
import com.schoolcoophub.lib.data.stock.model.Stock;

import java.util.List;

public class StockGuestAdapter extends RecyclerView.Adapter<StockGuestAdapter.StockViewHolder> {

    private Context context;
    private List<Stock> stockList;

    public StockGuestAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.stock_guest_card, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stock = stockList.get(position);
        holder.bind(stock);

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView imageView;
        private TextView nama, harga, jumlah, labelKosong;
        private View overlay;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            nama = itemView.findViewById(R.id.stockNameTextView);
            harga = itemView.findViewById(R.id.stockPriceTextView);
            jumlah = itemView.findViewById(R.id.stockQuantityTextView);
            labelKosong = itemView.findViewById(R.id.labelKosong);
            overlay = itemView.findViewById(R.id.overlay);
        }

        public void bind(Stock stock) {
            // Mendapatkan nama produk
            String productName = stock.getNamaBarang();
            int productHarga = stock.getHargaBarang();
            int jumlahBarang = stock.getJumlahBarang();

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

            // Set size image

            // Tampilkan atau sembunyikan label "Kosong" berdasarkan jumlah barang
            if (stock.getJumlahBarang() == 0) {
                labelKosong.setVisibility(View.VISIBLE);
                overlay.setVisibility(View.VISIBLE);
            } else {
                labelKosong.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
            }

            nama.setText(productName);
            harga.setText("Rp." + String.valueOf(productHarga));
            jumlah.setText("Stock : " + String.valueOf(jumlahBarang));
        }
    }
}
