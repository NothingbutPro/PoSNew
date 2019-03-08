package com.example.ics.restaurantapp.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ics.restaurantapp.R;

public class DinelistActivity extends Activity {
    Toolbar toolbar_dine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_item_list);

        toolbar_dine = (Toolbar) findViewById(R.id.toolbar_dine);

        toolbar_dine.setNavigationIcon(R.drawable.ic_arrow);

        toolbar_dine.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
