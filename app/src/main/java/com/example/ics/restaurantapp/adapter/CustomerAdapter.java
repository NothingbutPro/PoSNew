package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ics.restaurantapp.ModelDB.CustomerItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.util.ArrayList;

/**
 * Created by Ics on 8/6/2018.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<CustomerItem> list;

    public CustomerAdapter(Context context, ArrayList<CustomerItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_serach_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CustomerItem customerItem = list.get(position);
        holder.textName.setText(customerItem.getMobile_number());
        holder.textMob.setText(customerItem.getCustomer_name());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private TextView textMob;

        public MyViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.customer_name);
            textMob = itemView.findViewById(R.id.customer_mobile);
        }
    }
}
