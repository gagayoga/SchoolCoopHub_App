package com.schoolcoophub.lib.data.stock.adapter;

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
import com.schoolcoophub.lib.data.stock.model.LaporanPendapatan;

import java.util.List;

public class LaporanPenjualanAdapter extends RecyclerView.Adapter<LaporanPenjualanAdapter.StockViewHolder> {

    private Context context;
    private List<LaporanPendapatan> stockList;

    public LaporanPenjualanAdapter(Context context, List<LaporanPendapatan> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.laporan_card, parent, false);
        return new LaporanPenjualanAdapter.StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        LaporanPendapatan stock = stockList.get(position);
        holder.bind(stock);

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        private TextView txtBulan, txtPendapatan, txtTotalBarang, txtBarangLaris;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            txtBulan = itemView.findViewById(R.id.txtBulan);
            txtPendapatan = itemView.findViewById(R.id.txtPendapatan);
            txtTotalBarang = itemView.findViewById(R.id.txtTotalBarang);
            txtBarangLaris = itemView.findViewById(R.id.txtBarangLaris);
        }

        public void bind(LaporanPendapatan stock) {
            // Mendapatkan nama produk
            String bulan = stock.getBulan();
            String pendapatan = stock.getTotalPendapatan();
            String totalBarang = stock.getTotalBarang();
            String barangTerlaris = stock.getProdukTerlaris();

            txtBulan.setText("Pendapatan Bulan " + bulan);
            txtPendapatan.setText("Rp." + pendapatan);
            txtTotalBarang.setText("Total Barang Terjual : " + totalBarang + " Barang");
            txtBarangLaris.setText("Barang Terlaris Terjual : " + barangTerlaris);
        }
    }
}

