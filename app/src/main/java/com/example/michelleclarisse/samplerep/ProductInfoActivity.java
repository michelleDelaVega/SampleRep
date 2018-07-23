package com.example.michelleclarisse.samplerep;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.michelleclarisse.samplerep.Database.Database;
import com.example.michelleclarisse.samplerep.Model.Order;
import com.example.michelleclarisse.samplerep.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ProductInfoActivity extends AppCompatActivity {

    TextView prodName, prodPrice, prodDescription, prodDiscount;
    ImageView prodImage;
    FloatingActionButton cart;
    ElegantNumberButton qty;

    String ProdId = "";

    FirebaseDatabase database;
    DatabaseReference tbl_products, tbl_orders;

    FirebaseAuth mAuth;
Products products = new Products();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        database = FirebaseDatabase.getInstance();
        tbl_products = database.getReference("Products");
        tbl_orders = database.getReference("Orders");
        mAuth = FirebaseAuth.getInstance();

        prodImage = (ImageView) findViewById(R.id.prodInfo_image);
        prodName = (TextView) findViewById(R.id.prodInfo_name);
        prodPrice = (TextView) findViewById(R.id.prodInfo_price);
        prodDescription = (TextView) findViewById(R.id.prodInfo_description);
        prodDiscount = (TextView) findViewById(R.id.prodInfo_discount);

        cart = (FloatingActionButton) findViewById(R.id.btnCart);
        qty = (ElegantNumberButton) findViewById(R.id.number_button);

        //Get Food Id from Intent
        if (getIntent() != null) {
            ProdId = getIntent().getStringExtra("ProdId");
        }
        if (!ProdId.isEmpty()) {
            getDetailFood(ProdId);
        }

        cart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        ProdId,prodName.getText().toString(),qty.getNumber(),prodPrice.getText().toString(),prodDiscount.getText().toString()
                        ));
                Log.e("PRODID is ","_"+ProdId+"_");
            showAlertDialog();
            }//end of onClick()
        });
    }//end of onCreate()

    private void addToCart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String uid = currentUser.getUid();

        String name = prodName.getText().toString();
        String price = prodPrice.getText().toString();
        String quantitiy = qty.getNumber();
        String discount = prodDiscount.getText().toString();
        Double subtotal = (Double.parseDouble(quantitiy) * Double.parseDouble(price)) - (Double.parseDouble(discount) * Double.parseDouble(price));

        Log.d("UID : ", "" + uid);
        HashMap<String, String> dataMap = new HashMap<String, String>();

        dataMap.put("ProdName", name);
        dataMap.put("ProdPrice", price);
        dataMap.put("ProdQty", quantitiy);
        dataMap.put("ProdDiscount", discount);
        dataMap.put("ProdSubtotal", (subtotal).toString());

        Set set = dataMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry) iterator.next();
            Log.d("Key: ", "" + mentry.getKey() + " | Value: " + mentry.getValue());
        }
        tbl_orders.child("Cart").child(uid).child("Product_" + name).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ProductInfoActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProductInfoActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDetailFood(String prodId) {
        tbl_products.child(ProdId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Products products = dataSnapshot.getValue(Products.class);
                Picasso.with(getBaseContext()).load(products.getImage()).into(prodImage);
                prodPrice.setText(products.getPrice());
                prodName.setText(products.getName());
                prodDescription.setText(products.getDescription());
                prodDiscount.setText(products.getDiscount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }//end of getDetailFood()

    private void showAlertDialog() {
        String name = prodName.getText().toString();
        String price = prodPrice.getText().toString();
        String quantitiy = qty.getNumber();
        String discount = prodDiscount.getText().toString();
        Double subtotal = (Double.parseDouble(quantitiy) * Double.parseDouble(price)) - (Double.parseDouble(discount) * Double.parseDouble(price));

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInfoActivity.this);
        alertDialog.setTitle("Review");
        alertDialog.setMessage(name);

        final TextView edtQuantity = new TextView(ProductInfoActivity.this);
        final TextView edtSubtotal= new TextView(ProductInfoActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtQuantity.setLayoutParams(lp);
        edtSubtotal.setLayoutParams(lp);

        edtQuantity.setText("Quantity: "+quantitiy);
        edtSubtotal.setText("Subtotal: "+subtotal);

        alertDialog.setView(edtQuantity); // Add edit Text to Alert Dialog
        alertDialog.setView(edtSubtotal); // Add edit Text to Alert Dialog
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                addToCart();
                Toast.makeText(ProductInfoActivity.this, "Thank You , Order Placed", Toast.LENGTH_SHORT).show();
                finish();
            }//end of onClick()
        }); //end of positive

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });//end of negative
        alertDialog.show();
    }//end of showAlertDialog()
}//end of ProductInfoActivity.class