package com.example.jiemian.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder()
                .setLenient()  // 设置Gson为宽松模式
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/") // 使用模拟器的特殊IP地址
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(new OkHttpClient.Builder().build())
                .build();
    }
}
