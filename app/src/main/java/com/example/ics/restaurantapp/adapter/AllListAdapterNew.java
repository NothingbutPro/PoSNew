package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;

import java.util.List;

/**
 * Created by android on 5/31/2018.
 */

class AllListViewholder extends RecyclerView.ViewHolder{

    TextView order_no,receipt_no,order_for,dining_area,date_time,guest_name,amount,due;
    Button cancelOrder,printBill;

    public AllListViewholder(View itemView) {
        super(itemView);
        order_no = (TextView)itemView.findViewById(R.id.order_list_order_no);
        receipt_no = (TextView)itemView.findViewById(R.id.order_list_receipt_no);
        order_for = (TextView)itemView.findViewById(R.id.order_list_order_for);
        dining_area = (TextView)itemView.findViewById(R.id.order_list_order_dining_area);
        date_time = (TextView)itemView.findViewById(R.id.order_list_date_time);
        guest_name = (TextView)itemView.findViewById(R.id.order_list_guest_name);
        amount = (TextView)itemView.findViewById(R.id.order_list_amount);
        due = (TextView)itemView.findViewById(R.id.order_list_order_by);
        cancelOrder = (Button)itemView.findViewById(R.id.order_list_cancel_order);
        printBill = (Button)itemView.findViewById(R.id.order_list_print_bill);
    }
}

public class AllListAdapterNew extends RecyclerView.Adapter<AllListViewholder> {

    Context mContext;
    List<orderListItem> list;

    public AllListAdapterNew(Context mContext, List<orderListItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public AllListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item,parent,false);
        return new AllListViewholder(view);
    }

    @Override
    public void onBindViewHolder(AllListViewholder holder, int position) {
        holder.order_no.setText(String.valueOf(list.get(position).getOrder_no()));
        holder.receipt_no.setText(String.valueOf(list.get(position).getReceipt_no()));
        holder.order_for.setText(list.get(position).getOrder_for());
        holder.dining_area.setText(list.get(position).getDining_area());
        holder.date_time.setText(list.get(position).getDate_time());
        holder.guest_name.setText(list.get(position).getGuest_name());
        holder.amount.setText(String .valueOf(list.get(position).getAmount()));
        holder.due.setText(String.valueOf(list.get(position).getWaiter_name()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
