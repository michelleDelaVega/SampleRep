package com.example.michelleclarisse.samplerep;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.michelleclarisse.samplerep.Common.Common;
import com.example.michelleclarisse.samplerep.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class loginActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    EditText loginEmail, loginPassword;

    Button loginBtn;
    ProgressBar loginProgressBar;
    FirebaseDatabase database;
    DatabaseReference tbl_Users;
    Common common=new Common();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        tbl_Users = database.getReference("Users");

        loginPassword = (EditText) findViewById(R.id.txtpassword);
        loginEmail = (EditText) findViewById(R.id.txtemail);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        loginProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //loginProgressBar.setVisibility(View.VISIBLE);
                loginNow();
            }
        });
    }//end of onCreate

    public void loginNow() {
        final String emailForlogin = loginEmail.getText().toString();
        final String passwordForlogin = loginPassword.getText().toString();

        if(TextUtils.isEmpty(emailForlogin) || TextUtils.isEmpty(passwordForlogin)) {
            loginPassword.setError("Must be more than 6 characters.");
            loginEmail.setError("");
            Toast.makeText(loginActivity.this, "Enter necessary fields.", Toast.LENGTH_SHORT).show();
        } else {
            loginProgressBar.setVisibility(View.VISIBLE);


            //authenticate user
            mAuth.signInWithEmailAndPassword(emailForlogin, passwordForlogin)
                    .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {     //error
                                if (passwordForlogin.length() < 6) {
                                    loginPassword.setError("Must be more than 6 characters.");
                                } else {
                                    Toast.makeText(loginActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                }
                                Log.e("ANONE_ANONE: ", "Authentication failed!");
                            } else {//successful login
                                tbl_Users.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child(""+mAuth.getUid()).exists()){
                                            User user = dataSnapshot.child(""+mAuth.getUid()).getValue(User.class);
                                            Common.currentUser = user;
                                            Log.d("currentUser is ",""+ Common.currentUser.fullName());
                                            loginEmail.setText(Common.currentUser.getFName());
                                            String string = loginEmail.getText().toString();
                                            Log.d("ang laman ng string ay ",""+ string);
                                            sendToHome();
                                        }else{
                                            Log.e("currentUser is ","ULUL HINDI PUMASOK");
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });//end of addValueEventListener

                            }
                            loginProgressBar.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }//end of loginNow()
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null) {
            //mAuth.signOut();
            sendToHome();
        }
        }//end of onStart

    private void sendToHome() {
        startActivity(new Intent(loginActivity.this, Home.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(loginActivity.this, MainActivity.class));
        finish();
    }
}
