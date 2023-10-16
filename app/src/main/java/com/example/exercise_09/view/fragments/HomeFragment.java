package com.example.exercise_09.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.exercise_09.DetailsProduct;
import com.example.exercise_09.IItemClickSave;
import com.example.exercise_09.ISaveView;
import com.example.exercise_09.ItemClickListener;
import com.example.exercise_09.R;
import com.example.exercise_09.model.DBhelper;
import com.example.exercise_09.model.network.RetrofitClient;
import com.example.exercise_09.presenters.SavePresenter;
import com.example.exercise_09.view.adapters.AdapterCategoriesProduct;
import com.example.exercise_09.view.adapters.AdapterProduct;
import com.example.exercise_09.model.api.ApiService;
import com.example.exercise_09.model.objects.Product;
import com.example.exercise_09.model.objects.ProductsResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ItemClickListener, IItemClickSave, ISaveView {

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
    public void onResume() {
        super.onResume();
        updateAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcFragmentHome);
        rcFragmentHomeMostPopular = view.findViewById(R.id.rcFragmentHomeMostPopular);
        rcFragmentIphone = view.findViewById(R.id.rcFragmentIphone);
        rcCategogies = view.findViewById(R.id.rcFragmentHomeCategories);
        rcFragmentIphone = view.findViewById(R.id.rcFragmentIphone);
        imageSlider = view.findViewById(R.id.imgSlider);
        dBhelper = new DBhelper(getActivity());
//        dBhelper.deleteAllProducts();
        savePresenter = new SavePresenter(this);

        callApi();
        switchImg();
    }

    ProductsResponse productsResponse;

    //
    private AdapterProduct adapterProduct;

    private AdapterCategoriesProduct adapterCategoriesProduct;
    public List<Product> productList;


    //    list divide
    public List<Product> productListHotdeal;
    public List<Product> productListMostPopular;
    public List<Product> productListMostIphone;

    public List<Product> productListCategories;

    private RecyclerView recyclerView, rcFragmentHomeMostPopular, rcFragmentIphone, rcCategogies;

    private ImageSlider imageSlider;

    private SavePresenter savePresenter;

    private DBhelper dBhelper;


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
                            for (Product product : productsResponse.getProducts()) {
                                Log.d("TAG", "onResponse: " + product);
                                dBhelper.addProductFromAPI(product);
                            }
                        }
                        dataDivisionFromDB();
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

    private void dataDivisionFromDB() {
        List<Product> productListFromDb = new ArrayList<>();

        productListFromDb = dBhelper.getAllProduct();

        productListHotdeal = new ArrayList<>();

//        hot deal
        productListHotdeal = productListFromDb.stream()
                .filter(product -> product.getDiscountPercentage() > 15.0)
                .collect(Collectors.toList());
        initView(recyclerView, productListHotdeal);

    }

    private void dataDivision(List<Product> productList) {
        productListHotdeal = new ArrayList<>();
//      stream for Hot Deal

        productListHotdeal = productList.stream()
                .filter(product -> product.getDiscountPercentage() > 15.0)
                .collect(Collectors.toList());
        initView(recyclerView, productListHotdeal);

//        stream for Most Popular
        productListMostPopular = productList.stream()
                .filter(product -> product.getRating() > 4.5)
                .collect(Collectors.toList());
        initViewForListMostPopular(rcFragmentHomeMostPopular, productListMostPopular);

//       steam for Categories(lọc các loại sản phẩm)
//        List<String> sortedBrands = uniqueBrands.stream()
//                .sorted()
//                .collect(Collectors.toList());

        initViewForCategories(rcCategogies, productList);
//        Log.d("TAG", "dataDivision: "+sortedBrands);

//      stream for iphone
        productListMostIphone = productList.stream()
                .filter(product -> product.getBrand().equals("Apple"))
                .collect(Collectors.toList());
        initViewForIphone(rcFragmentIphone, productListMostIphone);
    }

    private void initViewForIphone(RecyclerView rcFragmentIphone, List<Product> productListMostIphone) {
        if (productListMostIphone != null && !productListMostIphone.isEmpty()) {
            adapterProduct = new AdapterProduct(getContext(), productListMostIphone);
            adapterProduct.setItemClickListener(this);
            adapterProduct.setItemClickSave(this);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
            rcFragmentIphone.setLayoutManager(gridLayoutManager);
            rcFragmentIphone.setAdapter(adapterProduct);
        }
    }

    private void initViewForCategories(RecyclerView rcCategogies, List<Product> productList) {
        if (productList != null && !productList.isEmpty()) {
            adapterCategoriesProduct = new AdapterCategoriesProduct(getContext(), productList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rcCategogies.setLayoutManager(linearLayoutManager);
            rcCategogies.setAdapter(adapterCategoriesProduct);
        }
    }

    private void initViewForListMostPopular(RecyclerView rcFragmentHomeMostPopular, List<Product> productListMostPopular) {
        if (productListMostPopular != null && !productListMostPopular.isEmpty()) {
            adapterProduct = new AdapterProduct(getContext(), productListMostPopular);
            adapterProduct.setItemClickListener(this);
            adapterProduct.setItemClickSave(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rcFragmentHomeMostPopular.setLayoutManager(linearLayoutManager);
            rcFragmentHomeMostPopular.setAdapter(adapterProduct);
        }
    }

    private void initView(RecyclerView recyclerView, List<Product> productListHotdeal) {
        if (productListHotdeal != null && !productListHotdeal.isEmpty()) {
            adapterProduct = new AdapterProduct(getContext(), productListHotdeal);
            adapterProduct.setItemClickListener(this);
            adapterProduct.setItemClickSave(this);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapterProduct);
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

    private void switchImg() {
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.img_slider, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    @Override
    public void onSuccess(Product product) {
        dBhelper.updateCheckLikeProduct(product.getId());
        checkProductStatus(product);
//        Log.d("TAG", "onSuccess: " + product.getCheck_like());
    }

    @Override
    public void onFail(Product product) {
        dBhelper.deleteProduct(product.getId());
    }

    @Override
    public void onSaveClick(Product product) {
        savePresenter.clickSave(product);
    }

    private void checkProductStatus(Product product) {
        int checkLike = dBhelper.getCheckLikeProduct(product.getId());
        Log.d("TAG", "checkProductStatus: " + checkLike);
        product.setCheck_like(checkLike);
        adapterProduct.notifyDataSetChanged();
    }

    private void updateAdapter() {
        // Update the adapter with the latest data
        dataDivisionFromDB();
        if (adapterProduct != null) {
            adapterProduct.notifyDataSetChanged();
        }
    }
}