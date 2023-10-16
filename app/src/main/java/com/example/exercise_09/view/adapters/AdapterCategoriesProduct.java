package com.example.exercise_09.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exercise_09.R;
import com.example.exercise_09.model.objects.Product;

import java.util.List;

public class AdapterCategoriesProduct extends RecyclerView.Adapter<AdapterCategoriesProduct.ViewHolder> {

    private Context context;

    private List<Product> productList;

    public AdapterCategoriesProduct(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public AdapterCategoriesProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategoriesProduct.ViewHolder holder, int position) {
        Product product = productList.get(position);

        Glide.with(context).load(product.getThumbnail()).into(holder.imageView);
        holder.textView.setText(product.getBrand());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgCategories);
            textView = itemView.findViewById(R.id.tvCategories);


        }
    }
}
