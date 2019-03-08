package com.example.ics.restaurantapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.tableItem;
import com.example.ics.restaurantapp.ModelDB.waiterItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.activities.DrawerActivity;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.util.ArrayList;

/**
 * Created by ICS on 15/03/2018.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList <tableItem>arrayList1stFloor;
    LayoutInflater inflter;
    DatabaseHelper helper;

    public CustomAdapter(Context applicationContext, ArrayList<tableItem> arrayList1stfloor) {
        this.context = applicationContext;
        this.arrayList1stFloor=arrayList1stfloor;
        inflter = (LayoutInflater.from(applicationContext));
        helper = new DatabaseHelper(applicationContext);

    }
    @Override
    public int getCount() {
        return arrayList1stFloor.size();
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
        view = inflter.inflate(R.layout.grid_row, null); // inflate the layout

        TextView icon = (TextView) view.findViewById(R.id.row_view); // get the reference of ImageView
        TextView splitTable1 = (TextView)view.findViewById(R.id.splitTable1);
        TextView splitTable2 = (TextView)view.findViewById(R.id.splitTable2);

        LinearLayout table = (LinearLayout)view.findViewById(R.id.table);
        LinearLayout split_table = (LinearLayout)view.findViewById(R.id.split_table);


        if(arrayList1stFloor.get(i).getSplitted().equals("yes")){
            split_table.setVisibility(View.VISIBLE);
            table.setVisibility(View.GONE);
            view.findViewById(R.id.splitTable1).setBackgroundResource(R.drawable.btn_back);
            ((TextView) view.findViewById(R.id.splitTable1)).setTextColor(Color.WHITE);
            view.findViewById(R.id.splitTable2).setBackgroundResource(R.drawable.btn_back);
            ((TextView) view.findViewById(R.id.splitTable2)).setTextColor(Color.WHITE);
            splitTable1.setText(arrayList1stFloor.get(i).getT_name());
            splitTable2.setText(arrayList1stFloor.get(i).getT_name());
            variables.selecetd_table_data = arrayList1stFloor.get(i);
            splitTable1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Split Table 1", Toast.LENGTH_SHORT).show();
                    variables.tableNumber = "1";
                    if(!variables.selecetd_table_data.getWaiter_id().equals("N/A")) {
                        Cursor waiterCursor = helper.getWaiterDetails(variables.selecetd_table_data.getWaiter_id());
                        if (waiterCursor.getCount() != 0) {
                            waiterCursor.moveToNext();
                            variables.selected_waiter_data = new waiterItem(
                                    waiterCursor.getString(0),
                                    waiterCursor.getString(1),
                                    waiterCursor.getString(2),
                                    waiterCursor.getString(3),
                                    waiterCursor.getString(4),
                                    waiterCursor.getString(5));
                        }
                    }else {
                        variables.selected_waiter_data = new waiterItem(
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A"
                        );
                    }
                    context.startActivity(new Intent(context,NewActivity.class));
                }
            });

            splitTable2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!variables.selecetd_table_data.getWaiter_id().equals("N/A")) {
                        Cursor waiterCursor = helper.getWaiterDetails(variables.selecetd_table_data.getWaiter_id());
                        if (waiterCursor.getCount() != 0) {
                            waiterCursor.moveToNext();
                            variables.selected_waiter_data = new waiterItem(
                                    waiterCursor.getString(0),
                                    waiterCursor.getString(1),
                                    waiterCursor.getString(2),
                                    waiterCursor.getString(3),
                                    waiterCursor.getString(4),
                                    waiterCursor.getString(5));
                        }
                    }else{
                        variables.selected_waiter_data = new waiterItem(
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A"

                        );
                    }
                    variables.tableNumber = "2";
                    context.startActivity(new Intent(context, NewActivity.class));
                    ((Activity)context).finish();
                }
            });


            //view.setBackgroundResource(R.drawable.btn_back);
        }else {
            icon.setText(arrayList1stFloor.get(i).getT_name());
            if(!arrayList1stFloor.get(i).getStatus().equals("free")) {
                view.findViewById(R.id.row_view).setBackgroundResource(R.drawable.btn_back);
                ((TextView) view.findViewById(R.id.row_view)).setTextColor(Color.WHITE);
            }else {
                view.findViewById(R.id.row_view).setBackgroundResource(R.drawable.btn_shp);
                ((TextView) view.findViewById(R.id.row_view)).setTextColor(Color.BLACK);
            }
        }

        return view;
    }
}
