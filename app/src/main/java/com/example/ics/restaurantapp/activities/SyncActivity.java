package com.example.ics.restaurantapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ics.restaurantapp.Data.AllData;
import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.DineAreaOutput;
import com.example.ics.restaurantapp.ModelDB.FloorTableOutput;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.adapter.CustomAdapter;
import com.example.ics.restaurantapp.handler.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

public class SyncActivity extends AppCompatActivity {
    LinearLayout sync_btn;
    String server_url;
    String Outlet;
    String discount_amo="0.0";
    String Group_item ;
    GridView grid_tables;
    private static ProgressBar progress1;
    private String categoryItem;
    String  Mob, Adress, Name="";
    //--------------------------
    ArrayList<String> floor_list;
    ArrayList<String> table_list;
    ArrayList<String> category_list;
    ArrayList<HashMap<String, String>> w_list;
    ArrayList<HashMap<String, String>> menu_list;
    ArrayList<HashMap<String, String>> d_list;
    ArrayList<HashMap<String, String>> k_list;
    ArrayList<HashMap<String,String>> arl;
    ArrayList<String> submenu_list;
    //--------------------------
    String Selected_floor;
    ArrayAdapter ad_floor;
    int flag = 0;
    private ProgressDialog dialog;
    private String din_area;
    public static String sFloorName;

    //-----------------------
    List<DineAreaOutput> dineAreaOutputList;
    List<FloorTableOutput> floorTableOutput;
    ArrayList<String> localCategoryList;
    ArrayList<String> localGroupList;
    //-----------------------
    public DatabaseHelper helper;
    int order_no =0;
    int receipt_no =0;


    private static final String TAG = "SyncActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        Outlet = AppPrefences.getOutlet(SyncActivity.this);
        Log.e("Outlet>>>>>",Outlet);

       //----------------------------------

        floor_list = new ArrayList<>();
        table_list = new ArrayList<>();
        w_list = new ArrayList<>();
        menu_list = new ArrayList<>();
        category_list = new ArrayList<>();
        d_list = new ArrayList<>();
        k_list = new ArrayList<>();
        arl = new ArrayList<>();
        submenu_list = new ArrayList<>();

        //--------------------------------

        AllData.dineAreaOutput = new ArrayList<>();
        AllData.floorTableOutput = new ArrayList<>();

        //----------------------------------------------

        Paper.init(this);


        //-----------------------------------

        helper = new DatabaseHelper(this);
        localCategoryList = new ArrayList<>();
        localGroupList = new ArrayList<>();
        //-------------------------------

        sync_btn = (LinearLayout) findViewById(R.id.sync_btn);

        sync_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetSyncAllData().execute();

            }
        });

        progress1 = (ProgressBar) findViewById(R.id.progress1);
        progress1.setProgress(0);
        progress1.setMax(100);

    }

    //------------------------------------------------------------------------------------

    class GetSyncAllData extends AsyncTask<String, String, String> {
        String output = "";


        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SyncActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //-------------------------------------------

                if (flag == 0) {
                    server_url = "http://twors.in/POS/Webservices/get_dining_area?outlet_id=" + Outlet;
                }
                if (flag == 1) {
                    server_url = "http://twors.in/POS/Webservices/get_all_table_list?outlet_id="+Outlet;
                }
                if (flag == 2) {
                    server_url = "http://twors.in/POS/Webservices/get_waiter_list?outlet_id=" + Outlet;
                }
                if (flag == 3) {
                    server_url = "http://twors.in/POS/Webservices/get_category_list?outlet_id=" + Outlet;
                }
                if (flag == 4) {
                    server_url = "http://twors.in/POS/Webservices/get_all_category_list?outlet_id="+Outlet;
                }
                if (flag == 5) {
                    server_url = "http://twors.in/POS/Webservices/get_all_menu_list?outlet_id="+Outlet;
                }
                if (flag == 6) {
                    server_url = "http://twors.in/POS/Webservices/search_menu?outlet_id="+Outlet;
//                    server_url = "http://twors.in/POS/Webservices/search_menu?outlet_id="+Outlet+"&value="+"";
                }
                if (flag == 7) {
                    server_url = "http://twors.in/POS/Webservices/takeaway?outlet_id="+Outlet+"&guest_name="+Name+"&address="+Adress+"&mobile="+Mob;
                }


                //-----------------------------------------------

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("sever_url>>>>>>>>>", server_url);
            output = HttpHandler.makeServiceCall(server_url);
            Log.e("getcomment_url", output);
            Log.d(TAG, "doInBackground: "+flag+server_url);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            Log.d(TAG, "onPostExecute: started");
            if (output == null) {
                dialog.dismiss();
            } else {
                //----------------------------------------------------

                Log.e("Flag", String.valueOf(flag));

                if (flag == 0) {
                    dinAreaOutput(output);
                } else if (flag == 1) {
                    floorTableOutput(output);
                } else if (flag == 2) {
                    waiterListOutput(output);
                } else if (flag == 3) {
                    categoryListOutput(output);
                } else if (flag == 4) {
                     itemListOutput(output);
                } else if (flag == 5) {
                      gridMenuOutput(output);
                } else if (flag == 6) {
                     searchMenuOutput(output);
                } else if (flag == 7) {
                    takeawayOutput(output);
                } else if (flag == 8) {

                } else if (flag == 9) {

                } else if (flag == 10) {


                    //-------------------------------------------------------------
                    super.onPostExecute(output);
                }
            }
        }

        //------------------------------------------------------------------

        private void dinAreaOutput(String output) {
            try {
                helper.trancateDineArea();
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONObject floor_obj = json.getJSONObject("floor");
                JSONArray floor_array = floor_obj.getJSONArray("floor");
                for (int i = 0; i < floor_array.length(); i++) {
                    JSONObject c = floor_array.getJSONObject(i);
                    String da_id = c.getString("da_id");
                    String da_name = c.getString("da_name");
                    String outlet_id = c.getString("outlet_id");
                    Log.d(TAG, "dinAreaOutput: "+i+" "+da_id+" "+da_name+" "+outlet_id);
                    floor_list.add(da_name);
                    helper.insertDineArea(da_id,da_name,outlet_id);
                }

                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(12);
                    flag = flag + 1;
                    new GetSyncAllData().execute();
                    Toast.makeText(SyncActivity.this, "response", Toast.LENGTH_LONG).show();

                } else {
                    //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

        //------------------------------------------------------------------------------------

        private void floorTableOutput(String output) {
            try {
                helper.trancateFloorTable();
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONObject message_obj = json.getJSONObject("message");
                for (int i=0;i<floor_list.size();i++) {
                    JSONArray First_Floor_array = message_obj.getJSONArray(floor_list.get(i));
                    for (int j = 0; j < First_Floor_array.length(); j++) {
                        JSONObject c = First_Floor_array.getJSONObject(j);
                        String t_id = c.getString("t_id");
                        String t_name = c.getString("t_name");
                        String din_area = c.getString("din_area");
                        String alias = c.getString("alias");
                        String outlet_id = c.getString("outlet_id");
                        String d_id = c.getString("d_id");
                        helper.insertFloorTable(t_id, t_name, din_area, alias, outlet_id, d_id,"free","","no");
                    }

                }

                Log.e("test_floor", table_list.toString());
                //CustomAdapter customAdapter1 = new CustomAdapter(getApplicationContext(), table_list);


                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(24);
                    flag = flag + 1;
                    new GetSyncAllData().execute();
                    AppPrefences.setFloor(SyncActivity.this, din_area);

                    Toast.makeText(SyncActivity.this, "response", Toast.LENGTH_LONG).show();


                } else {
                    //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error", e.toString());
                dialog.dismiss();
            }
        }

        //--------------------------------------------------------------

        private void waiterListOutput(String output) {

            try {
                helper.trancateWaiterTable();
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONObject waiter_obj = json.getJSONObject("waiter");
                JSONArray waiter_array = waiter_obj.getJSONArray("waiter");
                for (int i=0; i<waiter_array.length(); i++) {
                    JSONObject c = waiter_array.getJSONObject(i);
                    String w_id = c.getString("w_id");
                    String w_name = c.getString("w_name");
                    String alias = c.getString("alias");
                    String waiter_id = c.getString("waiter_id");
                    String outlet_id = c.getString("outlet_id");
                    helper.insertWaiterTable(w_id,w_name,alias,waiter_id,outlet_id,discount_amo);
//                    HashMap<String, String> contact = new HashMap<>();
//                    contact.put("w_id", w_id);
//                    contact.put("w_name", w_name);
//                    contact.put("alias", alias);
//                    contact.put("waiter_id", waiter_id);
//                    contact.put("outlet_id", outlet_id);
//                    w_list.add(contact);
                }


                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(36);
                    flag = flag + 1;
                    new GetSyncAllData().execute();

                    Toast.makeText(SyncActivity.this, "response", Toast.LENGTH_LONG).show();

                } else {
                    //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

        //--------------------------------------------------------------

        private void categoryListOutput(String output) {

            try {
                helper.trancateMenuTable();
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONObject category_obj = json.getJSONObject("category");
                JSONArray category_array = category_obj.getJSONArray("category");
                for (int i = 0; i < category_array.length(); i++) {
                    JSONObject c = category_array.getJSONObject(i);
                    String cat_id = c.getString("cat_id");
                    String cat_name = c.getString("cat_name");
                    String outlet_id = c.getString("outlet_id");

                        localCategoryList.add(cat_name);
                        helper.insertMenuTable(cat_id, cat_name, outlet_id);

//                    HashMap<String, String> contact = new HashMap<>();
//                    contact.put("cat_id", cat_id);
//                    contact.put("cat_name", cat_name);
//                    contact.put("outlet_id", outlet_id);
//                    menu_list.add(contact);
                }

                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(48);
                    flag = flag + 1;
                    new GetSyncAllData().execute();

                    Toast.makeText(SyncActivity.this, "response", Toast.LENGTH_LONG).show();


                } else {
                    //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

        //---------------------------------------------------------------

        private void itemListOutput(String output) {


            for(int i=0;i<localCategoryList.size();i++){
                Log.d(TAG, "itemListOutput: "+i+localCategoryList.get(i));
            }
            try {
                helper.trancateCategoryTable();
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONObject message_obj = json.getJSONObject("message");
                for(int j=0;j<localCategoryList.size();j++) {
                    JSONArray FOOD_array = message_obj.getJSONArray(localCategoryList.get(j));
                    Log.d(TAG, "itemListOutput: "+localCategoryList.get(j));
                    for (int i = 0; i < FOOD_array.length(); i++) {
                        JSONObject c = FOOD_array.getJSONObject(i);
                        String g_id = c.getString("g_id");
                        String g_name = c.getString("g_name");
                        String category = c.getString("category");
                        String outlet_id = c.getString("outlet_id");

                            helper.insertCategoryTable(g_id, g_name, category, outlet_id);
                            localGroupList.add(g_name);


                    }
                }

                Log.e("category_list?",category_list.toString());

                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(60);
                    flag = flag + 1;
                    new GetSyncAllData().execute();


                } else {
                   // ((NewActivity)context).getValues(category_list);
                    //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

       //----------------------------------------------------------------

        private void gridMenuOutput(String output) {

            try {
                helper.trancateGridMenuTable();
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONObject message_obj = json.getJSONObject("message");
                for(int j=0;j<localGroupList.size();j++) {
                    JSONArray MOCKTAILS_array = message_obj.getJSONArray(localGroupList.get(j));
                    for (int i = 0; i < MOCKTAILS_array.length(); i++) {
                        JSONObject c = MOCKTAILS_array.getJSONObject(i);
                        String m_id = c.getString("m_id");
                        String m_name = c.getString("m_name");
                        String barcode = c.getString("barcode");
                        String alias = c.getString("alias");
                        String item_rate1 = c.getString("item_rate1");
                        String item_rate2 = c.getString("item_rate2");
                        String item_rate3 = c.getString("item_rate3");
                        String rate_Taxsgst = c.getString("rate_Taxsgst");
                        String rate_Taxcgst = c.getString("rate_Taxcgst");
                        String rate_Taxigst = c.getString("rate_Taxigst");
                        String vat = c.getString("vat");
                        String group = c.getString("group");
                        String category = c.getString("category");
                        String serve_unit = c.getString("serve_unit");
                        String department = c.getString("department");
                        String outlet_id = c.getString("outlet_id");
                        helper.insertGridMenuTable(m_id, m_name, barcode, alias, item_rate1, item_rate2, item_rate3, rate_Taxsgst, rate_Taxcgst, rate_Taxigst, vat,group, category, serve_unit, department, outlet_id);
//                    HashMap<String, String> data = new HashMap<String, String>();
//                    data.put("m_id",m_id);
//                    data.put("m_name", m_name);
//                    data.put("item_rate1", item_rate1);
//                    data.put("item_rate2", item_rate2);
//                    data.put("item_rate3", item_rate3);
//
//                    d_list.add(data);
//                    k_list.add(data);
//                    arl.add(data);
//
//                    submenu_list.add(m_name);

                    }
                }

                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(72);
                    flag = flag + 1;
                   // startActivity(new Intent(SyncActivity.this,DrawerActivity.class));
                    new GetSyncAllData().execute();


                    Toast.makeText(SyncActivity.this, "response", Toast.LENGTH_LONG).show();


                } else {
                    //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        }

        //----------------------------------------------------------------

        private void searchMenuOutput(String output) {

            try {
                dialog.dismiss();
                JSONObject json = new JSONObject(output);
                String response = json.getString("response");
                JSONArray message_array = json.getJSONArray("message");
                for (int i=0; i<message_array.length(); i++){
                    JSONObject m = message_array.getJSONObject(i);
                    String m_id = m.getString("m_id");
                    String m_name = m.getString("m_name");
                    String barcode = m.getString("barcode");
                    String alias = m.getString("alias");
                    String item_rate1 = m.getString("item_rate1");
                    String item_rate2 = m.getString("item_rate2");
                    String item_rate3 = m.getString("item_rate3");
                    String rate_Taxsgst = m.getString("rate_Taxsgst");
                    String rate_Taxcgst = m.getString("rate_Taxcgst");
                    String rate_Taxigst = m.getString("rate_Taxigst");
                    String vat = m.getString("vat");
                    String group = m.getString("group");
                    String category = m.getString("category");
                    String serve_unit = m.getString("serve_unit");
                    String department = m.getString("department");
                    String outlet_id = m.getString("outlet_id");

                   // searchList.add(m_name);
                }

                /*adapter = new ArrayAdapter<String>(NewActivity.this, android.R.layout.simple_list_item_1,searchList);
                listview_search.setAdapter(adapter);*/


                Log.e("json>>>>>>>>>",json.toString());


                if (response.equalsIgnoreCase("1")) {
                    dialog.dismiss();
                    progress1.setProgress(84);
                    flag = flag + 1;
                    Paper.book().write("ORDER_NO",order_no);
                    Paper.book().write("RECEIPT_NO",receipt_no);
                    startActivity(new Intent(SyncActivity.this,DrawerActivity.class));
                    finish();
                 //   new GetSyncAllData().execute();

                    Toast.makeText(SyncActivity.this,"Your Search Successfully", Toast.LENGTH_LONG).show();

                }

                else
                {
                    Toast.makeText(SyncActivity.this,response,Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
            super.onPostExecute(output);
        }
    }

     //---------------------------------------------------------------------

    private void takeawayOutput(String output) {


    }

    //------------------------------------------------------------------------
}

