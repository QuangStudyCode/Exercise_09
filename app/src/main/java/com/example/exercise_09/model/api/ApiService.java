package com.example.exercise_09.model.api;

import com.example.exercise_09.model.objects.ProductsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("products?limit=80")
    Call<ProductsResponse> getProduct();

}
