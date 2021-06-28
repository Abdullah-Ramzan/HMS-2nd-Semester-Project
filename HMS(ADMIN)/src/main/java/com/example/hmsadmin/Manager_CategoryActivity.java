package com.example.hmsadmin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Manager_CategoryActivity extends AppCompatActivity
{
        private Button mAddButton,mAddImageButton;
    private TextInputLayout mCategoryName;
        private static final int PICK_IMAGE_REQUEST =2;
        private Uri imageUri;
        private StorageReference mStorageRef;
        private FirebaseDatabase mDatabase;
        private DatabaseReference mRef;
        private int count = 0;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_addcategory);

        mAddButton = (Button) findViewById(R.id.updateCategory);
        mAddImageButton = (Button) findViewById(R.id.Button_ChooseCategoryImage);
        mCategoryName = (TextInputLayout) findViewById(R.id.EdtitText_CategoryTitle);

        loadingBar=new ProgressDialog(this);


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
                String title=mCategoryName.getEditText().getText().toString();
                if (title.isEmpty())
                {
                    mCategoryName.setError("Title is required. Can't be empty");
                }
                else
                    {

                        loadingBar.setTitle("Category Updates");
                        loadingBar.setMessage("Please wait while we are Updating Your Category");
                        loadingBar.show();

                        uploadPhoto();
                }


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

                            Toast.makeText(Manager_CategoryActivity.this, "Image Uploaded Successfully", Toast.LENGTH_LONG).show();



                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    final String downloadUrl =
                                            uri.toString();



                                    String title=mCategoryName.getEditText().getText().toString();
                                    Category_Model model2 = new Category_Model(title,downloadUrl);
                                    mRef.push().setValue(model2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            loadingBar.dismiss();
                                            Toast.makeText(Manager_CategoryActivity.this,"Category Added", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Manager_CategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(Manager_CategoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
