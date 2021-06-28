package com.example.hotelmanagmentapplication;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.hotelmanagmentapplication.ModelClasses.UploadImage;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private CircleImageView profileImage;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private TextView customerName,customerEmail;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_profile, container, false);
        profileImage = (CircleImageView)view.findViewById(R.id.customerprofileimage);
        customerName = (TextView)view.findViewById(R.id.customer_name);
        customerEmail = (TextView)view.findViewById(R.id.customer_Email);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Profile");
        mAuth= FirebaseAuth.getInstance();

        String CurrentUser =mAuth.getCurrentUser().getEmail();
        customerEmail.setText(CurrentUser);



        String currentUserID=mAuth.getCurrentUser().getUid();
        mRef.child(currentUserID).child("ProfileImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String data=  dataSnapshot.getValue(String.class);
                Picasso.get().load(data).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


            }
        });
        mRef.child(currentUserID).child("CustomerName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data=  dataSnapshot.getValue(String.class);
                customerName.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {


            }
        });





        return view;
    }

}
