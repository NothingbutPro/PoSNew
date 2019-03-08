package com.example.ics.restaurantapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ics.restaurantapp.ModelDB.kitchenOrderItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.activities.BillSectionActivity;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ICS on 10/04/2018.
 */

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {

    private static final String TAG = "BillItemAdapter";
    private ArrayList<kitchenOrderItem> billitemlist;
    public Context context;
    String Qunty,Price;
    private int val,v4=0,v5,v6;
    private String Total="";

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView serial_number,itm_name,qunty,price,list_total;
      //  CardView card_view;
        int pos;

        public ViewHolder(View view) {
            super(view);

            serial_number = (TextView) view.findViewById(R.id.serial_number);
            itm_name = (TextView) view.findViewById(R.id.itm_name);
            qunty = (TextView) view.findViewById(R.id.qunty);
            price = (TextView) view.findViewById(R.id.price);
            list_total = (TextView) view.findViewById(R.id.list_total);
          //  card_view = (CardView) view.findViewById(R.id.card_view);
        }
    }

    public static Context mContext;
    public BillItemAdapter(Context mContext, ArrayList<kitchenOrderItem> bill_item_list) {
        context = mContext;
        billitemlist = bill_item_list;

    }

    @Override
    public BillItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bill_item, parent, false);

        return new BillItemAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BillItemAdapter.ViewHolder viewHolder, int position) {
        int no = position;
        int no2= ++no;
        //final Map<String, String> mMap = billitemlist.get(position);
        viewHolder.serial_number.setText(String.valueOf(billitemlist.get(position).getSerial()));
        viewHolder.itm_name.setText(billitemlist.get(position).getMname());
        viewHolder.price.setText(String.valueOf(billitemlist.get(position).getRate()));
        viewHolder.qunty.setText(String.valueOf(billitemlist.get(position).getQuantity()));
        viewHolder.list_total.setText(String.valueOf(billitemlist.get(position).getTotalPrice()));
       // viewHolder.card_view.setTag(viewHolder);
        viewHolder.pos = position;

//        Qunty = viewHolder.qunty.getText().toString().trim();
//        Price = mMap.get("item_rate1");
//
//        val = Integer.parseInt(Qunty) * Integer.parseInt(Price);
//        viewHolder.list_total.setText(Integer.toString(val));
//        viewHolder.price.setText(String.valueOf(val));
//
//        v4=v4+Integer.parseInt(Price);
//        Total = String.valueOf((v4));
//        BillSectionActivity.tatal_amount.setText(Total);
//        BillSectionActivity.amount.setText(Total);

    }

    @Override
    public int getItemCount() {
        return billitemlist.size();
    }
}
