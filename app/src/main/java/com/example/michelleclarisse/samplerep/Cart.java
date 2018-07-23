package com.example.michelleclarisse.samplerep;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.michelleclarisse.samplerep.Common.Common;
import com.example.michelleclarisse.samplerep.Database.Database;
import com.example.michelleclarisse.samplerep.Model.Order;
import com.example.michelleclarisse.samplerep.Model.Requests;
import com.example.michelleclarisse.samplerep.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference tbl_verified;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = FirebaseDatabase.getInstance();
        tbl_verified = database.getReference("Verified");
        //Init
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);
        loadListFood();

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }//end of onCreate
    private void showAlertDialog() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
            alertDialog.setTitle("One more step!");
            alertDialog.setMessage("Ënter your address: ");

            final EditText edtAddress = new EditText(Cart.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            edtAddress.setLayoutParams(lp);
            alertDialog.setView(edtAddress); // Add edit Text to Alert Dialog
            alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

            alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialogInterface, int i) {
                    Requests request = new Requests(
                            Common.currentUser.getPhone(),
                            Common.currentUser.fullName(),
                            edtAddress.getText().toString(),
                            txtTotalPrice.getText().toString(),
                            cart
                    );
                }
            });
    }//end of showDialog()
    private void loadListFood(){
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //Calculate Total Price
        double total = 0.00;
        for(Order order: cart)
        {
            Log.e("Price: ",""+order.getProdPrice());
            total += (Double.parseDouble(order.getProdPrice()))*(Double.parseDouble(order.getProdQty()));}
        Locale locale = new Locale("en	","PH");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));
    }
}//end of Cart.class
