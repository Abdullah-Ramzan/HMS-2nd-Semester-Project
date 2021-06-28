package com.example.hmsadmin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Manager_ItemsActivity extends AppCompatActivity {

    private EditText mTitle,mPrice;
    private Button mUpdate;
    private Button mChooseImage;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private static final int PICK_IMAGE_REQUEST =1;
    private StorageReference mStorageRef;
    private Uri mImageUri;
    private Spinner mSpinner;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String mCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_items);


        mUpdate=(Button) findViewById(R.id.UpdateChange);
        mChooseImage=(Button) findViewById(R.id.ChooseImage);
        mPrice = (EditText) findViewById(R.id.MenuPrice);
        mTitle = (EditText) findViewById(R.id.MenuTitle);
        mSpinner =findViewById(R.id.spinner);



        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference();
        mAuth= FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("Users");

        loadingBar=new ProgressDialog(this);

        showCategories();

        mUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {




                        loadingBar.setTitle("Category Updates");
                        loadingBar.setMessage("Please wait while we are Updating Your Category");
                        loadingBar.show();
                        uploadFile();




            }
        });

        mChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

    }

    private void showCategories()
    {
        mRef.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                arrayList.clear();
                for(DataSnapshot category:dataSnapshot.getChildren())
                {
                    arrayList.add(category.child("category_Name").getValue().toString());

                }





               ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Manager_ItemsActivity.this,android.R.layout.simple_spinner_dropdown_item,arrayList);
                mSpinner.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       mCategory= arrayList.get(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(Manager_ItemsActivity.this, "Select Any Category", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private String getFileExtension(Uri uri)
    {
        ContentResolver cR =getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile()
    {
        if(mImageUri !=null)
        {

            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
            +"."+ getFileExtension(mImageUri));

            fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                            Toast.makeText(Manager_ItemsActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();



                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    final String downloadUrl =
                                            uri.toString();
                                    String title=mTitle.getText().toString();
                                    String price=mPrice.getText().toString();
                                    String ProductRandomKey=mRef.push().getKey().toString();

                                    HorizontalModel model = new HorizontalModel(downloadUrl,title,price,ProductRandomKey);

                                    mRef.child("Menu").child(mCategory).child(ProductRandomKey).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            loadingBar.dismiss();
                                            mTitle.setText("");
                                            mPrice.setText("");
                                            Toast.makeText(Manager_ItemsActivity.this,"Item Added", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Manager_ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });






                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(Manager_ItemsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
            {
                Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
            }
    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() !=null)
        {
            mImageUri = data.getData();
        }
    }
}
