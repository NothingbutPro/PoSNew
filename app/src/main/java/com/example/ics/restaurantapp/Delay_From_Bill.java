package com.example.ics.restaurantapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ics.restaurantapp.activities.BillSectionActivity;
import com.example.ics.restaurantapp.activities.NewActivity;

public class Delay_From_Bill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay__from__bill);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Delay_From_Bill.this, NewActivity.class);
                // i.putExtra("Check", count);
                startActivity(i);
                finish();
            }
        }, 1000);
    }
}
