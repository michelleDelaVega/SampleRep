package com.example.michelleclarisse.samplerep;

import android.app.MediaRouteButton;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michelleclarisse.samplerep.Common.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UserProfile extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference tbl_Users;
    AuthCredential credential;
    Common common=new Common();

    CardView cardEditInfo,cardDisplayInfo;
    FloatingActionButton editInfo,saveInfo ;
    EditText userFName, userMName, userLName, userPhone, userEmail, userPassword, userConfirmPassword, userNewPassword ;
    TextView lblUserFullName, lblUserPhone, lblUserEmail, lblUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        cardEditInfo= (CardView) findViewById(R.id.layout_EditInfo);
        cardDisplayInfo = (CardView) findViewById(R.id.layout_ProfileInfo);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        tbl_Users = database.getReference("Users");

        editInfo = (FloatingActionButton)findViewById(R.id.btnEditProfile);
        saveInfo = (FloatingActionButton)findViewById(R.id.btnSaveProfile);
        lblUserFullName = (TextView)findViewById(R.id.infoFullName);
        lblUserPhone = (TextView)findViewById(R.id.infoPhone);
        lblUserEmail = (TextView)findViewById(R.id.infoEmail);
        lblUserPassword = (TextView)findViewById(R.id.infoPassword);

        userFName = (EditText)findViewById(R.id.editFName);
        userMName = (EditText)findViewById(R.id.editMName);
        userLName = (EditText)findViewById(R.id.editLName);
        userPhone = (EditText)findViewById(R.id.editPhone);
        userEmail = (EditText)findViewById(R.id.editEmail);
        userPassword = (EditText)findViewById(R.id.editPassword);
        userNewPassword = (EditText)findViewById(R.id.editNewPassword);
        userConfirmPassword = (EditText)findViewById(R.id.editConfirmPassword);

        lblUserEmail.setText(mAuth.getCurrentUser().getEmail());
        lblUserFullName.setText(Common.currentUser.fullName());
        lblUserPhone.setText(Common.currentUser.getPhone());
        lblUserPassword.setText(Common.currentUser.getPassword());

        user = mAuth.getCurrentUser();

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userFName.setText(Common.currentUser.getFName());
                userMName.setText(Common.currentUser.getMName());
                userLName.setText(Common.currentUser.getLName());
                userEmail.setText(Common.currentUser.getEmail());
                userPassword.setText(Common.currentUser.getPassword());
                userPhone.setText(Common.currentUser.getPhone());

                cardEditInfo.setVisibility(View.VISIBLE);
                saveInfo.setVisibility(View.VISIBLE);
                cardDisplayInfo.setVisibility(View.INVISIBLE);
                editInfo.setVisibility(View.INVISIBLE);
            }
        });
        saveInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credential = EmailAuthProvider.getCredential(Common.currentUser.getEmail(),Common.currentUser.getPassword());
                if(userConfirmPassword.getText().toString().equals(userNewPassword.getText().toString())){
            // Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String oldpw = userPassword.getText().toString();
                                String newpw = userNewPassword.getText().toString();
                                String confirmpw = userConfirmPassword.getText().toString();
                                String newemail= userEmail.getText().toString();

                                if (task.isSuccessful()) {
                                    if(!(oldpw.equals(newpw) || (newpw.equals("") || confirmpw.equals("")))){
                                        if(userConfirmPassword.getText().toString().equals(userNewPassword.getText().toString())){
                                        saveNewPassword();
                                        }
                                    }//change password

                                    if(!newemail.equals(user.getEmail())){
                                        saveNewEmail();
                                    }// change email
                                } else {
                                    Toast.makeText(UserProfile.this, "Auth Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });//reauthenticate
                    saveToFirebase();
                    Log.e("New Name",Common.currentUser.fullName());
                    Log.e("New Phone",Common.currentUser.getPhone());
                    Log.e("New Email",Common.currentUser.getEmail());
                    Log.e("New Password",Common.currentUser.getPassword());
                }

                cardDisplayInfo.setVisibility(View.VISIBLE);
                editInfo.setVisibility(View.VISIBLE);
                cardEditInfo.setVisibility(View.INVISIBLE);
                saveInfo.setVisibility(View.INVISIBLE);
            }
        });


    }//end of onCreate

    private void saveNewPassword() {
        user.updatePassword(userConfirmPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserProfile.this, "Update Password Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveNewEmail() {
        user.updateEmail(userEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserProfile.this, "Update Email Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UserProfile.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("currentUser Profile ",""+Common.currentUser.fullName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(cardEditInfo.getVisibility()==View.VISIBLE){
                new AlertDialog.Builder(this)
                        .setTitle("Cancel")
                        .setMessage("Are you sure you want to go back?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                UserProfile.super.onBackPressed();
                            }
                        }).create().show();
            }else{
        startActivity(new Intent(UserProfile.this, Home.class));
        finish();}
    }
    public void saveToFirebase(){

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String uid = currentUser.getUid();
        String fn = userFName.getText().toString().trim();
        String ln = userLName.getText().toString().trim();
        String mn = userMName.getText().toString().trim();
        String em = userEmail.getText().toString().trim();
        String pn = userPhone.getText().toString().trim();
        String pw = userNewPassword.getText().toString().trim();

        Log.d("UID : ",""+uid);
        HashMap<String, String> dataMap = new HashMap<String, String>();

        dataMap.put("FName",fn);   dataMap.put("LName",ln);    dataMap.put("MName",mn);
        dataMap.put("Email",em);        dataMap.put("Password",pw);     dataMap.put("Phone",pn);

        Set set = dataMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry mentry = (Map.Entry) iterator.next();
            Log.e("Key: ",""+mentry.getKey()+" | Value: "+mentry.getValue() );
        }

        tbl_Users.child(uid).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    lblUserEmail.setText(Common.currentUser.getEmail());
                    lblUserFullName.setText(Common.currentUser.fullName());
                    lblUserPhone.setText(Common.currentUser.getPhone());
                    lblUserPassword.setText(Common.currentUser.getPassword());
                }else{
                    Toast.makeText(UserProfile.this, "Error...", Toast.LENGTH_SHORT).show();
                }}
        });
    }//end of saveToFirebase()
}//end of UserProfile.class
