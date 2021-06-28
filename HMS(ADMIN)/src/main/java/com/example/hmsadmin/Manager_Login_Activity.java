package com.example.hmsadmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Manager_Login_Activity extends AppCompatActivity {

     private Button mCategory,mUpdatemenu,mOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);



        mCategory=(Button) findViewById(R.id.menu_button);
        mUpdatemenu=(Button)findViewById(R.id.UpdateMenu_Button);
        mOrders=(Button)findViewById(R.id.order_button);

        //-----------------MENU-ACTIVITY-----------------------
        mCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(Manager_Login_Activity.this, Manager_CategoryActivity.class);
                startActivity(intent1);
            }
        });


        //-----------------UPDATE_MENU-ACTIVITY-----------------------
        mUpdatemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Manager_Login_Activity.this, Manager_ItemsActivity.class);
                startActivity(intent2);
            }
        });






        //-----------------ORDES-ACTIVITY-----------------------
        mOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Manager_Login_Activity.this,Manager_Orders_Activity.class);
                startActivity(intent1);
            }
        });




    }
}
