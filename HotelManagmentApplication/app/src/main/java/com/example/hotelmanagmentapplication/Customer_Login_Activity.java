package com.example.hotelmanagmentapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class Customer_Login_Activity extends AppCompatActivity
{
    private TextInputLayout mEmailInputLayout,mPasswordInputLayout;
    private Button loginButton;
    private TextView registerlink,loginstatus;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mEmailInputLayout = findViewById(R.id.textinputlayout_email);
        mPasswordInputLayout = findViewById(R.id.textinputlayout_password);
        loginButton = findViewById(R.id.btn_login);
        registerlink=findViewById(R.id.tv_registerlink);
        loginstatus=findViewById(R.id.tv_login_status);
        mAuth=FirebaseAuth.getInstance();



        loadingBar=new ProgressDialog(this);

        registerlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Customer_Login_Activity.this,RegisterCustomerActivity.class);
                startActivity(intent);

            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!validateEmailAddress() | !validPassword())
                {
                    return;
                }
                String email= mEmailInputLayout.getEditText().getText().toString().trim();
                String password= mPasswordInputLayout.getEditText().getText().toString().trim();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Customer_Login_Activity.this, "Login Successfully", Toast.LENGTH_SHORT).show();


                            // Add INTENT HERE
                            Intent intent = new Intent(Customer_Login_Activity.this,Customer_Lobby_Activity.class);
                            startActivity(intent);


                        }
                        else
                        {
                            if(task.getException()instanceof FirebaseAuthInvalidCredentialsException)           //If the Password doesn't matches with the Firebase Authentication Database
                            {
                                mPasswordInputLayout.setError("Invalid Password");
                                Toast.makeText(Customer_Login_Activity.this, "Error: Invalid Password", Toast.LENGTH_SHORT).show();
                            }
                            else if(task.getException()instanceof FirebaseAuthInvalidUserException)           //If no such user exist as given
                            {
                                mEmailInputLayout.setError("Email not in use");
                                Toast.makeText(Customer_Login_Activity.this, "Error: EMAIL NOT IN USE", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

            }
        });

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

}
