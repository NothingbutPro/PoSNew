package com.example.ics.restaurantapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.waiterItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.SharedPreference.SessionManager;
import com.example.ics.restaurantapp.activities.DineActivity;
import com.example.ics.restaurantapp.activities.DrawerActivity;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ICS on 10/03/2018.
 */

public class WaiterAdapter extends RecyclerView.Adapter<WaiterAdapter.ViewHolder> {

    private static final String TAG = "WaiterAdapter";
    private ArrayList<waiterItem> waiterlist;
    public Context context;
    DatabaseHelper helper;
    private SessionManager sessionManager;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView waiter_name;
        CardView card_view;
        int pos;

        public ViewHolder(View view) {
            super(view);

            waiter_name = (TextView) view.findViewById(R.id.waiter_name);
            card_view = (CardView) view.findViewById(R.id.card_view);
        }
    }

    public static Context mContext;

    public WaiterAdapter(Context mContext, ArrayList<waiterItem> w_list) {
        context = mContext;
        waiterlist = w_list;
        helper = new DatabaseHelper(context);
        sessionManager = new SessionManager(context);

    }

    @Override
    public WaiterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wlist, parent, false);

        return new WaiterAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WaiterAdapter.ViewHolder viewHolder, final int position) {
        viewHolder.waiter_name.setText(waiterlist.get(position).getW_name());
        viewHolder.card_view.setTag(viewHolder);
        viewHolder.pos = position;

        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variables.selected_waiter_data = waiterlist.get(position);
                helper.updateTableWaiterID(variables.selecetd_table_data.getT_id(), variables.selected_waiter_data.getWaiter_id());
                String n = waiterlist.get(position).getW_name();
                String Id = waiterlist.get(position).getW_id();
                sessionManager.setWaiterDetails(n,Id);
                Intent intent = new Intent(context, NewActivity.class);
                intent.putExtra("waiterlist", waiterlist.get(position).getW_name());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return waiterlist.size();
    }

}
