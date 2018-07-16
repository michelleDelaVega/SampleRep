package com.example.michelleclarisse.samplerep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = FirebaseDatabase.getInstance().getReference();
    }
    public void mLogin(View v){
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
    public void mRegister(View v){
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
        finish();
    }
}
