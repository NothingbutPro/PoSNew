package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.kitchenOrderItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.activities.NewActivity;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ICS on 04/04/2018.
 */

public class KotAdapter extends RecyclerView.Adapter<KotAdapter.ViewHolder>  {

    private static final String TAG = "KotAdapter";
    private ArrayList<HashMap<String,String>> kotItemlist;
    private List<kitchenOrderItem> kitchenOrderItemList;
    public Context context;
    int counter;
    int updateFlag =0,updateModifiedFlag = 0;
    String S;
    OrderDatabseHelper orderDatabseHelper;
    DatabaseHelper helper;


    public class ViewHolder extends RecyclerView.ViewHolder {
        Button save_and_send;
        public TextView serial_number,item_name1,rate1,item_id,item_dummy_rate,item_dummy_sgst_tax,item_dummy_cgst_tax,item_dummy_igst_tax,item_sgst_tax,item_cgst_tax,item_igst_tax,item_vat_tax,item_dummy_vat_tax;
        public ImageView plus,minus;
        TextView quantity1;
        //   CardView card_view;
        int pos;

        public ViewHolder(View view) {
            super(view);
            counter = +1;
            serial_number = (TextView) view.findViewById(R.id.serial_number);
            item_name1 = (TextView) view.findViewById(R.id.item_name1);
            quantity1 = (TextView) view.findViewById(R.id.quantity1);
            rate1 = (TextView) view.findViewById(R.id.rate1);
//            plus = (ImageView) view.findViewById(R.id.plus);
            minus = (ImageView) view.findViewById(R.id.minus);
            item_id = (TextView) view.findViewById(R.id.item_id);
            item_dummy_rate = (TextView) view.findViewById(R.id.item_dummy_rate);
            item_dummy_sgst_tax = (TextView) view.findViewById(R.id.item_dummy_sgst_tax);
            item_dummy_cgst_tax = (TextView) view.findViewById(R.id.item_dummy_cgst_tax);
            item_dummy_igst_tax = (TextView) view.findViewById(R.id.item_dummy_igst_tax);
            item_sgst_tax = (TextView) view.findViewById(R.id.item_sgst_tax);
            item_cgst_tax = (TextView) view.findViewById(R.id.item_cgst_tax);
            item_igst_tax = (TextView) view.findViewById(R.id.item_igst_tax);
            item_dummy_vat_tax = (TextView)view.findViewById(R.id.item_dummy_vat_tax);
            item_vat_tax = (TextView)view.findViewById(R.id.item_vat_tax);
            //  card_view = (CardView) view.findViewById(R.id.card_view);

            S = AppPrefences.getQuantity(context);
        }
    }

    public static Context mContext;
    public KotAdapter(Context mContext, List<kitchenOrderItem> kitchenOrderItemList) {
        context = mContext;
        this.kitchenOrderItemList = kitchenOrderItemList;
        orderDatabseHelper = new OrderDatabseHelper(context);
        helper = new DatabaseHelper(context);
    }

    @Override
    public KotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kot_item, parent, false);

        return new KotAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final KotAdapter.ViewHolder viewHolder, final int position) {
        int no = position;
        int no2= ++no;
        //final Map<String, String> mMap = kotItemlist.get(position);
        final kitchenOrderItem localKitchenOrderItem = kitchenOrderItemList.get(position);
        viewHolder.serial_number.setText(String.valueOf(localKitchenOrderItem.getSerial()));
        viewHolder.item_id.setText(localKitchenOrderItem.getMid());
        viewHolder.item_name1.setText(localKitchenOrderItem.getMname());
        viewHolder.quantity1.setText(String.valueOf(localKitchenOrderItem.getQuantity()));
        viewHolder.rate1.setText(String.valueOf(localKitchenOrderItem.getRate()));
//        viewHolder.plus.setTag(viewHolder);
        viewHolder.minus.setTag(viewHolder);
        viewHolder.pos = position;
        viewHolder.item_dummy_rate.setText(String.valueOf(localKitchenOrderItem.getRate()));
        viewHolder.item_dummy_sgst_tax.setText(String.valueOf(localKitchenOrderItem.getTotalSgstTax()));
        viewHolder.item_dummy_cgst_tax.setText(String.valueOf(localKitchenOrderItem.getTotalCgstTax()));
        viewHolder.item_dummy_igst_tax.setText(String.valueOf(localKitchenOrderItem.getTotalIgstTax()));
        viewHolder.item_sgst_tax.setText(String.valueOf(localKitchenOrderItem.getSgstTax()));
        viewHolder.item_cgst_tax.setText(String.valueOf(localKitchenOrderItem.getCgstTax()));
        viewHolder.item_dummy_vat_tax.setText(String.valueOf(localKitchenOrderItem.getTotalVatTax()));
        viewHolder.item_vat_tax.setText(String.valueOf(localKitchenOrderItem.getVattax()));



      /*  viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
                *//*cardItemlist.remove(position);
                arrayAdapter.notifyDataSetChanged();*//*

            }
        });*/

//        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                counter = Integer.parseInt(viewHolder.quantity1.getText().toString().trim());
//                Log.e("counter", String.valueOf(counter));
//                counter = counter + 1;
//                if (counter > 0) {
//                    viewHolder.quantity1.setText(" " + counter);
//                    String localId = viewHolder.item_id.getText().toString();
//                    String localName = viewHolder.item_name1.getText().toString();
//                    String localRate = viewHolder.item_dummy_rate.getText().toString();
//                    float sgstTax = Float.parseFloat(viewHolder.item_sgst_tax.getText().toString());
//                    float cgstTax = Float.parseFloat(viewHolder.item_cgst_tax.getText().toString());
//                    float igstTax;
//                    try {
//                        igstTax = Float.parseFloat(viewHolder.item_igst_tax.getText().toString());
//                    }catch (NumberFormatException e){
//                        igstTax =  0;
//                    }
//
//                    float totalSgstTax = Float.parseFloat(viewHolder.item_dummy_sgst_tax.getText().toString());
//                    float totalCgstTax = Float.parseFloat(viewHolder.item_dummy_cgst_tax.getText().toString());
//                    float totalIgstTax = Float.parseFloat(viewHolder.item_dummy_igst_tax.getText().toString());
//                    float vattax = Float.parseFloat(viewHolder.item_vat_tax.getText().toString());
//                    float totalVatTax = Float.parseFloat(viewHolder.item_dummy_vat_tax.getText().toString());
//
//                    int value = counter * Integer.parseInt(localRate);
//                    viewHolder.rate1.setText(String.valueOf(localRate));
//
//                    if (vattax == 0) {
//                        orderDatabseHelper.updatTableItemOrderInformation(
//                                variables.selecetd_table_data.getT_id(),
//                                localId,
//                                localName,
//                                Integer.parseInt(localRate),
//                                counter,
//                                value,
//                                totalSgstTax + (Integer.parseInt(localRate) * sgstTax / 100),
//                                totalCgstTax + (Integer.parseInt(localRate) * cgstTax / 100),
//                                totalIgstTax + (Integer.parseInt(localRate) * igstTax / 100),
//                                0,
//                                variables.tableNumber
//                        );
//                    } else {
//                        orderDatabseHelper.updatTableItemOrderInformation(
//                                variables.selecetd_table_data.getT_id(),
//                                localId,
//                                localName,
//                                Integer.parseInt(localRate),
//                                counter,
//                                value,
//                                0,
//                                0,
//                                0,
//                                totalVatTax + (Integer.parseInt(localRate) * vattax / 100),
//                                variables.tableNumber
//                        );
//                    }
//
//                }
//            }
//        });



        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFlag = 0;
                updateModifiedFlag = 0;
                counter = Integer.parseInt(viewHolder.quantity1.getText().toString().trim());
                Log.e("counter", String.valueOf(counter));
                counter = counter - 1;
                if (counter >= 0) {
                    variables.modifyBtn = 1;
                    NewActivity.btn_modify.setVisibility(View.VISIBLE);
                    float igstTax,sgstTax ,cgstTax,totalSgstTax,totalCgstTax,totalIgstTax,vattax,totalVatTax;
                    viewHolder.quantity1.setText(" " + counter);
                    String localId = viewHolder.item_id.getText().toString();
                    String localName = viewHolder.item_name1.getText().toString();
                    String localRate = viewHolder.item_dummy_rate.getText().toString();
                    int localTotalprice =variables.total_price;
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

                    String localTime = "";
                    Cursor localCursor = orderDatabseHelper.getDataOfSelectedTable(
                            variables.selecetd_table_data.getT_id(),
                            variables.tableNumber
                    );
                    if(localCursor.getCount()!=0){
                        localCursor.moveToNext();
                        localTime = localCursor.getString(4);
                    }

                    viewHolder.rate1.setText(String.valueOf(localRate));

                    Cursor localCancelledDatabase = orderDatabseHelper.getCancelledOrderInfo(
                            variables.selecetd_table_data.getT_id(),
                            variables.tableNumber
                    );


                    Cursor itemInfoCursor = helper.getFoodInformation(localId);
                    itemInfoCursor.moveToNext();
                    Log.d(TAG, "onClick: itemCursorCount "+itemInfoCursor.getCount());

                    if (vattax == 0) {
                        if(localCancelledDatabase.getCount()==0){
                            orderDatabseHelper.insertCancelledOrderInfo(
                                    variables.selecetd_table_data.getT_id(),
                                    localId,
                                    localName,
                                    Integer.parseInt(localRate),
                                    1,
                                    Integer.parseInt(localRate),
                                    sgstTax,
                                    cgstTax,
                                    igstTax,
                                    Integer.parseInt(localRate)*sgstTax/100,
                                    Integer.parseInt(localRate) * cgstTax / 100,
                                    Integer.parseInt(localRate) * igstTax / 100,
                                    variables.tableNumber,
                                    vattax,
                                    0,
                                    itemInfoCursor.getString(2),
                                    itemInfoCursor.getString(3),
                                    itemInfoCursor.getString(11),
                                    itemInfoCursor.getString(12),
                                    itemInfoCursor.getString(13),
                                    itemInfoCursor.getString(14)
                            );
                        }else {
                            while(localCancelledDatabase.moveToNext()){
                                if(localCancelledDatabase.getString(1).equals(localId)){
                                    orderDatabseHelper.updateCancelledOrderInfo(
                                            variables.selecetd_table_data.getT_id(),
                                            localId,
                                            localName,
                                            Integer.parseInt(localRate),
                                            localCancelledDatabase.getInt(4)+1,
                                            localTotalprice - Integer.parseInt(localRate),
                                            totalSgstTax + (Integer.parseInt(localRate) * sgstTax / 100),
                                            totalCgstTax + (Integer.parseInt(localRate) * cgstTax / 100),
                                            totalIgstTax + (Integer.parseInt(localRate) * igstTax / 100),
                                            0,
                                            variables.tableNumber
                                    );
                                    updateFlag = 1;
                                    break;
                                }
                            }
                            if(updateFlag == 0){
                                orderDatabseHelper.insertCancelledOrderInfo(
                                        variables.selecetd_table_data.getT_id(),
                                        localId,
                                        localName,
                                        Integer.parseInt(localRate),
                                        1,
                                        Integer.parseInt(localRate),
                                        sgstTax,
                                        cgstTax,
                                        igstTax,
                                        Integer.parseInt(localRate)*sgstTax/100,
                                        Integer.parseInt(localRate) * cgstTax / 100,
                                        Integer.parseInt(localRate) * igstTax / 100,
                                        variables.tableNumber,
                                        vattax,
                                        0,
                                        itemInfoCursor.getString(2),
                                        itemInfoCursor.getString(3),
                                        itemInfoCursor.getString(11),
                                        itemInfoCursor.getString(12),
                                        itemInfoCursor.getString(13),
                                        itemInfoCursor.getString(14)
                                );
                            }

                        }

                        variables.cancelledTotalTax = variables.cancelledTotalTax + (Float.parseFloat(localRate) * sgstTax / 100)+ (Float.parseFloat(localRate) * cgstTax / 100)+(Float.parseFloat(localRate) * igstTax / 100);
                    } else {
                        if(localCancelledDatabase.getCount()==0){
                            orderDatabseHelper.insertCancelledOrderInfo(
                                    variables.selecetd_table_data.getT_id(),
                                    localId,
                                    localName,
                                    Integer.parseInt(localRate),
                                    1,
                                    Integer.parseInt(localRate),
                                    sgstTax,
                                    cgstTax,
                                    igstTax,
                                    0,
                                    0,
                                    0,
                                    variables.tableNumber,
                                    vattax,
                                    Integer.parseInt(localRate) * vattax / 100,
                                    itemInfoCursor.getString(2),
                                    itemInfoCursor.getString(3),
                                    itemInfoCursor.getString(11),
                                    itemInfoCursor.getString(12),
                                    itemInfoCursor.getString(13),
                                    itemInfoCursor.getString(14)
                            );
                        }else {
                            while (localCancelledDatabase.moveToNext()){
                                if(localCancelledDatabase.getString(1).equals(localId)){
                                    orderDatabseHelper.updateCancelledOrderInfo(
                                            variables.selecetd_table_data.getT_id(),
                                            localId,
                                            localName,
                                            Integer.parseInt(localRate),
                                            localCancelledDatabase.getInt(4)+1,
                                            localTotalprice + Integer.parseInt(localRate),
                                            0,
                                            0,
                                            0,
                                            totalVatTax + (vattax*Integer.parseInt(localRate)/100),
                                            variables.tableNumber
                                    );

                                    updateFlag =1;
                                    break;
                                }
                            }

                            if(updateFlag == 0){
                                orderDatabseHelper.insertCancelledOrderInfo(
                                        variables.selecetd_table_data.getT_id(),
                                        localId,
                                        localName,
                                        Integer.parseInt(localRate),
                                        1,
                                        Integer.parseInt(localRate),
                                        sgstTax,
                                        cgstTax,
                                        igstTax,
                                        0,
                                        0,
                                        0,
                                        variables.tableNumber,
                                        vattax,
                                        Integer.parseInt(localRate) * vattax / 100,
                                        itemInfoCursor.getString(2),
                                        itemInfoCursor.getString(3),
                                        itemInfoCursor.getString(11),
                                        itemInfoCursor.getString(12),
                                        itemInfoCursor.getString(13),
                                        itemInfoCursor.getString(14)
                                );
                            }

                        }
                        variables.cancelledTotalTax = variables.cancelledTotalTax + Float.parseFloat(localRate)*vattax/100;
                    }

                    variables.cancelledTotalAmount = variables.cancelledTotalAmount + Integer.parseInt(localRate);
                }else {
                    Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return kitchenOrderItemList.size();
    }

}
