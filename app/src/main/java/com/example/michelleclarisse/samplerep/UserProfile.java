package com.example.michelleclarisse.samplerep;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    FloatingActionButton editInfo;
    EditText userFName, userMName, userLName, userPhone, userEmail, userPassword, userConfirmPassword;
    TextView lblUserFullName, lblUserPhone, lblUserEmail, lblUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        editInfo = (FloatingActionButton)findViewById(R.id.btnEditProfile);


        userFName = (EditText)findViewById(R.id.editFName);
        userMName = (EditText)findViewById(R.id.editMName);
        userLName = (EditText)findViewById(R.id.editLName);
        userPhone = (EditText)findViewById(R.id.editPhone);
        userEmail = (EditText)findViewById(R.id.editEmail);
        userPassword = (EditText)findViewById(R.id.editPassword);
        userConfirmPassword = (EditText)findViewById(R.id.editConfirmPassword);

        lblUserFullName = (EditText)findViewById(R.id.editFName);


    }
}
