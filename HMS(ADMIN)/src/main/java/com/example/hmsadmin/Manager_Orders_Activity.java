package com.example.hmsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hmsadmin.OrderDeliveryModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manager_Orders_Activity extends AppCompatActivity
{
    private RecyclerView OrdersRecyclerList;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_orders);

        mRef = FirebaseDatabase.getInstance().getReference().child("OrderDeliveryDetails");


        OrdersRecyclerList = findViewById(R.id.orders_recycler_list);
        OrdersRecyclerList.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<OrderDeliveryModel> options =
                new FirebaseRecyclerOptions.Builder<OrderDeliveryModel>()
                        .setQuery(mRef, OrderDeliveryModel.class)
                        .build();

        FirebaseRecyclerAdapter<OrderDeliveryModel,OrdersViewHolder> adapter
                = new FirebaseRecyclerAdapter<OrderDeliveryModel, OrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull OrderDeliveryModel model)
            {

                holder.address.setText(model.getAddress());
                holder.cityname.setText(model.getCity());
                holder.phoneNumber.setText(String.valueOf(model.getPhoneNumber()));
                holder.ordernumber.setText(String.valueOf(holder.getAdapterPosition()+1));
                holder.productlink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Manager_Orders_Activity.this,ShowProducts_Activity.class);
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout4,parent,false);
                OrdersViewHolder viewHolder = new OrdersViewHolder(view);
                return viewHolder;
            }
        };
        adapter.startListening();
        OrdersRecyclerList.setAdapter(adapter);




    }




    public static class OrdersViewHolder extends RecyclerView.ViewHolder
    {
        TextView address,cityname,phoneNumber,ordernumber,productlink;

        public OrdersViewHolder(@NonNull View itemView)
        {
            super(itemView);
            productlink = itemView.findViewById(R.id.showProducts);
            address = itemView.findViewById(R.id.tv_address);
            cityname = itemView.findViewById(R.id.tv_cityName);
            phoneNumber=itemView.findViewById(R.id.tv_phonenumber);
            ordernumber=itemView.findViewById(R.id.ordernumber);
        }
    }
}


