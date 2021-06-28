package com.example.hotelmanagmentapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.hotelmanagmentapplication.ModelClasses.CartModel;
import com.example.hotelmanagmentapplication.ModelClasses.Category_Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment
{
    private View cartView;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private TextView textView1,textView3,textView4,textView5;
    private int Quantity;
    private RecyclerView recyclerView;
    private int total=0,grandtotal=0;
    private String mSpecialInstruction,mName,mPrice,mQuantity;
    private Button btn;








    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View cartview = inflater.inflate(R.layout.fragment_cart, container, false);


        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        btn=(Button)cartview.findViewById(R.id.finalizeOrder);


        recyclerView = cartview.findViewById(R.id.cart_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn.setVisibility(View.INVISIBLE);
        btn.setEnabled(false);

        String currentUserID =  mAuth.getCurrentUser().getUid();






        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setEnabled(true);

        if (mRef.child(currentUserID) == null)
        {

            recyclerView.setVisibility(View.INVISIBLE);
            recyclerView.setEnabled(false);
            btn.setVisibility(View.INVISIBLE);
            btn.setEnabled(false);
        }
        else
        {

            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setEnabled(true);
            btn.setVisibility(View.VISIBLE);
            btn.setEnabled(true);
        }



        currentUserID =  mAuth.getCurrentUser().getUid();
        FirebaseRecyclerOptions<CartModel> options =
                new FirebaseRecyclerOptions.Builder<CartModel>()
                        .setQuery(mRef.child("Cart").child(currentUserID),CartModel.class)
                        .build();
        FirebaseRecyclerAdapter<CartModel,CartViewHolder> adapter = new FirebaseRecyclerAdapter<CartModel, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull final CartModel model)
            {
                holder.specialInstruction.setText(model.getSpecialInstruction());
                holder.name.setText(model.getItemName());
                holder.price.setText(String.valueOf(model.getPrice()));
                holder.quantity.setText(String.valueOf(model.getQuantity()));
                holder.subtotal.setText(String.valueOf(model.getPrice()*model.getQuantity()));
                total=model.getPrice()*model.getQuantity();
                grandtotal =grandtotal+ total;
                final String dispalyprice = String.valueOf(grandtotal);



                holder.itemnumber.setText(String.valueOf(holder.getAdapterPosition()+1));

                mSpecialInstruction=model.getSpecialInstruction();
                mName = model.getItemName();
                mPrice = String.valueOf(model.getPrice());
                mQuantity = String.valueOf(model.getQuantity());












                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Total Amount: "+grandtotal, Toast.LENGTH_SHORT).show();
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Cart Options");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                if(i==0)
                                {


                                    String currentUser =  mAuth.getCurrentUser().getUid();
                                    mRef.child("OrderProducts").child(model.getmPRID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                                    mRef.child("Cart").child(currentUser).child(model.getmPRID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {

                                            holder.itemnumber.setText(String.valueOf(holder.getAdapterPosition()+1));

                                            Toast.makeText(getActivity(), "Item Deleted Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getActivity(),e.getMessage() , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }


                            }
                        });
                        builder.show();



                        return false;
                    }
                });


            }


            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout3,parent,false);
                CartViewHolder viewHolder = new CartViewHolder(view);
                return viewHolder;
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final String dispalytotal = String.valueOf(grandtotal);
                ShipmentDialog shipmentDialog= new ShipmentDialog() ;
                shipmentDialog.show(getFragmentManager(),"MyShipmentDialog");

            }
        });




        return cartview;

    }



    public static class CartViewHolder extends RecyclerView.ViewHolder
    {
        TextView specialInstruction,name,price,quantity,subtotal,itemnumber;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            specialInstruction = itemView.findViewById(R.id.tv5);
            name = itemView.findViewById(R.id.tv2);
            price=itemView.findViewById(R.id.tv3);
            quantity=itemView.findViewById(R.id.tv4);
            subtotal=itemView.findViewById(R.id.tv1);
            itemnumber=itemView.findViewById(R.id.itemnumber);
        }




    }


}




