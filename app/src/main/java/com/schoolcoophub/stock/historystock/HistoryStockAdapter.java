package com.schoolcoophub.stock.historystock;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.schoolcoophub.HistoryStock;
import com.schoolcoophub.R;
import com.schoolcoophub.StockByKategori;
import com.schoolcoophub.stock.stockbykategori.StockKategoriAdapter;

import java.util.List;


public class HistoryStockAdapter extends RecyclerView.Adapter<HistoryStockAdapter.StockViewHolder>{

    private Context context;
    private List<HistoryStocks> stockList;

    public HistoryStockAdapter(Context context, List<HistoryStocks> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public HistoryStockAdapter.StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_card, parent, false);
        return new HistoryStockAdapter.StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryStockAdapter.StockViewHolder holder, int position) {
        HistoryStocks item = stockList.get(position);

        // Ambil Data
        String productName = item.getNamaBarang();
        String status = item.getStatus();
        String tanggal = item.getTanggalPesan();
        int productHarga = item.getTotalHarga();
        int jumlahBarang = item.getJumlahBarang();

        holder.namaBarangTextView.setText(productName);
        holder.jumlahBarangTextView.setText(Integer.toString(jumlahBarang) + " Items");
        holder.hargaBarangTextView.setText("Total Harga : Rp " + Integer.toString(productHarga));
        holder.statuseTextView.setText("Status : " + status);
        holder.dateTextView.setText("Tanggal Pesan : " + tanggal);

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detemail = item.getEmail();
                String detname = item.getName();
                String detnamaBarang = item.getNamaBarang();
                String detstatus = item.getStatus();
                String dettanggalPesan = item.getTanggalPesan();
                int dethargaBarang = item.getHargaBarang();
                int detjumlahBarang = item.getJumlahBarang();
                int dettotalHarga = item.getTotalHarga();
                //String status = responseKeluar.getData().getS();

                // Membuat pesan untuk ditampilkan dalam alert
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Detail Transaksi\n\n");
                messageBuilder.append("Username: ").append(detname).append("\n");
                messageBuilder.append("Email: ").append(detemail).append("\n");
                messageBuilder.append("Nama Barang: ").append(detnamaBarang).append("\n");
                messageBuilder.append("Harga Barang: Rp ").append(dethargaBarang).append("\n");
                messageBuilder.append("Jumlah Barang: ").append(detjumlahBarang).append("\n");
                messageBuilder.append("Total Harga: Rp ").append(dettotalHarga).append("\n");
                messageBuilder.append("Tanggal Pesan: ").append(dettanggalPesan).append("\n");
                messageBuilder.append("Status : ").append(detstatus).append("\n");

                // Menampilkan pesan dalam alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Transaksi Berhasil");
                builder.setMessage(messageBuilder.toString());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        TextView namaBarangTextView, hargaBarangTextView, jumlahBarangTextView, statuseTextView, dateTextView;
        MaterialButton btnDetail;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            namaBarangTextView = itemView.findViewById(R.id.tvNama);
            hargaBarangTextView = itemView.findViewById(R.id.tvPrice);
            jumlahBarangTextView = itemView.findViewById(R.id.tvJml);
            statuseTextView = itemView.findViewById(R.id.tvStatus);
            dateTextView = itemView.findViewById(R.id.tvDate);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }
}
