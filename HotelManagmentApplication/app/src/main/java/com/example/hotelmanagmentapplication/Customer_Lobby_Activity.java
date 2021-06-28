package com.example.hotelmanagmentapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Customer_Lobby_Activity extends AppCompatActivity
{
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private ActionBarDrawerToggle toggle;
    private String data1,data2;
    private CircleImageView headerimage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_lobby);

        navigationView = (NavigationView)findViewById(R.id.navigation);
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer);
        Toolbar toolbar =findViewById(R.id.toolbar);


        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Profile");

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.quantum_white_text));


        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                drawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId())
                {
                    case R.id.nav_foodMenu:
                        Toast.makeText(Customer_Lobby_Activity.this, "Food Menu", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,new TestFragment())
                                .commit();
                        break;

                    case R.id.nav_cart:
                        Toast.makeText(Customer_Lobby_Activity.this, "Cart", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,new CartFragment())
                                .commit();
                        break;

                    case R.id.nav_Profile:
                        Toast.makeText(Customer_Lobby_Activity.this, "Profile", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,new ProfileFragment())
                                .commit();
                        break;
                    case R.id.nav_Share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody = "Your Body Here";
                        String shareSub="Your Subject Here";
                        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                        intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);

                        startActivity(Intent.createChooser(intent,"ShareVia"));
                        Toast.makeText(Customer_Lobby_Activity.this, "Share", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.nav_logout:
                        mAuth.signOut();
                        Intent intent1 = new Intent(Customer_Lobby_Activity.this,Customer_Login_Activity.class);
                        startActivity(intent1);
                        finish();
                        Toast.makeText(Customer_Lobby_Activity.this, "Sign out", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        String currentUserID=mAuth.getCurrentUser().getUid();
        mRef.child(currentUserID).child("ProfileImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data2=  dataSnapshot.getValue(String.class);
                View headerview = navigationView.getHeaderView(0);
                CircleImageView headerimage = headerview.findViewById(R.id.header_image);
                Picasso.get().load(data2).into(headerimage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


            }
        });
        mRef.child(currentUserID).child("CustomerName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data1=  dataSnapshot.getValue(String.class);
                View headerview = navigationView.getHeaderView(0);
                TextView name = headerview.findViewById(R.id.header_name);
                name.setText(data1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


            }
        });

        String CurrentUser =mAuth.getCurrentUser().getEmail();

        View headerview = navigationView.getHeaderView(0);
        TextView email = headerview.findViewById(R.id.header_email);
        TextView name = headerview.findViewById(R.id.header_name);
      //
        email.setText(CurrentUser);
        name.setText(data1);
       //


        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new TestFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_foodMenu);
        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }






}
