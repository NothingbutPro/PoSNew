package com.example.ics.restaurantapp.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.DayCloseItem;
import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.adapter.DaycloseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DayCloseActivity extends AppCompatActivity {
    Toolbar toolbar_day_close;
    RecyclerView recyclerDayclose;
    private DaycloseAdapter daycloseAdapter;
    List<DayCloseItem> dayclose_list;
    Button btn_next;
    OrderDatabseHelper orderDatabseHelper;
    DatabaseHelper helper;
    private String strQuantity;
    private String strTableNo;
    private String strWaiterName;
    private String strItem;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_close);

        btn_next = (Button) findViewById(R.id.btn_next);

        dayclose_list = new ArrayList<>();

        orderDatabseHelper = new OrderDatabseHelper(this);
        helper = new DatabaseHelper(this);

        Cursor dayCloseCursor = orderDatabseHelper.getRunningOrderList();

        while (dayCloseCursor.moveToNext()) {
            String Wname;
            Cursor cursor = helper.getWaiterDetails(dayCloseCursor.getString(14));
            if (cursor.getCount() != 0) {
                cursor.moveToNext();
                Wname = cursor.getString(1);
            } else {
                Wname = "N/A";
            }
            dayclose_list.add(new DayCloseItem(
                    dayCloseCursor.getInt(0),
                    dayCloseCursor.getString(2),
                    dayCloseCursor.getString(3),
                    dayCloseCursor.getString(4),
                    dayCloseCursor.getString(5),
                    dayCloseCursor.getString(8),
                    Wname,
                    dayCloseCursor.getInt(6),
                    dayCloseCursor.getInt(7)
            ));
        }


        toolbar_day_close = (Toolbar) findViewById(R.id.toolbar_day_close);

        toolbar_day_close.setNavigationIcon(R.drawable.ic_arrow);

        toolbar_day_close.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (dayclose_list.size() > 0) {
            btn_next.setVisibility(View.INVISIBLE);
        } else {
            btn_next.setVisibility(View.VISIBLE);
        }
        daycloseAdapter = new DaycloseAdapter(DayCloseActivity.this, dayclose_list);
        recyclerDayclose = (RecyclerView) findViewById(R.id.recyclerDayclose);
        recyclerDayclose.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DayCloseActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerDayclose.setLayoutManager(linearLayoutManager);
        recyclerDayclose.setAdapter(daycloseAdapter);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor localModifiedDatabase = orderDatabseHelper.getCancelledModifiedData();
                while (localModifiedDatabase.moveToNext()) {
                    count++;
                    Log.e("Parag", count+"");
                    /*strTableNo = localModifiedDatabase.getString(0);
                    strItem = localModifiedDatabase.getString(2);
                    strQuantity = localModifiedDatabase.getString(4);
                    strWaiterName = localModifiedDatabase.getString(0);*/

                }
                Toast.makeText(DayCloseActivity.this, "Sending Modified data to server", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //----------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
