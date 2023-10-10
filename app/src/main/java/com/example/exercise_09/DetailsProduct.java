package com.example.exercise_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exercise_09.model.Product;
import com.google.gson.Gson;

public class DetailsProduct extends AppCompatActivity {

    private ImageView imageView;
    private TextView TitleProductDetails;

    private TextView descriptionProductDetails;

    private TextView detailsProduct;


    private TextView priceProductDetails;

    private ImageView backToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_product);

        imageView = findViewById(R.id.imgProductDetails);
        TitleProductDetails = findViewById(R.id.TitleProductDetails);
        descriptionProductDetails = findViewById(R.id.descriptionProductDetails);
        detailsProduct = findViewById(R.id.detailsProduct);
        priceProductDetails = findViewById(R.id.priceProductDetails);
        backToMain = findViewById(R.id.backToMain);

        backToMain.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            String jsonProduct = intent.getStringExtra("product");
            Gson gson = new Gson();
            Product product = gson.fromJson(jsonProduct, Product.class);


//          bind data
            Glide.with(DetailsProduct.this).load(product.getThumbnail()).into(imageView);
            TitleProductDetails.setText(product.getTitle());
            descriptionProductDetails.setText(product.getDescription());
            priceProductDetails.setText("$"+product.getPrice());
            Log.d("TAG", "onCreate: " + product.getBrand());
        }
    }
}