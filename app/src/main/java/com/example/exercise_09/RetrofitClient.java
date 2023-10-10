package com.example.exercise_09;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
//    retrofit là gì
//    hhtp là gì
    private static final String BASE_URL = "https://dummyjson.com";
    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    public static <T> T create(Class<T> tClass) {
        return getInstance().create(tClass);
    }

}
