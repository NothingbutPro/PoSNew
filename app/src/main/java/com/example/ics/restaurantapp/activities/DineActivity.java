package com.example.ics.restaurantapp.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.R;

public class DineActivity extends AppCompatActivity {
    TextView text,all_list,tet_food;
    LinearLayout lout,ordr;
    ListView list_all,listfood;
    Button btn_all,btn_food,btn_liqour;
    String[] listItem,foodlst;
    // Array of strings...
    /*String[] list = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine);

        text = (TextView)findViewById(R.id.text);
        lout = (LinearLayout)findViewById(R.id.lout);
        ordr = (LinearLayout)findViewById(R.id.ordr);
        btn_all = (Button)findViewById(R.id.btn_all);
        btn_food = (Button)findViewById(R.id.btn_food);
        btn_liqour = (Button)findViewById(R.id.btn_liqour);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             lout.setVisibility(View.VISIBLE);
            }
        });
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lout.setVisibility(View.GONE);
            }
        });

           FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(DineActivity.this,DineActivity.class);
              startActivity(intent);
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_liqour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        list_all=(ListView)findViewById(R.id.list_all);
        all_list=(TextView)findViewById(R.id.all_list);
        listItem = getResources().getStringArray(R.array.array_technology);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
              R.layout.list_all_item, R.id.all_list, listItem);
        list_all.setAdapter(adapter);

        list_all.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value=adapter.getItem(position);
                Toast.makeText(getApplicationContext(),value, Toast.LENGTH_SHORT).show();

            }
        });

        listfood=(ListView)findViewById(R.id.listfood);
        tet_food=(TextView)findViewById(R.id.tet_food);
        foodlst = getResources().getStringArray(R.array.array_food);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                R.layout.all_list_food, R.id.tet_food, foodlst);
        listfood.setAdapter(adapter1);

        listfood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
              //  String value=adapter.getItem(position);
                Intent intent = new Intent(DineActivity.this,DineActivity.class);
                intent.putExtra("listfood","tet_food");
                startActivity(intent);

            }
        });
    }

}
