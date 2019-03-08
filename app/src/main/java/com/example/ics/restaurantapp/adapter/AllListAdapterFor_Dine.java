package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.model.Order_Item;

import java.util.List;

public class AllListAdapterFor_Dine extends RecyclerView.Adapter<AllListViewholder> {

    Context mContext;
    List<Order_Item> list;

    public AllListAdapterFor_Dine(Context mContext, List<Order_Item> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public AllListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item_dine,parent,false);
        return new AllListViewholder(view);
    }

    @Override
    public void onBindViewHolder(AllListViewholder holder, int position) {
        holder.order_no.setText(String.valueOf(list.get(position).getId()));
//        holder.receipt_no.setText(String.valueOf(list.get(position).getReceipt_no()));
//        holder.order_for.setText(list.get(position).get());
        holder.dining_area.setText(list.get(position).getDinenigArea());
        holder.date_time.setText(list.get(position).getDateTime());
        holder.guest_name.setText(list.get(position).getCustomerName());
        holder.amount.setText(String .valueOf(list.get(position).getTotalAmount()));
        holder.due.setText(String.valueOf(list.get(position).getWaiterName()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
