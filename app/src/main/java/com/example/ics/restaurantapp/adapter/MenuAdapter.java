package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;

import java.util.ArrayList;

/**
 * Created by ICS on 20/03/2018.
 */

public class MenuAdapter extends BaseAdapter {
    Context context;
    String logos[];
    ArrayList <String>menu1List;
    LayoutInflater inflter;

    public MenuAdapter(Context applicationContext, ArrayList<String> menu1list) {
        this.context = applicationContext;
        this.menu1List = menu1list;

        this.logos = logos;

        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return menu1List.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.item_grid, null); // inflate the layout
        TextView itm = (TextView) view.findViewById(R.id.itm); // get the reference of ImageView
        itm.setText(menu1List.get(i)); // set logo images
        return view;
    }

}
