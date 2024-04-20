package com.schoolcoophub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;

    private SharedPreferences sharedPreferences;
    private String userToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        lottie = findViewById(R.id.lottieSplash);

        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        userToken = getUserToken();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, 5000);
    }

    private void checkUser(){
        if (userToken.isEmpty()) {
            Intent intent = new Intent(SplashScreen.this, LoginPage.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SplashScreen.this, "Selamat Datang Kembali, Anda sudah login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private String getUserToken() {
        return sharedPreferences.getString("user_token", "");
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}