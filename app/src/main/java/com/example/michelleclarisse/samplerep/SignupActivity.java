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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SignupActivity extends AppCompatActivity {

    DatabaseReference database;
    EditText fName, mName, lName, email, phone, password, confirmPassword;
    Button clear, register, cancel;
    FirebaseAuth mAuth;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        fName = (EditText) findViewById(R.id.txtFName);
        lName = (EditText) findViewById(R.id.txtLName);
        mName = (EditText) findViewById(R.id.txtMName);
        email = (EditText) findViewById(R.id.txtEmail);
        phone = (EditText) findViewById(R.id.txtPhone);
        password = (EditText) findViewById(R.id.txtPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        clear = (Button) findViewById(R.id.btnClear);
        cancel = (Button) findViewById(R.id.btnCancel);
        register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String testEmail = email.getText().toString().trim();
                    String testPassword = password.getText().toString().trim();
                    String testConfirmPassword = confirmPassword.getText().toString().trim();
                    String testFname = fName.getText().toString().trim();
                    String testMname = mName.getText().toString().trim();
                    String testLname= lName.getText().toString().trim();
                    String testPhone = phone.getText().toString().trim();
                    if (TextUtils.isEmpty(testEmail)) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    }if (TextUtils.isEmpty(testPassword)) {
                        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    }else{
                        if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        }else{
                            if(testConfirmPassword.equals(testPassword)){
                            saveToFirebase();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                            }else{
                            Toast.makeText(SignupActivity.this,"Password must be the same.",Toast.LENGTH_LONG).show();
                            password.setText("");
                            confirmPassword.setText("");
                                }
                            }
                     }
                }catch (Exception e){
                    Toast.makeText(SignupActivity.this,"Warning. Missing Fields.",Toast.LENGTH_LONG).show();
                }}});

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fName.setText("");                lName.setText("");                mName.setText("");
                email.setText("");                phone.setText("");                password.setText("");
                confirmPassword.setText("");
            }
        });

    cancel.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            startActivity(new Intent(SignupActivity.this,MainActivity.class));  finish();
        }});
    }//end of onCreate
    public void saveToFirebase(){
        String fn = fName.getText().toString().trim();
        String ln = lName.getText().toString().trim();
        String mn = mName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pn = phone.getText().toString().trim();
        String pw = password.getText().toString().trim();
        counter =+1 ;
        //create user
        mAuth.createUserWithEmailAndPassword(em, pw)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Log.e("ANONE_ANONE: ", "create Account: Fail!", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

        HashMap<String, String> dataMap = new HashMap<String, String>();

        dataMap.put("First Name",fn);   dataMap.put("Last Name",ln);    dataMap.put("Middle Name",mn);
        dataMap.put("Email",em);        dataMap.put("Password",pw);     dataMap.put("phone",pn);

        Set set = dataMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry) iterator.next();
            Log.d("Key: ",""+mentry.getKey()+" | Value: "+mentry.getValue() );
        }
        Log.d("Counter: ",""+counter);
/*        database.push().child("Users").setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d("Remarks", "PUMASOK");
                    Toast.makeText(SignupActivity.this,"BETCH PLZ", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(SignupActivity.this,"BAKIT HINDE?", Toast.LENGTH_LONG).show();
                }
            }
        });
*/
        database.child("Users").push().setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, ""+ fName.getText().toString()+"was successfully added!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignupActivity.this, "Error...", Toast.LENGTH_SHORT).show();
                }}
        });

    }//end of saveToFirebase()
        /*@Override
        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
        }*/
}//done!

/*
 * GUI NA LANG KULANG MGA TEH
 */
