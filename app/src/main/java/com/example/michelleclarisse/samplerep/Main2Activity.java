package com.example.michelleclarisse.samplerep;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    TextView lname, lpass, lemail, luid;
    Button logout;
    FirebaseAuth.AuthStateListener authListener;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        logout = (Button) findViewById(R.id.btnLogout);
        lname = (TextView) findViewById(R.id.lblName);
        luid = (TextView) findViewById(R.id.lblUserID);
        lpass = (TextView) findViewById(R.id.lblPassword);
        lemail = (TextView) findViewById(R.id.lblEmail);

        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Main2Activity.this, loginActivity.class));
                    finish();
                }
            }
        };
/*
        Bundle b = getIntent().getExtras();
        String keyEmail = b.getString("keyEmail");
        String keyPassword = b.getString("keyPassword");

        mAuth.signInWithEmailAndPassword(keyEmail, keyPassword)
                .addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("ANONE_ANONE: ", "Authentication failed!");
                        } else {    currentUI();       }
                    }
                });
 */       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
    }//end of onCreate
    public void currentUI(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        luid.setText(currentUser.getUid());
        lemail.setText(currentUser.getEmail());
        Log.d("UID",""+currentUser.getUid());
        Log.d("Email", ""+currentUser.getEmail());
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }
}
