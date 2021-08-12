package com.unity.mynativeapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.unity.mynativeapp.MainActivity;
import com.unity.mynativeapp.Productdetails;
import com.unity.mynativeapp.R;
import com.unity.mynativeapp.model.Products;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import static androidx.core.content.ContextCompat.startActivity;

public class ProductAdapter extends FirebaseRecyclerAdapter<Products,ProductAdapter.ProductViewHolder>{

    Context context;


    public ProductAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Products> options, Context context) {
        super(options);
        this.context = context;
    }

    public ProductAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Products> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int i, @NonNull @NotNull Products products) {
        holder.prodName.setText(products.getProductName());
        holder.prodPrice.setText(products.getProductPrice().toString());
        Picasso.with(context).load(products.getImageUrl()).fit().centerCrop().into(holder.prodImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Productdetails.class);
                intent.putExtra("sku", products.getSku());
                v.getContext().startActivity(intent);
            }
        });
        holder.wishImage.setTag(R.drawable.ic_heart);
        holder.wishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer resource = (Integer) holder.wishImage.getTag();
                if(resource == R.drawable.ic_heart){
                    holder.wishImage.setImageResource(R.drawable.ic_wished_heart);
                    holder.wishImage.setTag(R.drawable.ic_wished_heart);
                }
                else {
                    holder.wishImage.setImageResource(R.drawable.ic_heart);
                    holder.wishImage.setTag(R.drawable.ic_heart);
                }

            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_row_item, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImage, wishImage;
        TextView prodName, prodPrice;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.prod_image);
            wishImage = itemView.findViewById(R.id.wishImg);
            prodName = itemView.findViewById(R.id.prod_name);
            prodPrice = itemView.findViewById(R.id.prod_price);

        }
    }
}

