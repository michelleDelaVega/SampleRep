package com.example.michelleclarisse.samplerep;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    EditText txtEmail, txtPassword;
    FirebaseAuth mAuth;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        txtPassword = (EditText) findViewById(R.id.txtpassword);
        txtEmail = (EditText) findViewById(R.id.txtemail);
        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginNow();
            }
        });
    }//end of onCreate

    public void loginNow() {
        final String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            txtPassword.setError("Must be more than 6 characters.");
            txtEmail.setError("");
            Toast.makeText(loginActivity.this, "Enter necessary fields.", Toast.LENGTH_SHORT).show();
        } else {
            //authenticate user
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(loginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                if (password.length() < 6) {
                                    txtPassword.setError("Must be more than 6 characters.");
                                } else {
                                    Toast.makeText(loginActivity.this, "Try again", Toast.LENGTH_LONG).show();
                                }
                                Log.e("ANONE_ANONE: ", "Authentication failed!");
                            } else {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(loginActivity.this, Main2Activity.class);
                                intent.putExtra("keyEmail",user.getEmail().toString());
                                intent.putExtra("keyPassword",txtPassword.getText().toString());
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
        }
    }//end of loginNow()
    /*@Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/


}
