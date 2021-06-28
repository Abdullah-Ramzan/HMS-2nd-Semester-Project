package com.example.hotelmanagmentapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hotelmanagmentapplication.ModelClasses.Category_Model;
import com.example.hotelmanagmentapplication.ModelClasses.HorizontalModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayItem_Fragment extends Fragment
{
    private View DisplayView;
    private RecyclerView myItemList;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private StaggeredGridLayoutManager mManager;
    private StorageReference mStorageRef;
    String data;
    private FirebaseRecyclerAdapter<HorizontalModel, DisplayItem_Fragment.TestViewHolder> adapter;


    public DisplayItem_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DisplayView =  inflater.inflate(R.layout.fragment_displayitem, container, false);

        myItemList = (RecyclerView)DisplayView.findViewById(R.id.display_list);
        mManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        myItemList.setLayoutManager(mManager);

        mRef= FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("Categories");
        mAuth = FirebaseAuth.getInstance();


        String currentUserID= mAuth.getCurrentUser().getUid();
        mRef.child("Personal").child("Selected_Item").child(currentUserID).child("Current_Item").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                data = dataSnapshot.getValue().toString();
                HorizontalModel model = new HorizontalModel(data);


                FirebaseRecyclerOptions options =
                        new FirebaseRecyclerOptions.Builder<HorizontalModel>()
                                .setQuery(mRef.child("Menu").child(model.getmSelectedItem()),HorizontalModel.class).build();
                FirebaseRecyclerAdapter<HorizontalModel, DisplayItem_Fragment.TestViewHolder> adapter =
                        new FirebaseRecyclerAdapter<HorizontalModel, DisplayItem_Fragment.TestViewHolder>(options)
                        {


                            @Override
                            protected void onBindViewHolder(@NonNull final DisplayItem_Fragment.TestViewHolder holder, final int position, @NonNull final HorizontalModel model2)
                            {
                                holder.textView1.setText(model2.getmText1());
                                holder.textView2.setText(model2.getmText2());
                                Picasso.get().load(model2.getmImageUrl()).into(holder.imageView);
                                final String ImageUrl = model2.getmImageUrl();

                                holder.itemView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(getContext(), "Name: "+holder.textView1.getText(), Toast.LENGTH_SHORT).show();

                                        String item = holder.textView1.getText().toString();
                                        String price = holder.textView2.getText().toString();
                                        int intPrice = Integer.parseInt(price);

                                        Dialog_Fragment dialog_fragment= new Dialog_Fragment();
                                        dialog_fragment.show(getFragmentManager(),"MyGeneralDialog");

                                        Bundle bundle = new Bundle();
                                        bundle.putString("Item_Selected",item);
                                        bundle.putInt("Price",intPrice);
                                        dialog_fragment.setArguments(bundle);




                                    }
                                });



                            }

                            @NonNull
                            @Override
                            public DisplayItem_Fragment.TestViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
                                View view;
                                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout2,parent,false);
                                final DisplayItem_Fragment.TestViewHolder viewHolder = new DisplayItem_Fragment.TestViewHolder(view);


                                return viewHolder;
                            }
                        };

                adapter.startListening();
                myItemList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return DisplayView;
    }







    public static class TestViewHolder extends RecyclerView.ViewHolder
    {


        TextView textView1,textView2;
        ImageView imageView;

        public TestViewHolder(@NonNull View itemView)
        {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textview1);
            imageView = itemView.findViewById(R.id.image1);
            textView2 = itemView.findViewById(R.id.textview3);
        }
    }


}
