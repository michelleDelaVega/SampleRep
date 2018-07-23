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

public class Main2Activity extends AppCompatActivity {
    TextView lname, lpass, lemail, luid;
    Button logout;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase database;
    DatabaseReference tbl_users;
    FirebaseAuth.AuthStateListener mAuthListener;

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
        database = FirebaseDatabase.getInstance();
        tbl_users = database.getReference("");
        firebaseUser = mAuth.getCurrentUser();
        String userID = firebaseUser.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
/*tbl_users.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        showDataSnapshot(dataSnapshot);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});*/
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
    }//end of onCreate

    private void showDataSnapshot(DataSnapshot dataSnapshot) {
    for(DataSnapshot ds: dataSnapshot.getChildren()){
        User uinfo = new User();
        uinfo.setEmail(ds.child(firebaseUser.getUid()).getValue(User.class).getEmail());
        lemail.setText(uinfo.getEmail());
    }
    }

    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }
    private void sendToLogin() {
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();
    }
}
