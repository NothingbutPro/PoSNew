package com.example.ics.restaurantapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.model.Menu_Items;
import com.example.ics.restaurantapp.model.Menu_Items_For_Dine;
import com.example.ics.restaurantapp.model.Order_Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ics on 8/18/2018.
 */

public class AdapterFor_Dine extends RecyclerView.Adapter<AdapterFor_Dine.MyViewHolder> {
    private Context mContext;
    private ArrayList<Order_Item> order_items;

    public AdapterFor_Dine(Context activity, ArrayList<Order_Item> order_items) {
        mContext = activity;
        this.order_items = order_items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_list_item_dine, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order_Item order_item = order_items.get(position);
        holder.order_no.setText(order_item.getId());
        holder.receipt_no.setText(order_item.getBillNo());
        holder.dining_area.setText(order_item.getDinenigArea());
        holder.date_time.setText(order_item.getDateTime());
        holder.guest_name.setText(order_item.getCustomerName());
        holder.amount.setText(order_item.getTotalAmount());
        holder.payment.setText(order_item.getPaymentMethod());
        holder.due.setText(order_item.getWaiterName());
    }

    @Override
    public int getItemCount() {
        return order_items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView order_no, receipt_no, order_for, dining_area, date_time, guest_name, amount, due, payment;
        Button cancelOrder, printBill;

        public MyViewHolder(View itemView) {
            super(itemView);
            order_no = (TextView) itemView.findViewById(R.id.order_list_ser_no);
            receipt_no = (TextView) itemView.findViewById(R.id.order_list_receipt_no);
            payment = (TextView) itemView.findViewById(R.id.order_paymant_amount);
            dining_area = (TextView) itemView.findViewById(R.id.order_list_dining_for);
            date_time = (TextView) itemView.findViewById(R.id.order_list_date_time);
            guest_name = (TextView) itemView.findViewById(R.id.order_list_guest_name);
            amount = (TextView) itemView.findViewById(R.id.order_list_amount);
            due = (TextView) itemView.findViewById(R.id.order_list_order_by);
            cancelOrder = (Button) itemView.findViewById(R.id.order_list_cancel_order);
            printBill = (Button) itemView.findViewById(R.id.order_list_print_bill);

        }
    }
}
