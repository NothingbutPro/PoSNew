package com.example.ics.restaurantapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;

public class BillActivity extends AppCompatActivity {
    TextView itemname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        itemname = (TextView)findViewById(R.id.itemname);

        Intent intent = getIntent();
        String dddd = intent.getStringExtra("listfood");

      /*  itemname.setText(dddd);*/

    }
}
