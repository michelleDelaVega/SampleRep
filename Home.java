package com.example.michelleclarisse.samplerep;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michelleclarisse.samplerep.Common.Common;
import com.example.michelleclarisse.samplerep.Interface.ItemClickListener;
import com.example.michelleclarisse.samplerep.Model.Category;
import com.example.michelleclarisse.samplerep.Model.User;
import com.example.michelleclarisse.samplerep.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    FirebaseAuth mAuth;
    TextView lblCurrentUserName,lblCurrentEmail;
    DatabaseReference tbl_Users, tbl_Category;
    NavigationView navigationView;
    User user = new User();

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    // Firebase Objects
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        tbl_Users = database.getReference("Users");
        tbl_Category= database.getReference("Category");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);

        lblCurrentUserName = (TextView) header.findViewById(R.id.lblNavUsername);
        lblCurrentEmail = (TextView) header.findViewById(R.id.lblNavEmail);
        lblCurrentEmail.setText("");
        tbl_Users.addValueEventListener(new ValueEventListener() { //4:00 am | July 23,2018 | onCreate() nakalagay....
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(""+mAuth.getUid()).exists()){
                    user = dataSnapshot.child(""+mAuth.getUid()).getValue(User.class);
                    Common.currentUser = user;
                    Log.e("currentUser is ",""+Common.currentUser.getEmail());
                    String value = ""+dataSnapshot.child(""+mAuth.getUid()).child("Email").getValue(String.class);
                    lblCurrentUserName.setText(Common.currentUser.fullName());
                    lblCurrentEmail.setText(Common.currentUser.getEmail());

                }else{
                    Log.e("currentUser is ","ULUL HINDI PUMASOK");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });//end of addValueEventListener
         //Log.e("Ang laman ng string ay ",Common.currentUser.getFName());

    //    lblCurrentUserName.setText(Common.currentUser.fullName());
     //   lblCurrentEmail.setText(Common.currentUser.getEmail());

        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        loadMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }//end of onCreate()


    private void loadMenu() {   //Loading Menu Category
        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,tbl_Category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickItem = model;

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(Home.this, clickItem.getName(),Toast.LENGTH_SHORT).show();

                        Intent productListActivity = new Intent(Home.this, ProductListActivity.class);
                        productListActivity.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(productListActivity);

                    }
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_signout) {
            new AlertDialog.Builder(this)
                    .setTitle("Sign out?")
                    .setMessage("Are you sure you want to sign out?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            logOut();
                        }
                    }).create().show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_signout) {
            logOut();
        } else if (id == R.id.nav_users) {
//for admin
        } else if (id == R.id.nav_menu) {
//for admin
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void logOut() {
        mAuth.signOut();
        sendToLogin();
    }
    private void sendToLogin() {
        startActivity(new Intent(Home.this, MainActivity.class));
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();

        tbl_Users.addValueEventListener(new ValueEventListener() { //4:00 am | July 23,2018 | onCreate() nakalagay....
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(""+mAuth.getUid()).exists()){
                    user = dataSnapshot.child(""+mAuth.getUid()).getValue(User.class);
                    Common.currentUser = user;
                    Log.e("currentUser is ",""+mAuth.getUid());
                    String value = ""+dataSnapshot.child(""+mAuth.getUid()).child("Email").getValue(String.class);
                }else{
                    Log.e("currentUser is ","ULUL HINDI PUMASOK");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });//end of addValueEventListener
        //if()
        // Log.e("Ang laman ng string ay ",Common.currentUser.getFName());
    }//end of onStart
}
