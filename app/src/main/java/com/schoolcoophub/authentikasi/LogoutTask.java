package com.schoolcoophub.authentikasi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogoutTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "LogoutTask";

    //private static final String API_URL = "https://api-schoolcoophub-24.000webhostapp.com/api/users/logout";
    private static final String API_URL = "http://192.168.81.229:8000/api/users/logout";

    private String AUTH_TOKEN;

    private final Context context;

    public LogoutTask(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String userToken = sharedPreferences.getString("user_token", "");

        AUTH_TOKEN = userToken;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL)
                .post(RequestBody.create(null, new byte[0])) // Gunakan metode POST kosong
                .header("Authorization", "Bearer " + AUTH_TOKEN)
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                // Hapus semua data dari SharedPreferences setelah logout berhasil
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Menghapus semua data
                editor.apply();

                return true; // Logout berhasil
            } else {
                Log.e(TAG, "Error: " + response.code() + " - " + response.message());
                return false; // Gagal melakukan logout
            }
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage(), e);
            return false; // Terjadi kesalahan saat melakukan request
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Logout Berhasil, Silakan Anda login dahulu", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, SplashScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // Clear task stack
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Logout Gagal, Silakan Anda ulangi lagi", Toast.LENGTH_SHORT).show();
        }
    }
}
