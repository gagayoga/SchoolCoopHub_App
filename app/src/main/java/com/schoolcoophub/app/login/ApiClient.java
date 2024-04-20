package com.schoolcoophub.app.login;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS);

        OkHttpClient client = builder.build();


        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl("https://apistemdu.000webhostapp.com/api/")
                .baseUrl("http://192.168.43.21:8000/api/users/")
                //.baseUrl("http://192.168.4.105:8080/api/")
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static UserServices  getUserServices(){
        UserServices userServices = getRetrofit().create(UserServices.class);

        return userServices;
    }
}
