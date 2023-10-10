package com.example.exercise_09.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.exercise_09.ItemClickListener;
import com.example.exercise_09.R;
import com.example.exercise_09.model.Product;

import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private Context context;
    private List<Product> productList;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public AdapterProduct(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProduct.ViewHolder holder, int position) {
        Product product = productList.get(position);
        if (product == null) {
            return;
        }

        Glide.with(holder.itemView.getContext())
                .load(product.getThumbnail())
                .into(holder.imgProduct);
        holder.tvTitle.setText(product.getTitle());
        holder.tvPrice.setText("$" + product.getPrice());
        holder.tvRate.setText(String.valueOf(product.getRating()));


        holder.cardView.setOnClickListener(v -> {
            itemClickListener.onItemClick(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct;
        private TextView tvTitle;

        private TextView tvPrice;
        private CardView cardView;

        private TextView tvRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRate = itemView.findViewById(R.id.tvRate);

            cardView = itemView.findViewById(R.id.myCard);


        }
    }
}
