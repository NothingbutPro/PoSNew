package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.cardOrderItem;
import com.example.ics.restaurantapp.ModelDB.foodDetailsItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ics.restaurantapp.activities.NewActivity.kot_items_bill_nodes;

/**
 * Created by ICS on 22/03/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    String Quantity = "", Rate, Total;

    private ArrayList<HashMap<String, String>> cardItemlist;
    public Context context;
    public CardAdapter arrayAdapter;
    public cardOrderItem selectedFood;
    public List<cardOrderItem> allSelectedFoodList;

    private int value, v4 = 0, v5, v6;
    private String totalPrice = "";
    private int counter = 0;
    private String S;
    private OrderDatabseHelper orderDatabseHelper;

    private static final String TAG = "CardAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button save_and_send;
        public TextView item_name, rate, total, item_id, item_dummy_rate, item_dummy_sgst_tax, item_dummy_cgst_tax, item_dummy_igst_tax, item_sgst_tax, item_cgst_tax, item_igst_tax, item_vat_tax, item_dummy_vat_tax;
        public ImageView delete, plus, minus;

        TextView quantity;
        //   CardView card_view;
        int pos;

        public ViewHolder(View view) {
            super(view);
            //counter = +1;
            save_and_send = ( Button)view.findViewById(R.id.save_and_send_kitchan);
            item_name = (TextView) view.findViewById(R.id.item_name);
            quantity = (TextView) view.findViewById(R.id.quantity);
            rate = (TextView) view.findViewById(R.id.rate);
            delete = (ImageView) view.findViewById(R.id.delete);
            plus = (ImageView) view.findViewById(R.id.plus_);
            minus = (ImageView) view.findViewById(R.id.minus_);
            item_id = (TextView) view.findViewById(R.id.item_id);
            item_dummy_rate = (TextView) view.findViewById(R.id.item_dummy_rate);
            item_dummy_sgst_tax = (TextView) view.findViewById(R.id.item_dummy_sgst_tax);
            item_dummy_cgst_tax = (TextView) view.findViewById(R.id.item_dummy_cgst_tax);
            item_dummy_igst_tax = (TextView) view.findViewById(R.id.item_dummy_igst_tax);
            item_sgst_tax = (TextView) view.findViewById(R.id.item_sgst_tax);
            item_cgst_tax = (TextView) view.findViewById(R.id.item_cgst_tax);
            item_igst_tax = (TextView) view.findViewById(R.id.item_igst_tax);
            item_dummy_vat_tax = (TextView) view.findViewById(R.id.item_dummy_vat_tax);
            item_vat_tax = (TextView) view.findViewById(R.id.item_vat_tax);
            //total=(TextView)view.findViewById(R.id.txt_total);
            //  card_view = (CardView) view.findViewById(R.id.card_view);


//            int value = AppPrefences.getInt("your_int_key");

            //Now it will return "10"
        }
    }

    public static Context mContext;

    public CardAdapter(Context mContext, List<cardOrderItem> allFoofList) {
        context = mContext;
        allSelectedFoodList = allFoofList;
        orderDatabseHelper = new OrderDatabseHelper(mContext);

    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new CardAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder viewHolder, final int position) {
//        final Map<String, String> mMap = cardItemlist.get(position);
//        viewHolder.item_name.setText(mMap.get("m_name"));
//        viewHolder.rate.setText(mMap.get("item_rate1"));
        selectedFood = allSelectedFoodList.get(position);
        viewHolder.item_name.setText(selectedFood.getMname());
        viewHolder.rate.setText(String.valueOf(selectedFood.getRate()));
        viewHolder.item_id.setText(selectedFood.getMid());
        viewHolder.delete.setTag(viewHolder);
        viewHolder.plus.setTag(viewHolder);
        viewHolder.minus.setTag(viewHolder);
        viewHolder.pos = position;
        viewHolder.quantity.setText(String.valueOf(selectedFood.getQuantity()));
        viewHolder.item_dummy_rate.setText(String.valueOf(selectedFood.getRate()));
        viewHolder.item_dummy_sgst_tax.setText(String.valueOf(selectedFood.getTotalSgstTax()));
        viewHolder.item_dummy_cgst_tax.setText(String.valueOf(selectedFood.getTotalCgstTax()));
        viewHolder.item_dummy_igst_tax.setText(String.valueOf(selectedFood.getTotalIgstTax()));
        viewHolder.item_sgst_tax.setText(String.valueOf(selectedFood.getSgstTax()));
        viewHolder.item_cgst_tax.setText(String.valueOf(selectedFood.getCgstTax()));
        viewHolder.item_dummy_vat_tax.setText(String.valueOf(selectedFood.getTotalVatTax()));
        viewHolder.item_vat_tax.setText(String.valueOf(selectedFood.getVattax()));

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                allSelectedFoodList.remove(position);
//                notifyDataSetChanged();

                Log.d(TAG, "onClick: onDeletingItem " + allSelectedFoodList.get(position).getMid() + " " + allSelectedFoodList.get(position).getMname());
                int localRate = Integer.parseInt(viewHolder.rate.getText().toString());
                int localQuantity = Integer.parseInt(viewHolder.quantity.getText().toString());
                variables.total_price -= localQuantity * localRate;
                NewActivity.txtTotal.setText(String.valueOf(variables.total_price));
                orderDatabseHelper.deleteCardOrderItem(viewHolder.item_id.getText().toString());
                List<cardOrderItem> tempList = new ArrayList<>();
                Cursor localCursor = orderDatabseHelper.getAllCardOrderData();
                while (localCursor.moveToNext()) {
                    tempList.add(new cardOrderItem(
                            localCursor.getString(0),
                            localCursor.getString(1),
                            localCursor.getInt(2),
                            localCursor.getInt(3),
                            localCursor.getInt(4),
                            localCursor.getInt(8),
                            localCursor.getInt(9),
                            localCursor.getInt(10),
                            localCursor.getInt(11),
                            localCursor.getInt(12),
                            localCursor.getInt(13),
                            localCursor.getInt(14),
                            localCursor.getInt(15)
                    ));


                }

                allSelectedFoodList = tempList;

                Log.d(TAG, "onClick: onDeleting item list size = " + allSelectedFoodList.size());

                notifyDataSetChanged();
                notifyItemRemoved(position);

            }
        });

        viewHolder.plus.setTag(position);
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Integer.parseInt(viewHolder.quantity.getText().toString().trim());
                Log.e("counter", String.valueOf(counter));
                counter = counter + 1;
                float igstTax,sgstTax ,cgstTax,totalSgstTax,totalCgstTax,totalIgstTax,vattax,totalVatTax;
                viewHolder.quantity.setText(" " + counter);
                String localId = viewHolder.item_id.getText().toString();
                String localName = viewHolder.item_name.getText().toString();
                String localRate = viewHolder.item_dummy_rate.getText().toString();
                try {
                    sgstTax = Float.parseFloat(viewHolder.item_sgst_tax.getText().toString());
                }catch (NumberFormatException e){
                    sgstTax =  0;
                }
                try {
                    igstTax = Float.parseFloat(viewHolder.item_igst_tax.getText().toString());
                }catch (NumberFormatException e){
                    igstTax =  0;
                }
                try {
                    cgstTax = Float.parseFloat(viewHolder.item_cgst_tax.getText().toString());
                }catch (NumberFormatException e){
                    cgstTax =  0;
                }
                try {
                    totalSgstTax = Float.parseFloat(viewHolder.item_dummy_sgst_tax.getText().toString());
                }catch (NumberFormatException e){
                    totalSgstTax =  0;
                }
                try {
                    totalCgstTax = Float.parseFloat(viewHolder.item_dummy_cgst_tax.getText().toString());
                }catch (NumberFormatException e){
                    totalCgstTax =  0;
                }
                try {
                    totalIgstTax = Float.parseFloat(viewHolder.item_dummy_igst_tax.getText().toString());
                }catch (NumberFormatException e){
                    totalIgstTax =  0;
                }
                try {
                    vattax = Float.parseFloat(viewHolder.item_vat_tax.getText().toString());
                }catch (NumberFormatException e){
                    vattax =  0;
                }
                try {
                    totalVatTax = Float.parseFloat(viewHolder.item_dummy_vat_tax.getText().toString());
                }catch (NumberFormatException e){
                    totalVatTax =  0;
                }

                Quantity = viewHolder.quantity.getText().toString().trim();

                value = counter * Integer.parseInt(localRate);
                viewHolder.rate.setText(String.valueOf(localRate));
//                for(int p=0 ;p<kot_items_bill_nodes.size();p++)
//                {
//                  if(kot_items_bill_nodes.get(p).getItem_node().equals(localName))
//                  {
//                      kot_items_bill_nodes.get(p).setitem_qtynode(Integer.parseInt(Quantity));
//                  }
//                }
                if (vattax == 0) {
                    orderDatabseHelper.updateQuantityAndTotalPrice(
                            localId,
                            localName,
                            Integer.parseInt(localRate),
                            counter,
                            value,
                            variables.selected_floor,
                            variables.table_no,
                            variables.table_name,
                            totalSgstTax + (Integer.parseInt(localRate) * sgstTax / 100),
                            totalCgstTax + (Integer.parseInt(localRate) * cgstTax / 100),
                            totalIgstTax + (Integer.parseInt(localRate) * igstTax / 100),
                            0
                    );
                } else {
                    orderDatabseHelper.updateQuantityAndTotalPrice(
                            localId,
                            localName,
                            Integer.parseInt(localRate),
                            counter, value,
                            variables.selected_floor,
                            variables.table_no,
                            variables.table_name,
                            0,
                            0,
                            0,
                            totalVatTax + (Integer.parseInt(localRate) * vattax / 100)
                    );
                }


                variables.total_price = variables.total_price + Integer.parseInt(localRate);

                NewActivity.txtTotal.setText(String.valueOf(variables.total_price));
            }
        });

        viewHolder.minus.setTag(position);
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float igstTax,sgstTax ,cgstTax,totalSgstTax,totalCgstTax,totalIgstTax,vattax,totalVatTax;
                counter = Integer.parseInt(viewHolder.quantity.getText().toString().trim());
                Log.e("counter", String.valueOf(counter));
                counter = counter - 1;
                if (counter>=0) {
                    viewHolder.quantity.setText(" " + counter);
                    String localId = viewHolder.item_id.getText().toString();
                    String localName = viewHolder.item_name.getText().toString();
                    String localRate = viewHolder.item_dummy_rate.getText().toString();
                    try {
                        sgstTax = Float.parseFloat(viewHolder.item_sgst_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        sgstTax = 0;
                    }
                    try {
                        igstTax = Float.parseFloat(viewHolder.item_igst_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        igstTax = 0;
                    }
                    try {
                        cgstTax = Float.parseFloat(viewHolder.item_cgst_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        cgstTax = 0;
                    }
                    try {
                        totalSgstTax = Float.parseFloat(viewHolder.item_dummy_sgst_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        totalSgstTax = 0;
                    }
                    try {
                        totalCgstTax = Float.parseFloat(viewHolder.item_dummy_cgst_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        totalCgstTax = 0;
                    }
                    try {
                        totalIgstTax = Float.parseFloat(viewHolder.item_dummy_igst_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        totalIgstTax = 0;
                    }
                    try {
                        vattax = Float.parseFloat(viewHolder.item_vat_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        vattax = 0;
                    }
                    try {
                        totalVatTax = Float.parseFloat(viewHolder.item_dummy_vat_tax.getText().toString());
                    } catch (NumberFormatException e) {
                        totalVatTax = 0;
                    }


                    Quantity = viewHolder.quantity.getText().toString().trim();
//                Rate = mMap.get("item_rate1");

//                value = Integer.parseInt(Quantity) * Integer.parseInt(selectedFood.getItem_rate1());
                    value = counter * (selectedFood.getRate());
                    viewHolder.rate.setText(String.valueOf(localRate));
                    if (vattax == 0) {
                        orderDatabseHelper.updateQuantityAndTotalPrice(
                                localId,
                                localName,
                                Integer.parseInt(localRate),
                                counter, value,
                                variables.selected_floor,
                                variables.table_no,
                                variables.table_name,
                                totalSgstTax - (Integer.parseInt(localRate) * sgstTax / 100),
                                totalCgstTax - (Integer.parseInt(localRate) * cgstTax / 100),
                                totalIgstTax - (Integer.parseInt(localRate) * igstTax / 100),
                                0
                        );
                    } else {
                        orderDatabseHelper.updateQuantityAndTotalPrice(
                                localId,
                                localName,
                                Integer.parseInt(localRate),
                                counter, value,
                                variables.selected_floor,
                                variables.table_no,
                                variables.table_name,
                                0,
                                0,
                                0,
                                totalVatTax - (Integer.parseInt(localRate) * vattax / 100)
                        );
                    }

                    variables.total_price = variables.total_price - Integer.parseInt(localRate);

                    if(counter == 0){
                        orderDatabseHelper.deleteCardOrderItem(viewHolder.item_id.getText().toString());
                        List<cardOrderItem> tempList = new ArrayList<>();
                        Cursor localCursor = orderDatabseHelper.getAllCardOrderData();
                        while (localCursor.moveToNext()) {
                            tempList.add(new cardOrderItem(localCursor.getString(0),
                                    localCursor.getString(1),
                                    localCursor.getInt(2),
                                    localCursor.getInt(3),
                                    localCursor.getInt(4),
                                    localCursor.getInt(8),
                                    localCursor.getInt(9),
                                    localCursor.getInt(10),
                                    localCursor.getInt(11),
                                    localCursor.getInt(12),
                                    localCursor.getInt(13),
                                    localCursor.getInt(14),
                                    localCursor.getInt(15)
                            ));


                        }

                        allSelectedFoodList = tempList;

                        Log.d(TAG, "onClick: onDeleting item list size = " + allSelectedFoodList.size());

                        notifyDataSetChanged();
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Item removed from Cart", Toast.LENGTH_SHORT).show();
                    }

                    // v4=v4+Integer.parseInt(Rate);
                    // v4=v4-(selectedFood.getRate());
//                    totalPrice = String.valueOf((v4));
                    NewActivity.txtTotal.setText(String.valueOf(variables.total_price));
                }else {
                }
            }
        });

//        Quantity = viewHolder.quantity.getText().toString();
//        Rate = viewHolder.item_dummy_rate.getText().toString();
//
//
//        Log.e("Quantity", Quantity);
//        Log.e("Rate", Rate);
//
//        value = Integer.parseInt(Quantity) * Integer.parseInt(Rate);
//
//        v4 = v4 + Integer.parseInt(String.valueOf(value));
//        totalPrice = String.valueOf((v4));
//        //NewActivity.txtTotal.setText(totalPrice);
//
//        Log.e("totalPrice", totalPrice);
//
//        S = String.valueOf(Integer.parseInt(Quantity));
//
//        AppPrefences.setQuantity(context, S);
//        Log.e("S>>", Quantity);


    }

    @Override
    public int getItemCount() {
        return allSelectedFoodList.size();
    }

    public void removeAt(int position) {
        cardItemlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cardItemlist.size());
    }

}