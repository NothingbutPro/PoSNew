package com.example.ics.restaurantapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ics.restaurantapp.R;

public class MainActivity extends AppCompatActivity {
  /*  String U_id = "";
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";*/

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        U_id = sharedPreferences.getString("flag", null);

        if (U_id == null) {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {

                            Intent i=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(i);
                            finish();

                        }
                    }, 1600);

                }
            }, 2000);

        } else {
            Intent intent = new Intent(MainActivity.this, DrawerActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
