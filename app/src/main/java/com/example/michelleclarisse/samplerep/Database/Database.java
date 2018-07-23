package com.example.michelleclarisse.samplerep.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "SampleRepDB.db";
    private static final int DB_VER=1;
    public Database (Context context){
        super(context, DB_NAME, null, DB_VER);
    }
    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect={"ProdId","ProdName","ProdQty", "ProdPrice","ProdDiscount"};
                String sqlTable="ProductInfo";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List <Order> result = new ArrayList<>();
    if(c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProdId")),
                        c.getString(c.getColumnIndex("ProdName")),
                        c.getString(c.getColumnIndex("ProdQty")),
                        c.getString(c.getColumnIndex("ProdPrice")),
                        c.getString(c.getColumnIndex("ProdDiscount"))
                        ));
            }while (c.moveToNext());
        }
            return result;
    }//end of getCarts
    public void addToCart(Order order)
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO ProductInfo(ProdId,ProdName,ProdQty,ProdPrice,ProdDiscount) VALUES('%s','%s','%s','%s','%s')",
                order.getProdId(),
                order.getProdName(),
                order.getProdQty(),
                order.getProdPrice(),
                order.getProdDiscount());
        db.execSQL(query);
    }
    public void cleanCart()
    {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM ProductInfo");
        db.execSQL(query);
    }
}//end of Database.class
