package com.example.hotelmanagmentapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.hotelmanagmentapplication.ModelClasses.HorizontalModel;
import com.example.hotelmanagmentapplication.ModelClasses.UploadImage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterCustomerActivity extends AppCompatActivity
{
    private TextInputLayout mEmailInputLayout,mPasswordInputLayout,mName;
    private Button Registerbtn;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    private CircleImageView mProfieImage;
    private ImageButton mProfileImagebtn;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private static final int PICK_IMAGE_REQUEST =2;
    private StorageReference mStorageRef;
    private Uri mImageUri;
    private String currentUserID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        mEmailInputLayout = findViewById(R.id.textinputlayout_customeremail);
        mPasswordInputLayout = findViewById(R.id.textinputlayout_customerpassword);
        mName = findViewById(R.id. textinputlayout_customername);
       Registerbtn =(Button) findViewById(R.id.customer_registerbtn);
       mProfieImage = (CircleImageView) findViewById(R.id.circularimage);
        mProfileImagebtn = (ImageButton) findViewById(R.id.registerimagebtn);
        loadingBar=new ProgressDialog(this);

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Profile");
        mAuth= FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();






       Registerbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               if(!validateEmailAddress() | !validPassword() | !validName())
               {
                   return;
               }


               loadingBar.setTitle("CUSTOMER REGISTERATION");
               loadingBar.setMessage("Please wait while we are registering Your Data");
               loadingBar.show();

               String email= mEmailInputLayout.getEditText().getText().toString().trim();
               String password= mPasswordInputLayout.getEditText().getText().toString().trim();

               mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task)
                   {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(RegisterCustomerActivity.this, "REGISTER SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                           loadingBar.dismiss();
                           Intent intent = new Intent(RegisterCustomerActivity.this,Customer_Login_Activity.class);
                           startActivity(intent);
                       }
                       else {
                           if(task.getException()instanceof FirebaseAuthUserCollisionException)
                           {
                               mEmailInputLayout.setError("Email already in use.");
                               Toast.makeText(RegisterCustomerActivity.this, "Error: EMAIL ALREADY IN USE.", Toast.LENGTH_SHORT).show();
                           }
                           loadingBar.dismiss();
                       }
                   }
               });


               uploadProfile();




           }
       });


       mProfileImagebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openProfileChooser();
           }
       });

    }

    private void uploadProfile()
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

                            Toast.makeText(RegisterCustomerActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();



                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    final String downloadUrl =
                                            uri.toString();
                                    String customerName= mName.getEditText().getText().toString().trim();



                                    UploadImage model = new UploadImage(downloadUrl,customerName);

                                    String currentUserID=mAuth.getCurrentUser().getUid();

                                    mRef.child(currentUserID).child("ProfileImage").setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            loadingBar.dismiss();
                                            Toast.makeText(RegisterCustomerActivity.this,"Image Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                    mRef.child(currentUserID).child("CustomerName").setValue(customerName).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            loadingBar.dismiss();
                                            Toast.makeText(RegisterCustomerActivity.this,"Image Uploaded", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(RegisterCustomerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void openProfileChooser()
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



          //  Picasso.get().load(mImageUri).into(mImageview);



        }
    }


    private boolean validateEmailAddress()
    {
        String email= mEmailInputLayout.getEditText().getText().toString().trim();
        if(email.isEmpty())             //Using method isEmpty()
        {
            mEmailInputLayout.setError("Email is required. Can't be empty");                 //Setting up an error
            return false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmailInputLayout.setError("Invalid Email Address. Enter valid Email Address");
            return false;
        }
        else
        {
            mEmailInputLayout.setError(null);
            return true;
        }

    }


    private boolean validPassword()
    {
        String password= mPasswordInputLayout.getEditText().getText().toString().trim();
        if(password.isEmpty())
        {
            mPasswordInputLayout.setError("Password is required. Can't be empty");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_UPPERCASE_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one Upper-Case character is required.");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_LOWERCASE_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one Lower-Case character is required.");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_SPECIALCHARACTER_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one Special character is required.");
            return false;
        }
        else if(!PasswordUtils.PASSWORD_NUMBER_PATTERN.matcher(password).matches())
        {
            mPasswordInputLayout.setError("Password is weak.Mininmum one digit character is required.");
            return false;
        }


        else
        {
            mPasswordInputLayout.setError(null);
            return true;
        }

    }

    private boolean validName()
    {
        String name= mName.getEditText().getText().toString().trim();
        if(name.isEmpty())
        {
            mName.setError("Name is required. Can't be empty");
            return false;
        }
        return true;
    }


}
