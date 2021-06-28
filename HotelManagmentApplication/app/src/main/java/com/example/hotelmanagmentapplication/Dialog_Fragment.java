package com.example.hotelmanagmentapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hotelmanagmentapplication.ModelClasses.CartModel;
import com.example.hotelmanagmentapplication.ModelClasses.OrderProductModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dialog_Fragment extends DialogFragment
{

    private TextInputEditText mInput,mQuantity;
    private TextView mtvOk,mtvCancel;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private static final String TAG = "MyGeneralDialog";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dialog, container, false);

        mInput = (TextInputEditText) view.findViewById(R.id.EditText_SpecialInstruction);
        mtvOk = (TextView) view.findViewById(R.id.Ok_Button);
        mtvCancel = (TextView) view.findViewById(R.id.Cancel_Button);
        mQuantity = (TextInputEditText) view.findViewById(R.id.EditText_Quantity);



        mRef= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mtvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mtvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String Quantity = mQuantity.getText().toString();
                int Qty = Integer.parseInt(Quantity);
                String input = mInput.getText().toString();
                if(!input.equals(""))
                {
                    String currentUserID= mAuth.getCurrentUser().getUid();
                    mRef.child("Personal").child("Quantity").child(currentUserID).child("Current_Quantity").setValue(String.valueOf(Qty));
                    mRef.child("Personal").child("Special_Instruction").child(currentUserID).child("Special_Instruction").setValue(input);

                    Bundle bundle = getArguments();
                    String selected_item = bundle.getString("Item_Selected");
                    int price = bundle.getInt("Price");
                    mRef.child("Personal").child("Selected_Item").child(currentUserID).child("Item").setValue(selected_item);



                    Bundle bundle1 = getArguments();
                    String category = bundle1.getString("Category");


                    mRef.child("Personal").child("Price").child(currentUserID).child("ItemPrice").setValue(price);

                    final String saveCurrentTime,saveCurrentDate;
                    String ProductRandomKey;
                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("MM dd,yyyy");
                    saveCurrentDate = currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
                    saveCurrentTime =currentDate.format(calendar.getTime());
//                    ProductRandomKey = saveCurrentTime+saveCurrentDate;




                     ProductRandomKey = mRef.push().getKey().toString();


                    CartModel cartModel = new CartModel(category,selected_item,Qty,input,price,saveCurrentDate,saveCurrentTime,ProductRandomKey);
                    OrderProductModel orderProductModel = new OrderProductModel(input,selected_item,String.valueOf(price),String.valueOf(Qty),ProductRandomKey);
                    mRef.child("OrderProducts").child(ProductRandomKey).setValue(orderProductModel);

                    mRef.child("Cart").child(currentUserID).child(ProductRandomKey).setValue(cartModel);
                    Toast.makeText(getActivity(),"Cart Updated" , Toast.LENGTH_SHORT).show();
                }
                getDialog().dismiss();
            }

        });

        return view;
    }
}
