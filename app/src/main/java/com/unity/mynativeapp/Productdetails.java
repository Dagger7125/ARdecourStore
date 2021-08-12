package com.unity.mynativeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.unity.mynativeapp.databinding.ActivityProductdetailsBinding;
import com.unity.mynativeapp.databinding.ActivitySignInBinding;
import com.unity.mynativeapp.login.SignInActivity;
import com.unity.mynativeapp.model.Products;
import com.unity3d.player.UnityPlayer;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.SimpleFormatter;

public class Productdetails extends AppCompatActivity {

    String sku = "", img, currentUser;
    ActivityProductdetailsBinding binding;
    int maxQuantity= 1;
    int qty;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        sku = getIntent().getStringExtra("sku");
        getProductinfo(sku);

        //for integer int x = Integer.parseInt(getIntent().getStringExtra(""));
        binding.arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Productdetails.this, MainUnityActivity.class);
                intent.putExtra("name", sku);
                startActivity(intent);
            }
        });

        binding.qtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(String.valueOf(binding.qtytext.getText()));
                if(qty < maxQuantity){
                    qty = qty+1;
                    binding.qtytext.setText(String.format("%d", qty));
                }
            }
        });

        binding.qtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = Integer.parseInt(String.valueOf(binding.qtytext.getText()));
                if(qty > 1){
                    qty = qty-1;
                    binding.qtytext.setText(String.format("%d", qty));
                }
            }
        });

        binding.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Productdetails.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getProductinfo(String sku) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(sku).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);
                    binding.detailname.setText(products.getProductName());
                    binding.detail.setText(products.getDescription());
                    binding.detailprice.setText(String.format("%d",products.getProductPrice()));
                    maxQuantity = products.getProductQty();
                    Picasso.with(Productdetails.this).load(products.getImageUrl().toString()).fit().centerCrop().into(binding.detailimg);
                    img = products.getImageUrl().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    private void addingToCartList() {
        final DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
        currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("sku",sku);
        cartMap.put("imageUrl",img);
        cartMap.put("productName",binding.detailname.getText().toString());
        cartMap.put("productQty",qty);
        cartMap.put("productPrice",Integer.parseInt(binding.detailprice.getText().toString()));
        total = Integer.parseInt(String.valueOf(binding.detailprice.getText())) * qty;
        cartMap.put("totalPrice",total);

        cartRef.child(currentUser).child(sku).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Productdetails.this, "Added to Cart!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Productdetails.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}