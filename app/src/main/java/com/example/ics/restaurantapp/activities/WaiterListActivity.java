package com.example.ics.restaurantapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.waiterItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.adapter.WaiterAdapter;
import com.example.ics.restaurantapp.handler.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WaiterListActivity extends AppCompatActivity {
    RecyclerView recycler_waiter;
    Button btn_skip;
    String server_url;
    private WaiterAdapter waiterAdapter;
    //ArrayList<HashMap<String, String>> w_list;
    ArrayList<waiterItem> waiter_list;
    android.support.v7.widget.Toolbar toolbar_waiter;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String Outlet;
    DatabaseHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_list);

        btn_skip = (Button)findViewById(R.id.btn_skip);

        Outlet= AppPrefences.getOutlet(WaiterListActivity.this);
        String floor = AppPrefences.getFloor(WaiterListActivity.this);

       String name = AppPrefences.getGuest(WaiterListActivity.this);
       helper = new DatabaseHelper(this);

        //new GetWaiterAsynctask().execute();
        waiter_list = new ArrayList<>();

        Cursor local =  helper.getAllDataTable3();
        while(local.moveToNext()){
            waiter_list.add(new waiterItem(local.getString(0).toString(),local.getString(1).toString(),local.getString(2).toString(),local.getString(3).toString(),local.getString(4).toString(),local.getString(5).toString()));
        }

        waiterAdapter = new WaiterAdapter(WaiterListActivity.this, waiter_list);
        recycler_waiter = (RecyclerView) findViewById(R.id.recycler_waiter);
        recycler_waiter.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WaiterListActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_waiter.setLayoutManager(linearLayoutManager);
        recycler_waiter.setAdapter(waiterAdapter);

        //w_list = new ArrayList<>();

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variables.selected_waiter_data = new waiterItem(
                        "N/A",
                        "N/A",
                        "N/A",
                        "N/A",
                        "N/A",
                        "N/A"
                );
                helper.updateTableWaiterID(variables.selecetd_table_data.getT_id(),"N/A");
                Intent intent = new Intent(WaiterListActivity.this,NewActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

   //------------------------------------------------------------------

    class GetWaiterAsynctask extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(WaiterListActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
           /* arComentChat = new ArrayList<>();*/
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://twors.in/POS/Webservices/get_waiter_list?outlet_id="+Outlet;
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.e("sever_url>>>>>>>>>", server_url);
            output = HttpHandler.makeServiceCall(server_url);
            Log.e("getcomment_url", output);
            System.out.println("getcomment_url" + output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output == null) {
                dialog.dismiss();
            } else {
                try {
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

                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("w_id", w_id);
                        contact.put("w_name", w_name);
                        contact.put("alias", alias);
                        contact.put("waiter_id", waiter_id);
                        contact.put("outlet_id", outlet_id);
                        //w_list.add(contact);
                    }

                    //waiterAdapter = new WaiterAdapter(WaiterListActivity.this, w_list);
                    recycler_waiter = (RecyclerView) findViewById(R.id.recycler_waiter);
                    recycler_waiter.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WaiterListActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recycler_waiter.setLayoutManager(linearLayoutManager);
                    recycler_waiter.setAdapter(waiterAdapter);

                    if (output.equalsIgnoreCase("1")) {
                        dialog.dismiss();

                   Toast.makeText(WaiterListActivity.this, "response", Toast.LENGTH_LONG).show();
                     /* Intent intent = new Intent(WaiterListActivity.this,NewActivity.class);
                       intent.putExtra("wname","w_name");
                       startActivity(intent);*/

                    } else {
                        //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }
}
