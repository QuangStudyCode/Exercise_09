package com.example.exercise_09.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.exercise_09.R;
import com.example.exercise_09.model.Product;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsProductFragment newInstance(String param1, String param2) {
        DetailsProductFragment fragment = new DetailsProductFragment();
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

    private ImageView imageView;
    private TextView TitleProductDetails;

    private TextView descriptionProductDetails;

    private TextView detailsProduct;


    private TextView priceProductDetails;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_details_product, container, false);

        imageView = view.findViewById(R.id.imgProductDetails);
        TitleProductDetails = view.findViewById(R.id.TitleProductDetails);
        descriptionProductDetails = view.findViewById(R.id.descriptionProductDetails);
        detailsProduct = view.findViewById(R.id.detailsProduct);
        priceProductDetails = view.findViewById(R.id.priceProductDetails);


        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("product")) {
            String productJson = bundle.getString("product");
            Gson gson = new Gson();
            Product product = gson.fromJson(productJson, Product.class);
            // Sử dụng đối tượng Product ở đây
            //            glide image
//            Glide.with(getContext()).load(product.getThumbnail()).into(imageView);
//            TitleProductDetails.setText(product.getTitle());
//            descriptionProductDetails.setText(product.getDescription());
//            priceProductDetails.setText(product.getPrice());

        }
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}