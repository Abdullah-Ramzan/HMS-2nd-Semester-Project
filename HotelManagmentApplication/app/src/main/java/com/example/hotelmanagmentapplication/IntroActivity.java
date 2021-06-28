package com.example.hotelmanagmentapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;


public class IntroActivity extends AppIntro {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //First Slider
        addSlide(AppIntroFragment.newInstance("Welcome to HMS", "Amzaing Application as a Project for Homemade food as well as for Commercial Hotel/Cafe Sale with easy and amazing management features",
                R.drawable.firsthotel, ContextCompat.getColor(getApplicationContext(),R.color.Color)));
        //Second Slider
        addSlide(AppIntroFragment.newInstance("On-Demand Application", "This App uses Firebase Database features and also OOP concepts for Management process",
                R.drawable.secondrider, ContextCompat.getColor(getApplicationContext(),R.color.secondColor)));




        //Initialization
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPreference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if(sharedPreferences!=null)
        {
            boolean checkShared = sharedPreferences.getBoolean("Checked",false);
            if(checkShared==true)
            {
                Intent intent = new Intent(IntroActivity.this, Customer_Login_Activity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent intent = new Intent(IntroActivity.this, Customer_Login_Activity.class);
        startActivity(intent);
        editor.putBoolean("Checked",false).commit();
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent intent = new Intent(IntroActivity.this, Customer_Login_Activity.class);
        startActivity(intent);
        editor.putBoolean("Checked",true).commit();
        finish();
    }


}
