package com.schoolcoophub.authentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.schoolcoophub.R;
import com.schoolcoophub.lib.data.stock.view.MainActivity;
import com.schoolcoophub.lib.data.stock_user.view.GuestPage;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;

    private SharedPreferences sharedPreferences;

    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottie = findViewById(R.id.lottieSplash);

        // Get data user dari penyimpanan lokal
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, 5000);
    }

    // Validasi user login
    private void checkUser(){
        if (userToken.isEmpty()) {
            Intent intent = new Intent(SplashScreen.this, GuestPage.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SplashScreen.this, "Selamat Datang Kembali, Anda sudah login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // Fungsi get data token user
    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}