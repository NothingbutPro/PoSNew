package com.example.ics.restaurantapp.activities;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.adapter.TakeAway_Adapter;

import java.util.ArrayList;
import java.util.HashMap;

public class TakeAwayActivity extends Activity {
    RecyclerView recycler_takeaway;
    Toolbar toolbar_take_away;
    private TakeAway_Adapter takeAway_adapter;
    ArrayList<HashMap<String, String>> takeaway_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_away);

        toolbar_take_away = (Toolbar) findViewById(R.id.toolbar_take_away);

        toolbar_take_away.setNavigationIcon(R.drawable.ic_arrow);

        toolbar_take_away.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*takeAway_adapter = new TakeAway_Adapter(TakeAwayActivity.this, takeaway_list);
        recycler_takeaway = (RecyclerView) findViewById(R.id.recycler_takeaway);
        recycler_takeaway.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TakeAwayActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_takeaway.setLayoutManager(linearLayoutManager);
        recycler_takeaway.setAdapter(takeAway_adapter);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
