package com.example.hotelmanagmentapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class  Splash_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
              try
              {
               sleep(3000);
              }
              catch (Exception e)
              {
                  e.printStackTrace();
              }
              finally
              {
                  Intent intent = new Intent(Splash_Activity.this,IntroActivity.class);
                  startActivity(intent);

              }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
