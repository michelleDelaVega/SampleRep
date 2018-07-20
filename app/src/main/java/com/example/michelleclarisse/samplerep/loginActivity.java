package com.example.michelleclarisse.samplerep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    FirebaseAuth mAuth;
    Button loginBtn;
    ProgressBar loginProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
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
                                    Toast.makeText(loginActivity.this, "Try again", Toast.LENGTH_LONG).show();
                                }
                                Log.e("ANONE_ANONE: ", "Authentication failed!");
                            } else {                        //successful login
                                sendToHome();
                                /*
                                FirebaseUser user = mAuth.getCurrentUser();

                                Intent intent = new Intent(loginActivity.this, Main2Activity.class);

                                intent.putExtra("keyEmail",user.getEmail().toString());
                                intent.putExtra("keyPassword",loginPassword.getText().toString());
                                startActivity(intent);
                                finish();
                                */
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
            sendToHome();
        }
        }//end of onStart

    private void sendToHome() {
        startActivity(new Intent(loginActivity.this, Main2Activity.class));
        finish();
    }

}
