package com.example.exercise_09.api;

import com.example.exercise_09.model.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("products")
    Call<ProductsResponse> getProduct();


}
