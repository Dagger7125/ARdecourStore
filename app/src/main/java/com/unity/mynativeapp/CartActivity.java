package com.unity.mynativeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.unity.mynativeapp.adapter.CartAdapter;
import com.unity.mynativeapp.adapter.ProductAdapter;
import com.unity.mynativeapp.databinding.ActivityCartBinding;
import com.unity.mynativeapp.databinding.ActivityMainBinding;
import com.unity.mynativeapp.model.Cart;
import com.unity.mynativeapp.model.Products;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;
    RecyclerView productRecycler;
    CartAdapter cartAdapter;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        productRecycler = binding.recyclerView;
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        productRecycler.setLayoutManager(layoutManager);

        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Cart").child(currentUser), Cart.class)
                        .build();
        cartAdapter = new CartAdapter(options);
        productRecycler.setAdapter(cartAdapter);
        cartAdapter.startListening();

    }
}