package com.schoolcoophub.stock.stockterbaru;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.schoolcoophub.R;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private Context context;
    private List<Stock> stockList;

    public StockAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_stock, parent, false);
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
        private TextView nama, harga;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imageView = itemView.findViewById(R.id.imageView);
            nama = itemView.findViewById(R.id.namaBarang);
            harga = itemView.findViewById(R.id.hargaBarang);
        }

        public void bind(Stock stock) {
            // Mendapatkan nama produk
            String productName = stock.getNamaBarang();
            int productHarga = stock.getHargaBarang();

            // Mengonversi nama produk menjadi nama file gambar (asumsikan tanpa spasi dan huruf kecil)
            String imageName = productName.replaceAll("\\s+", "").toLowerCase();

            // Mendapatkan ID gambar dari drawable
            int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());

            // Set image resource to ImageView
            if (imageResId != 0) {
                imageView.setImageResource(imageResId);
            } else {
                imageView.setImageResource(R.drawable.placeholder_image); // Jika tidak ditemukan, gunakan placeholder
            }

            nama.setText(productName);
            harga.setText("Rp." + String.valueOf(productHarga));

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle card view click event
                    // Example: Open detail activity for this stock item
                }
            });
        }
    }
}
