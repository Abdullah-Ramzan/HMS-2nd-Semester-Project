package com.example.hotelmanagmentapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class Orders extends AppCompatActivity {

    EditText editText;
    TextView textView1,textView2;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

      editText=(EditText)findViewById(R.id.address_edittext);
        textView1=(TextView) findViewById(R.id.text_view1);
        textView2=(TextView) findViewById(R.id.text_view2);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Users");


        //Intialize Places
        Places.initialize(getApplicationContext(),"AIzaSyCXOx9KI8Mxcf5JUwmxOTYtZ-KdAQpgvDQ");


        //Set edittext to non_focusable
        editText.setFocusable(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG,Place.Field.NAME);
                //Create Intent
                Intent intent =new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                        ,fieldList).build(Orders.this);
                //Start activity result
                startActivityForResult(intent,100);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100&& resultCode ==RESULT_OK)
        {
            //When success
            //Initialize place
            Place place =Autocomplete.getPlaceFromIntent(data);
            //set address on Edittext
            editText.setText(place.getAddress());

            //set localion name


            textView1.setText(String.format("Location Name : %s",place.getName()));
            //set Latitude & longitude
            textView2.setText(String.valueOf(place.getLatLng()));

        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR)
        {
            //When error
            //Initialize status

            Status status =Autocomplete.getStatusFromIntent(data);
            //Display Toast
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            textView1.setText(String.format(status.getStatusMessage()));
        }
    }
}
