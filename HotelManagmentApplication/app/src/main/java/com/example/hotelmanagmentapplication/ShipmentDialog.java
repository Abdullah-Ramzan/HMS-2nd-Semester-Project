package com.example.hotelmanagmentapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import com.example.hotelmanagmentapplication.ModelClasses.OrderDeliveryModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShipmentDialog extends DialogFragment
{
    private static final String TAG = "MyShipmentDialog";
    private TextInputEditText mPhoneNumber,mAddress,mCityName;
    private TextView mtvSend,mtvclose;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shipment, container, false);

        mPhoneNumber= (TextInputEditText)view.findViewById(R.id.EditText_PhoneNumber);
        mAddress=(TextInputEditText)view.findViewById(R.id.EditText_Address);
        mCityName= (TextInputEditText)view.findViewById(R.id.EditText_City);
        mtvclose = (TextView)view.findViewById(R.id.Cancel_Btn);
        mtvSend = (TextView)view.findViewById(R.id.Send_Button);

        mRef= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        mtvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String phoneNumber =  mPhoneNumber.getText().toString();
                String address = mAddress.getText().toString();
                String cityName = mCityName.getText().toString();


                if(!mPhoneNumber.equals(null) && !mAddress.equals(null) && !mCityName.equals(null))
                {

                    String ProductRandomKey = mRef.push().getKey().toString();

                    OrderDeliveryModel ordermodel = new OrderDeliveryModel(address,cityName,phoneNumber);
                    String currentUserID=mAuth.getCurrentUser().getUid();

                    mRef.child("OrderDeliveryDetails").child(ProductRandomKey).setValue(ordermodel);



                    Toast.makeText(getActivity(), "Delivery Details Confirmed", Toast.LENGTH_SHORT).show();

//                    Toast.makeText(getActivity(), "Please Wait for Confirmation From Manager", Toast.LENGTH_SHORT).show();
                    currentUserID = mAuth.getCurrentUser().getUid();
                    mRef.child("Cart").child(currentUserID).removeValue();
                    getDialog().dismiss();

                }
                else
                    {
                        getDialog().dismiss();
                        Toast.makeText(getActivity(), "Shippment Not Confirmed!!! Please Fill all requirements", Toast.LENGTH_SHORT).show();

                    }
            }
        });

        mtvclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getDialog().dismiss();


            }
        });






        return view;

    }
}
