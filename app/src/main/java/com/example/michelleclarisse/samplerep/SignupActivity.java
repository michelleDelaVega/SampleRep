package com.example.michelleclarisse.samplerep;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference database;
    EditText fName, mName, lName, email, phone, password, confirmPassword;
    Button clear, register;
    AlertDialog.Builder alertDialog;
    DialogInterface.OnClickListener dialogClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        alertDialog = new AlertDialog.Builder(SignupActivity.this);

        database = FirebaseDatabase.getInstance().getReference();

        fName = (EditText) findViewById(R.id.txtFName);
        lName = (EditText) findViewById(R.id.txtLName);
        mName = (EditText) findViewById(R.id.txtMName);
        email = (EditText) findViewById(R.id.txtEmail);
        phone = (EditText) findViewById(R.id.txtPhone);
        password = (EditText) findViewById(R.id.txtPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        clear = (Button) findViewById(R.id.btnClear);
        register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(confirmPassword.getText().toString().equals(password.getText().toString())){
                        saveToFirebase();
                        // intent to go back to the homepage
                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        /*dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Yes button clicked
                                        saveToFirebase();
                                        // intent to go back to the homepage
                                        Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        break;
                                }}};*/}else{
                        Toast.makeText(SignupActivity.this,"Password must be the same.",Toast.LENGTH_LONG).show();
                        password.setText("");
                        confirmPassword.setText("");
                    }
                }catch (Exception e){
                    Toast.makeText(SignupActivity.this,"Warning. Missing Fields.",Toast.LENGTH_LONG).show();
                }}});
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName.setText("");
                lName.setText("");
                mName.setText("");
                email.setText("");
                phone.setText("");
                password.setText("");
                confirmPassword.setText("");
            }
        });
    }
    public void saveToFirebase(){
    String fn = fName.getText().toString().trim();
    String ln = lName.getText().toString().trim();
    String mn = mName.getText().toString().trim();
    String em = email.getText().toString().trim();
    String pn = phone.getText().toString().trim();
    String pw = password.getText().toString().trim();

    HashMap<String, String> dataMap = new HashMap<String, String>();

    dataMap.put("First Name",fn);   dataMap.put("Last Name",ln);    dataMap.put("Middle Name",mn);
    dataMap.put("Email",em);        dataMap.put("Password",pw);     dataMap.put("phone",pn);

    database.push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
            Toast.makeText(SignupActivity.this, ""+ fName.getText().toString()+"was successfully added!", Toast.LENGTH_SHORT).show();
        }else{
                Toast.makeText(SignupActivity.this, "Error...", Toast.LENGTH_SHORT).show();
            }}
    });

    }

}