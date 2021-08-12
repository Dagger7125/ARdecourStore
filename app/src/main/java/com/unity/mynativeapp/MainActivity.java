package com.unity.mynativeapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.unity.mynativeapp.adapter.ProductAdapter;
import com.unity.mynativeapp.adapter.ProductCategoryAdapter;
import com.unity.mynativeapp.databinding.ActivityMainBinding;
import com.unity.mynativeapp.model.ProductCategory;
import com.unity.mynativeapp.model.Products;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    boolean isUnityLoaded = false;
    ProductCategoryAdapter productCategoryAdapter;
    RecyclerView productCatRecycler, productRecycler;
    ProductAdapter productAdapter;
    ActivityMainBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String image = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        ImageView imageView = (ImageView) binding.userimg;
        Picasso.with(this).load(image).fit().centerCrop().into(imageView);
        name = "Hello, ".concat(name).concat("!");
        binding.username.setText(name);

        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(new ProductCategory(1, "Trending"));
        productCategoryList.add(new ProductCategory(2, "Newest First"));
        productCategoryList.add(new ProductCategory(3, "High to Low"));
        productCategoryList.add(new ProductCategory(4, "Low to High"));
        productCategoryList.add(new ProductCategory(5, "Out of Stock"));
        setProductCatRecycler(productCategoryList);

        productRecycler = binding.productRecycler;
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productRecycler.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Products.class)
                        .build();
        productAdapter = new ProductAdapter(options);
        productRecycler.setAdapter(productAdapter);

        binding.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.all.setTextColor(Color.parseColor("#005B1B"));
                binding.vase.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));
                binding.skulpture.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));
                Typeface mface = ResourcesCompat.getFont(MainActivity.this, R.font.rubik_medium);
                Typeface bface = ResourcesCompat.getFont(MainActivity.this, R.font.rubik_bold);
                binding.all.setTypeface(bface);
                binding.vase.setTypeface(mface);
                binding.skulpture.setTypeface(mface);
                FirebaseRecyclerOptions<Products> all =
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), Products.class)
                                .build();
                productAdapter.updateOptions(all);
                productAdapter.notifyDataSetChanged();
            }
        });

        binding.skulpture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.skulpture.setTextColor(Color.parseColor("#005B1B"));
                binding.vase.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));
                binding.all.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));
                Typeface mface = ResourcesCompat.getFont(MainActivity.this, R.font.rubik_medium);
                Typeface bface = ResourcesCompat.getFont(MainActivity.this, R.font.rubik_bold);
                binding.all.setTypeface(mface);
                binding.vase.setTypeface(mface);
                binding.skulpture.setTypeface(bface);
                FirebaseRecyclerOptions<Products> skulpture =
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(FirebaseDatabase.getInstance()
                                        .getReference().child("Products").orderByChild("category").equalTo("skulpture"), Products.class)
                                .build();
                productAdapter.updateOptions(skulpture);
                productAdapter.notifyDataSetChanged();
            }
        });

        binding.vase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.vase.setTextColor(Color.parseColor("#005B1B"));
                binding.all.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));
                binding.skulpture.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.cardview_dark_background));
                Typeface mface = ResourcesCompat.getFont(MainActivity.this, R.font.rubik_medium);
                Typeface bface = ResourcesCompat.getFont(MainActivity.this, R.font.rubik_bold);
                binding.all.setTypeface(mface);
                binding.vase.setTypeface(bface);
                binding.skulpture.setTypeface(mface);
                FirebaseRecyclerOptions<Products> vase =
                        new FirebaseRecyclerOptions.Builder<Products>()
                                .setQuery(FirebaseDatabase.getInstance()
                                        .getReference().child("Products").orderByChild("category").equalTo("vase"), Products.class)
                                .build();
                productAdapter.updateOptions(vase);
                productAdapter.notifyDataSetChanged();

            }
        });

        binding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        handleIntent(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
        productAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (productAdapter != null) {
            productAdapter.stopListening();
        }
    }

    private void setProductCatRecycler(List<ProductCategory> productCategoryList){

        productCatRecycler = findViewById(R.id.cat_recycler);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCatRecycler.setLayoutManager(layoutManager);
        productCategoryAdapter = new ProductCategoryAdapter(this, productCategoryList);
        productCatRecycler.setAdapter(productCategoryAdapter);

    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
    }

    void handleIntent(Intent intent) {
        if(intent == null || intent.getExtras() == null) return;

        if(intent.getExtras().containsKey("setColor")){
            View v = findViewById(R.id.cartBtn);
            switch (intent.getExtras().getString("setColor")) {
                case "yellow": v.setBackgroundColor(Color.YELLOW); break;
                case "red": v.setBackgroundColor(Color.RED); break;
                case "blue": v.setBackgroundColor(Color.BLUE); break;
                default: break;
            }
        }
    }

    public void btnLoadUnity(View v) {
        isUnityLoaded = true;
        Intent intent = new Intent(this, MainUnityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) isUnityLoaded = false;
    }

    public void unloadUnity(Boolean doShowToast) {
        if(isUnityLoaded) {
            Intent intent = new Intent(this, MainUnityActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("doQuit", true);
            startActivity(intent);
            isUnityLoaded = false;
        }
        else if(doShowToast) showToast("Show Unity First");
    }

    public void btnUnloadUnity(View v) {
        unloadUnity(true);
    }

    public void showToast(String message) {
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
