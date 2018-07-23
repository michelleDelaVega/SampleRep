package com.example.michelleclarisse.samplerep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.michelleclarisse.samplerep.Interface.ItemClickListener;
import com.example.michelleclarisse.samplerep.Model.Products;
import com.example.michelleclarisse.samplerep.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference productList;

    String categoryId = "";
    FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        //Firebase
        database = FirebaseDatabase.getInstance();
        productList = database.getReference("Products");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_products);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Get intent here

        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadProductList(categoryId);
        }

    }//end of onCreate

    private void loadProductList(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(Products.class,
                R.layout.product_item,
                ProductViewHolder.class,
                productList.orderByChild("MenuId").equalTo(categoryId) //like: Select * from Foods where MenuId =
        ) {
            @Override
            protected void populateViewHolder(ProductViewHolder viewHolder, Products model, int postion) {
                viewHolder.productName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.productImage);

                final Products clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent productInfo= new Intent(ProductListActivity.this,ProductInfoActivity.class);
                        productInfo.putExtra("ProdId",adapter.getRef(position).getKey());
                        startActivity(productInfo);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }
}