package com.example.hotelmanagmentapplication;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
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

import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment
{
    private View TestView;
    private RecyclerView myTestList;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
   private StaggeredGridLayoutManager mManager;
    private StorageReference mStorageRef;
    FirebaseRecyclerAdapter<Category_Model,TestViewHolder> adapter;





    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TestView =  inflater.inflate(R.layout.fragment_test, container, false);

        myTestList = (RecyclerView)TestView.findViewById(R.id.test_list);
        mManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);
        myTestList.setLayoutManager(mManager);
        myTestList.setHasFixedSize(true);

        mRef= FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("Categories");
        mAuth = FirebaseAuth.getInstance();


        FirebaseRecyclerOptions options =
                new FirebaseRecyclerOptions.Builder<Category_Model>()
                        .setQuery(mRef.child("Categories"),Category_Model.class).build();

        FirebaseRecyclerAdapter<Category_Model,TestViewHolder> adapter =
                new FirebaseRecyclerAdapter<Category_Model, TestViewHolder>(options)
                {


                    @Override
                    protected void onBindViewHolder(@NonNull final TestViewHolder holder, final int position, @NonNull final Category_Model model2)
                    {
                        holder.textView1.setText(model2.getCategory_Name());
                        Picasso.get().load(model2.getCategory_Pic()).into(holder.imageView);
                        final String ImageUrl = model2.getCategory_Pic();

                        holder.textView1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {

                                Toast.makeText(getContext(), "Name: "+holder.textView1.getText(), Toast.LENGTH_SHORT).show();
                                getFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container,new DisplayItem_Fragment()).addToBackStack("Menu Screen")
                                        .commit();


                                String item= holder.textView1.getText().toString();
                                String currentUserID= mAuth.getCurrentUser().getUid();
                                mRef.child("Personal").child("Selected_Item").child(currentUserID).child("Current_Item").setValue(item);
                                HorizontalModel model = new HorizontalModel(item);


                                DisplayItem_Fragment displayItem_fragment= new DisplayItem_Fragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("Category",item);
                                displayItem_fragment.setArguments(bundle);


                            }
                        });
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(getContext(), "Name: "+holder.textView1.getText()+" Price: "+holder.textView2.getText(), Toast.LENGTH_SHORT).show();
//                                String currentUserID=mAuth.getCurrentUser().getUid();
//
//                                mRef.child("Cart").child(currentUserID).child("Name").setValue(holder.textView1.getText());
//                                mRef.child("Cart").child(currentUserID).child("Price").setValue(holder.textView2.getText());
//                                mRef.child("Cart").child(currentUserID).child("Name").setValue(ImageUrl);
//                               // Intent intent = new Intent(getContext(),Manager_UpdateMenu_Activity.class);
//                              //  startActivity(intent);
//
//                            }
//                        });


                    }

                    @NonNull
                    @Override
                    public TestViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
                        View view;
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
                        final TestViewHolder viewHolder = new TestViewHolder(view);


                        return viewHolder;
                    }
                };

        adapter.startListening();
        myTestList.setAdapter(adapter);

        return TestView;
    }



    public static class TestViewHolder extends RecyclerView.ViewHolder
    {


        TextView textView1;
        ImageView imageView;

        public TestViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textview1);
            imageView = itemView.findViewById(R.id.image1);
        }
    }
}
