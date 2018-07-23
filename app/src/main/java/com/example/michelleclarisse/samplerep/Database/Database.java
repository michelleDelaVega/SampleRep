package com.example.michelleclarisse.samplerep.Database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.michelleclarisse.samplerep.Model.Order;
import com.example.michelleclarisse.samplerep.Model.Products;
import com.example.michelleclarisse.samplerep.ProductInfoActivity;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Database {
    FirebaseDatabase database;
    DatabaseReference tbl_products, tbl_orders;

    FirebaseAuth mAuth;

    public Database() {
    }
/*
    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect-{"ProductName","ProductId", "Quantity", "Price","Discount};
                String sqlTable="orderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query{db,sqlSelect,null,null,null,null,null};

        final List <Order> result = new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnindex("ProductId")),
                        c.getString(c.getColumnindex("ProductName")),
                        c.getString(c.getColumnindex("Quantity")),
                        c.getString(c.getColumnindex("Price")),
                        c.getString(c.getColumnindex("Discount")),
                        ));
            }while (c.moveToNext());

        }
        return result;

    }//end of sqlSelect[]
    }//end of getCarts

    private void addToCart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String uid = currentUser.getUid();
        tbl_products.child(ProdId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Products products = dataSnapshot.getValue(Products.class);

        String name = products.getName();
        String price = products.getPrice();
        String quantitiy = pr;
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
        });//end of tbl_orders
     }//end of onDataChanged

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
            });

    }//end of addToCart()
*/

}//end of Database.class
