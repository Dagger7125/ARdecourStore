package com.unity.mynativeapp.adapter;


        import android.content.Context;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.FirebaseDatabase;
        import com.squareup.picasso.Picasso;
        import com.unity.mynativeapp.Productdetails;
        import com.unity.mynativeapp.R;
        import com.unity.mynativeapp.model.Cart;
        import org.jetbrains.annotations.NotNull;


public class CartAdapter extends FirebaseRecyclerAdapter<Cart,CartAdapter.ProductViewHolder>{

    Context context;
    String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid(), sku;

    public CartAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Cart> options, Context context) {
        super(options);
        this.context = context;
    }

    public CartAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Cart> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int i, @NonNull @NotNull Cart products) {
        holder.prodName.setText(products.getProductName());
        holder.prodPrice.setText(products.getProductPrice().toString());
        holder.prodQty.setText(products.getProductQty().toString());
        Picasso.with(context).load(products.getImageUrl()).fit().centerCrop().into(holder.prodImage);
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sku = products.getSku().toString();
                FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser).child(sku).removeValue();
                CartAdapter.super.notifyDataSetChanged();
                Toast.makeText(v.getContext(), "Item Removed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_row_item, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView prodImage, delBtn;
        TextView prodName, prodPrice, prodQty;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImage = itemView.findViewById(R.id.cart_prod_img);
            prodQty = itemView.findViewById(R.id.cart_prod_qty);
            prodName = itemView.findViewById(R.id.cart_prod_name);
            prodPrice = itemView.findViewById(R.id.cart_prod_price);
            delBtn = itemView.findViewById(R.id.cart_prod_del);
        }
    }
}

