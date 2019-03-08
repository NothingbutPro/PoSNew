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

import com.example.ics.restaurantapp.ModelDB.DayCloseItem;
import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ICS on 06/06/2018.
 */

public class DaycloseAdapter extends RecyclerView.Adapter<DaycloseAdapter.ViewHolder> {

    private static final String TAG = "DaycloseAdapter";
    private List<DayCloseItem> daycloselist;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderNo,orderFor,dinningArea,dateTime,guestName,amountDclose,dueDclose,statusDclose,orderBy;
        CardView card_view;
        int pos;

        public ViewHolder(View view) {
            super(view);

            orderNo = (TextView) view.findViewById(R.id.orderNo);
            orderFor = (TextView) view.findViewById(R.id.orderFor);
            dinningArea = (TextView) view.findViewById(R.id.dinningArea);
            dateTime = (TextView) view.findViewById(R.id.dateTime);
            guestName = (TextView) view.findViewById(R.id.guestName);
            amountDclose = (TextView) view.findViewById(R.id.amountDclose);
            dueDclose = (TextView) view.findViewById(R.id.dueDclose);
            statusDclose = (TextView) view.findViewById(R.id.statusDclose);
            orderBy = (TextView) view.findViewById(R.id.orderBy);
            card_view = (CardView) view.findViewById(R.id.dayCloseCardView);
        }
    }

    public DaycloseAdapter(Context mContext, List<DayCloseItem> dayclose_list) {
        context = mContext;
        daycloselist = dayclose_list;

    }

    @Override
    public DaycloseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.daycloserow, parent, false);

        return new DaycloseAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DaycloseAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.orderNo.setText(String.valueOf(daycloselist.get(position).getOrderNo()));
        viewHolder.orderFor.setText(daycloselist.get(position).getOrderFor());
        viewHolder.dinningArea.setText(daycloselist.get(position).getDineArea());
        viewHolder.dateTime.setText(daycloselist.get(position).getDateTime());
        viewHolder.guestName.setText(daycloselist.get(position).getGuestName());
        viewHolder.amountDclose.setText(String .valueOf(daycloselist.get(position).getAmount()));
        viewHolder.dueDclose.setText(String .valueOf(daycloselist.get(position).getDue()));
        viewHolder.statusDclose.setText(daycloselist.get(position).getStatus());
        viewHolder.orderBy.setText(daycloselist.get(position).getOrderBy());
        viewHolder.card_view.setTag(viewHolder);
        viewHolder.pos = position;

        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return daycloselist.size();
    }

}
