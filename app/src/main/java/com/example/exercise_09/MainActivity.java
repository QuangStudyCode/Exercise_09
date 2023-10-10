package com.example.exercise_09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.exercise_09.api.ApiService;
import com.example.exercise_09.model.ProductsResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ProductsResponse productsResponse;
    private ImageSlider imageSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        switchImg();

    }

    private void switchImg() {
        imageSlider = findViewById(R.id.imgSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }


    private void initView() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);


//        Bundle bundle = new Bundle();
//        bundle.putString("myKey", "NguyenBaQuang");
//
//        navController.navigate(R.id.action_homeFragment_to_detailsProductFragment);

    }
}