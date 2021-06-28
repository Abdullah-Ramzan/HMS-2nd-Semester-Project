package com.example.hmsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowProducts_Activity extends AppCompatActivity
{
    private RecyclerView myItemList;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private Button AcceptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        myItemList = (RecyclerView)findViewById(R.id.product_list);

        mRef= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        myItemList.setLayoutManager(new LinearLayoutManager(this));
        AcceptButton =(Button) findViewById(R.id.acceptOrder);


        AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOrder();
            }
        });

        FirebaseRecyclerOptions<OrderProductModel> options =
                new FirebaseRecyclerOptions.Builder<OrderProductModel>()
                        .setQuery(mRef.child("OrderProducts"), OrderProductModel.class)
                        .build();

        FirebaseRecyclerAdapter<OrderProductModel, ShowProducts_Activity.ProductsViewHolder> adapter
                = new FirebaseRecyclerAdapter<OrderProductModel, ProductsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShowProducts_Activity.ProductsViewHolder holder, int position, @NonNull OrderProductModel model)
            {
                holder.itemName.setText(model.getItemName());
                holder.itemNumber.setText(String.valueOf(holder.getAdapterPosition()+1));
                holder.quantity.setText(model.getQuantity());
                holder.price.setText(model.getPrice());
                holder.specialInstruction.setText(model.getSpecialInstruction());
            }

            @NonNull
            @Override
            public ShowProducts_Activity.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout3,parent,false);
                ShowProducts_Activity.ProductsViewHolder viewHolder = new ShowProducts_Activity.ProductsViewHolder(view);
                return viewHolder;
            }
        };
        adapter.startListening();
        myItemList.setAdapter(adapter);




    }

    private void acceptOrder()
    {
        mRef.child("OrderProducts").removeValue();
        mRef.child("OrderDeliveryDetails").removeValue();
        Intent intent = new Intent(ShowProducts_Activity.this,Manager_Login_Activity.class);
        startActivity(intent);


    }


    public static class ProductsViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemNumber,itemName,price,quantity,specialInstruction;

        public ProductsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            itemNumber = itemView.findViewById(R.id.itemnumber);
            itemName = itemView.findViewById(R.id.itemname);
            price = itemView.findViewById(R.id.tv_price);
            quantity = itemView.findViewById(R.id.tv_quantity);
            specialInstruction = itemView.findViewById(R.id.tv_specialInstruction);

        }
    }

}



