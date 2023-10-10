package com.example.exercise_09.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exercise_09.DetailsProduct;
import com.example.exercise_09.ItemClickListener;
import com.example.exercise_09.R;
import com.example.exercise_09.RetrofitClient;
import com.example.exercise_09.adapter.AdapterProduct;
import com.example.exercise_09.api.ApiService;
import com.example.exercise_09.model.Product;
import com.example.exercise_09.model.ProductsResponse;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcFragmentHome);
        callApi();
    }

    ProductsResponse productsResponse;

    //
    private AdapterProduct adapterProduct;

    public List<Product> productList;

    private RecyclerView recyclerView;

    private void callApi() {
        ApiService apiService = RetrofitClient.create(ApiService.class);
        apiService.getProduct().enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        productsResponse = new ProductsResponse();
                        productsResponse = response.body();

                        productList = new ArrayList<>();
                        if (productsResponse != null) {
                            productList.addAll(productsResponse.getProducts());
                            initView();
                        }

                    }
                } else {
                    Log.d("TAG", "onResponse: not");
                }
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: ");
            }
        });
    }

    private void initView() {
        if (productList != null && !productList.isEmpty()) {
            adapterProduct = new AdapterProduct(getContext(), productList);
            adapterProduct.setItemClickListener(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapterProduct);
        }
    }

    @Override
    public void onItemClick(Product product) {
        Log.d("TAG", "onItemClick: " + product.toString());

//        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String productJson = gson.toJson(product);
        Log.d("TAG", "onItemClick: " + productJson);

//        bundle.putString("product",productJson);
//        NavController navController = Navigation.findNavController(requireView());
//        navController.navigate(R.id.action_homeFragment_to_detailsProductFragment, bundle);

        Intent intent = new Intent(getContext(), DetailsProduct.class);
        intent.putExtra("product", productJson);
        startActivity(intent);
    }
}