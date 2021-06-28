package com.example.hmsadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity
{

    private Button mAddButton,mAddImageButton;
    private EditText mCategoryName;
    private static final int PICK_IMAGE_REQUEST =2;
    private Uri imageUri;
    private StorageReference mStorageRef;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddButton = (Button) findViewById(R.id.Button_UpdateCategory);
        mAddImageButton = (Button) findViewById(R.id.Button_ChooseCategoryImage);
        mCategoryName = (EditText) findViewById(R.id.EdtitText_CategoryTitle);




        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference().child("Categories");
        mStorageRef = FirebaseStorage.getInstance().getReference("Categories");

        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });

    }

    private void uploadPhoto()
    {
        if(imageUri !=null)
        {

            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    +"."+ getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                            Toast.makeText(MainActivity.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();



                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    final String downloadUrl =
                                            uri.toString();
                                    String title=mCategoryName.getText().toString();


                                    Category_Model model2 = new Category_Model(title,downloadUrl);

                                    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//                                    mRef.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if(dataSnapshot.exists())
//                                            {
//                                                count = (int) dataSnapshot.getChildrenCount();
//
//
//                                            }else {}
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });


                                    // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


                                    mRef.push().setValue(model2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Toast.makeText(MainActivity.this,"Category Added", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
        else
        {
            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR =getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openImageChooser()
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
            imageUri = data.getData();
        }
    }
}
