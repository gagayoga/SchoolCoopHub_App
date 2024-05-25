package com.schoolcoophub.authentikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.schoolcoophub.lib.data.stock.view.MainActivity;
import com.schoolcoophub.R;
import com.schoolcoophub.lib.koneksi.ApiClient;
import com.schoolcoophub.lib.request.LoginRequest;
import com.schoolcoophub.lib.response.LoginResponse;
import com.schoolcoophub.lib.response_error.LoginResponseErrors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    TextInputEditText edtEmail, edtPassword;

    MaterialButton btnLogin;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        // Validasi password
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        int maxLength = 15;
        edtPassword.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        // Fungsi button login
        btnLogin = findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateInput();
            }
        });

        // Get data di penyimpanan lokal
        sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }

    // fungsi untuk login menggunakan fungsi POST
    public void isLogin(){
        String emailInput, passwordInput;
        emailInput = edtEmail.getText().toString().trim();
        passwordInput = edtPassword.getText().toString().trim();

        LoginRequest loginrequest = new LoginRequest();
        loginrequest.setEmail(emailInput);
        loginrequest.setPassword(passwordInput);

        retrofit2.Call<LoginResponse> loginResponseCall = ApiClient.getUserServices().userLogin(loginrequest);

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    LoginResponse loginResponse = response.body();

                    String username = loginResponse.getData().getName();
                    int idUser = loginResponse.getData().getId();
                    String token = loginResponse.getData().getToken();

                    // Menyimpan token ke SharedPreferences
                    saveUserToken(token, idUser);

                    String role = loginResponse.getData().getRole();

//                    Toast.makeText(LoginPage.this, "Login Succesfully, Selamat Datang Kembali " + username, Toast.LENGTH_SHORT).show();
//                    Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intentHome);
//                    finish();

                    if(role == "Administrator"){
                        Toast.makeText(LoginPage.this, "Login Succesfully, Selamat Datang Kembali " + username, Toast.LENGTH_SHORT).show();
                        Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentHome);
                        finish();
                    }else{
                        Toast.makeText(LoginPage.this, "Login Failed,Akses masuk ditolak ", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Gson gson = new Gson();
                    try {
                        // Kode untuk mengonversi response errorBody() ke objek LoginResponseErrors
                        LoginResponseErrors errorsResponse = gson.fromJson(response.errorBody().charStream(), LoginResponseErrors.class);

                        String errorMessage = errorsResponse.getMessage(); // Ambil pesan kesalahan dari respons

                        // Tampilkan pesan kesalahan ke pengguna menggunakan Toast
                        Toast.makeText(LoginPage.this, errorMessage, Toast.LENGTH_SHORT).show();

                        // Fokuskan kembali ke field email jika diperlukan
                        if (errorMessage != null && errorMessage.contains("Email")) {
                            edtEmail.requestFocus(); // Fokuskan kembali ke field email jika pesan berisi "Email"
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Tangani kesalahan saat membaca errorBody()
                        Toast.makeText(LoginPage.this, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginPage.this, "Login failed, please cek koneksi internet Anda.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Fungsi validasi inputan user
    private void ValidateInput(){
        String emailInput, passwordInput;
        emailInput = edtEmail.getText().toString().trim();
        passwordInput = edtPassword.getText().toString().trim();

        // validasi inputan kosong
        if(TextUtils.isEmpty(emailInput)){
            edtEmail.setError("Email wajib di isi!");
            edtEmail.setFocusable(true);
            return;
        } else if(TextUtils.isEmpty(passwordInput)){
            edtPassword.setError("Password wajib di isi!");
            edtPassword.setFocusable(true);
            return;
        }else if (!isValidEmail(emailInput)) {
            edtEmail.setError("Email harus berakhir dengan @gmail.com!");
            edtEmail.setFocusable(true);
            return;
        }
        else{
            isLogin();
        }
    }

    // Fungsi untuk memeriksa validitas email dengan ekspresi reguler
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_]+@gmail\\.com$";
        return email.matches(emailPattern);
    }

    // Metode untuk menyimpan token pengguna ke SharedPreferences
    private void saveUserToken(String token, int idUser) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_token", token);
        editor.putString("id_user", Integer.toString(idUser));
        editor.apply();
    }
}