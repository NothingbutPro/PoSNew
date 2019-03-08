package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.model.Menu_Detail;
import com.example.ics.restaurantapp.model.Order_Item;

import java.util.ArrayList;

/**
 * Created by Ics on 8/18/2018.
 */

public class AdapterFor_MenuItem extends RecyclerView.Adapter<AdapterFor_MenuItem.MyViewHolder> {
    private Context mContext;
    private ArrayList<Menu_Detail> menu_details;

    public AdapterFor_MenuItem(Context activity, ArrayList<Menu_Detail> menu_detail) {
        mContext = activity;
        this.menu_details = menu_detail;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_for_menu_row, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Menu_Detail menu_detail = menu_details.get(position);
        holder.menu_Nnme.setText(menu_detail.getMenuName());
        holder.menu_Qty.setText(menu_detail.getQty());
        holder.menu_Rate.setText(menu_detail.getRate());
        holder.menu_Tax.setText(menu_detail.getTax());
        holder.menu_Amo.setText(menu_detail.getAmount());
    }

    @Override
    public int getItemCount() {
        return menu_details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menu_Nnme, menu_Qty, menu_Rate, menu_Tax, menu_Amo;

        public MyViewHolder(View itemView) {
            super(itemView);
            menu_Nnme = (TextView) itemView.findViewById(R.id.menu_name);
            menu_Qty = (TextView) itemView.findViewById(R.id.menu_qty);
            menu_Rate = (TextView) itemView.findViewById(R.id.menu_rate);
            menu_Tax = (TextView) itemView.findViewById(R.id.menu_tax);
            menu_Amo = (TextView) itemView.findViewById(R.id.menu_amo);
        }
    }
}
