package com.example.exercise_09.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.exercise_09.DetailsProduct;
import com.example.exercise_09.ItemClickListener;
import com.example.exercise_09.model.DBhelper;
import com.example.exercise_09.R;
import com.example.exercise_09.view.adapters.AdapterProduct;
import com.example.exercise_09.model.objects.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WishListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishListFragment extends Fragment implements ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WishListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WishListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishListFragment newInstance(String param1, String param2) {
        WishListFragment fragment = new WishListFragment();
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
        return inflater.inflate(R.layout.fragment_wish_list, container, false);
    }

    private RecyclerView recyclerView;
    private DBhelper dBhelper;

    private List<Product> productList;

    private AdapterProduct adapterProduct;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcWishList);
        intitData();
        initView();
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

    private void intitData() {
        dBhelper = new DBhelper(getContext());
        productList = new ArrayList<>();
        productList = dBhelper.getProductByChecked();

        for (Product product : productList) {
            Log.d("TAG", "intitData: " + product.getId() + "/" + product.getCheck_like());
        }
    }

    @Override
    public void onItemClick(Product product) {
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        String productJson = gson.toJson(product);
        Log.d("TAG", "onItemClick: " + productJson);

        Intent intent = new Intent(getContext(), DetailsProduct.class);
        intent.putExtra("product", productJson);
        startActivity(intent);
    }
}