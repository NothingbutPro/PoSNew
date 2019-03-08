package com.example.ics.restaurantapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.CustomerDatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.DBDiscountHelper;
import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.kitchenOrderItem;
import com.example.ics.restaurantapp.ModelDB.tableItem;
import com.example.ics.restaurantapp.ModelDB.waiterItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.SharedPreference.SessionManager;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.adapter.CustomAdapter;
import com.example.ics.restaurantapp.handler.HttpHandler;
import com.example.ics.restaurantapp.receiver.BlueServiceOnce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    Spinner Spinner_floor;
    GridView grid_tables, gridSplittables;
    ArrayAdapter ad_floor;
    String server_url;
    String Mob, Adress, Name = "";
    String Outlet;
    EditText m_number, name, address;
    Button t_submit;
    ArrayList<String> floor_list;
    String Selected_floor;
    ArrayList<String> table_list;
    private String Din_area = "";
    public static String t_name;
    public static String sFloorName;
    private String din_area;
    SessionManager sessionManager;
    LinearLayout grid, gridSplit;
    ArrayList<kitchenOrderItem> billSectionOrderList = new ArrayList<>();
    //-------------------------
    List<String> dinAreaListLocal;
    Cursor localCursor;
    DatabaseHelper helper;
    List<String> localList;

    OrderDatabseHelper orderDatabseHelper;
    CustomerDatabaseHelper customerHelper;

    private static final String TAG = "DrawerActivity";
    private DBDiscountHelper dbHelper;

    @Override
    protected void onResume() {
        super.onResume();

        localCursor = helper.getAllData();
        localList = new ArrayList<>();

        while (localCursor.moveToNext()) {
            localList.add(localCursor.getString(1));
        }

        Spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
        ad_floor = new ArrayAdapter(this, R.layout.custom_spinner, localList);
        ad_floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_floor.setAdapter(ad_floor);
        Spinner_floor.setOnItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: updating order no and receipt no " + variables.order_no + " " + variables.receipt_no);
        //orderDatabseHelper.updatReceiptAndOrderNoTable(String.valueOf(variables.order_no),String .valueOf(variables.receipt_no));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        SharedPreferences sharedPreferences = getSharedPreferences("mishra", MODE_PRIVATE);
        String address = sharedPreferences.getString("device", "");
        dbHelper = new DBDiscountHelper(this, "disisaab_offline.sqlite", null, 1);
        dbHelper.queryData("CREATE TABLE IF NOT EXISTS WAITER_DISCOUNT_ACCOUNT(Id INTEGER PRIMARY KEY AUTOINCREMENT, Waiter_id VARCHAR NOT NULL, Waiter_table VARCHAR NOT NULL, Waiter_name VARCHAR, Waiter_discount  VARCHAR, Waiter_area  VARCHAR)");
        if (address == null || address.equals("")) {
            Toast.makeText(this, "address not fiounf", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, " coul  find address", Toast.LENGTH_LONG).show();
            startService(new Intent(DrawerActivity.this, BlueServiceOnce.class));
//            try {
//                createBluetoothSocket(con_dev);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        grid_tables = (GridView) findViewById(R.id.grid_tables);
        gridSplittables = (GridView) findViewById(R.id.gridSplittables);

        grid = (LinearLayout) findViewById(R.id.grid);
        gridSplit = (LinearLayout) findViewById(R.id.gridSplit);

        //  new GetFloorAsynctask().execute();

        Outlet = AppPrefences.getOutlet(DrawerActivity.this);

        floor_list = new ArrayList<String>();

        helper = new DatabaseHelper(this);
        localCursor = helper.getAllData();
        localList = new ArrayList<>();

        while (localCursor.moveToNext()) {
            localList.add(localCursor.getString(1));
        }

        localList.add("sagar");
        orderDatabseHelper = new OrderDatabseHelper(this);
        customerHelper = new CustomerDatabaseHelper(DrawerActivity.this);
        initializingOrderAndReceiptNo();
        Spinner_floor = (Spinner) findViewById(R.id.spinner_floor);
        ad_floor = new ArrayAdapter(this, R.layout.custom_spinner, localList);
        ad_floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner_floor.setAdapter(ad_floor);
        Spinner_floor.setOnItemSelectedListener(this);

       /* sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Outlet = sharedPreferences.getString("Outlet", "");*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializingOrderAndReceiptNo() {
        Cursor receiptCursor = orderDatabseHelper.getReceiptAndOrderNoTable();
        if (receiptCursor.getCount() == 0) {
            orderDatabseHelper.insertReceiptAndOrderNoTable(0, 0);
            variables.order_no = 0;
            variables.receipt_no = 0;
        } else {
            receiptCursor.moveToNext();
            variables.order_no = Integer.parseInt(receiptCursor.getString(0));
            variables.receipt_no = Integer.parseInt(receiptCursor.getString(1));
            Log.d(TAG, "initializingOrderAndReceiptNo: setting order and receipt " + variables.order_no + " " + variables.receipt_no);
        }
    }

    //=======================================================================================================================
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action  bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.refresh) {
            Intent intent = new Intent(DrawerActivity.this, SyncActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        }

        //---------------------------------------------------------------------------------------------
        else if (id == R.id.nav_gallery) {

            // custom dialog
            final Dialog dialog = new Dialog(DrawerActivity.this);
            dialog.setContentView(R.layout.dialog_take_away);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            m_number = (EditText) dialog.findViewById(R.id.m_number);
            name = (EditText) dialog.findViewById(R.id.name);
            address = (EditText) dialog.findViewById(R.id.address);
            t_submit = (Button) dialog.findViewById(R.id.t_submit);

            t_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Mob = m_number.getText().toString();
                    Name = name.getText().toString();
                    Adress = address.getText().toString();
                    if (!Mob.equals("") & !Name.equals("") & !Adress.equals("")) {
                        Cursor customerCursor = customerHelper.getCustomer(Mob);
                        if (customerCursor.getCount() != 0) {
                            Toast.makeText(DrawerActivity.this, "You are already registered!! \n Please try with another mobile number", Toast.LENGTH_SHORT).show();
                        } else {
                            customerHelper.insertNewCustomer(
                                    Mob,
                                    Name,
                                    Adress
                            );
                            Toast.makeText(DrawerActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DrawerActivity.this, WaiterListActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    } else {
                        Toast.makeText(DrawerActivity.this, "Please Fill all entries", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            dialog.show();
        }

        //-------------------------------------------------------------------------------------------------
        else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(DrawerActivity.this, OrderListNew.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(DrawerActivity.this, ReportActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(DrawerActivity.this, DayCloseActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_send) {
            AppPrefences.setLogout(DrawerActivity.this, "");
            Intent in = new Intent(DrawerActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
          /*  AppPrefences.setLogout(DrawerActivity.this, "RESPOS-ID40739");
            Intent in = new Intent(DrawerActivity.this, LoginActivity.class);
            startActivity(in);
            finish();
*/
        } else if (id == R.id.nav_confi) {
            Intent in = new Intent(DrawerActivity.this, ConfigActivity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Selected_floor = Spinner_floor.getSelectedItem().toString();
        variables.selected_floor = Selected_floor;
        Log.e("<<<<<<<Selected_loc", Selected_floor);
        sessionManager = new SessionManager(this);
        sessionManager.setKeyUserArea(Selected_floor);
        //Log.d(TAG, "onItemSelected: selected item");

        final ArrayList<tableItem> localTable = new ArrayList<>();
        final Cursor local = helper.getAllDataTable2(Selected_floor);

        while (local.moveToNext()) {
            localTable.add(new tableItem(
                    local.getString(0),
                    local.getString(1),
                    local.getString(2),
                    local.getString(3),
                    local.getString(4),
                    local.getString(5),
                    local.getString(6),
                    local.getString(7),
                    local.getString(8)
            ));
        }

        CustomAdapter customAdapter1 = new CustomAdapter(DrawerActivity.this, localTable);
        grid_tables.setAdapter(customAdapter1);

        grid_tables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                t_name = localTable.get(position).getT_name();
                variables.selecetd_table_data = localTable.get(position);
//                variables.table_name = t_name;
//                variables.table_no = localTable.get(position).getT_id();
//                variables.table_status = localTable.get(position).getStatus();
//                helper.updateTableStatus(localTable.get(position).getT_id()
//                        ,localTable.get(position).getT_name(),
//                        localTable.get(position).getDin_area(),
//                        localTable.get(position).getAlias(),
//                        localTable.get(position).getOutlet_id(),
//                        localTable.get(position).getD_id(),
//                        "allocated");
                AppPrefences.setTable(DrawerActivity.this, t_name);
                Log.e("abcd>>>", t_name);
                variables.tableNumber = "1";
                //Log.d(TAG, "onItemClick: current status "+variables.table_status);
                if (variables.selecetd_table_data.getStatus().equals("free")) {
                    Intent intent = new Intent(DrawerActivity.this, WaiterListActivity.class);
                    startActivity(intent);
                } else {
                    if (!variables.selecetd_table_data.getWaiter_id().equals("N/A")) {
                        Cursor waiterCursor = helper.getWaiterDetails(variables.selecetd_table_data.getWaiter_id());
                        waiterCursor.moveToNext();
                        variables.selected_waiter_data = new waiterItem(
                                waiterCursor.getString(0),
                                waiterCursor.getString(1),
                                waiterCursor.getString(2),
                                waiterCursor.getString(3),
                                waiterCursor.getString(4),
                                waiterCursor.getString(5));
                        startActivity(new Intent(DrawerActivity.this, NewActivity.class));
                        /*Intent intent = new Intent(DrawerActivity.this, NewActivity.class);
                        intent.putExtra("Check", "Comeing");
                        startActivity(intent);*/
                    } else {
                        variables.selected_waiter_data = new waiterItem(
                                "NA",
                                "NA",
                                "NA",
                                "NA",
                                "NA",
                                "NA");
                        Intent intent = new Intent(DrawerActivity.this, NewActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        //new GetTableAsynctask().execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
    }

    //----------------------------------------------------------------------------------------

    class GetFloorAsynctask extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DrawerActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
            // arComentChat = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://twors.in/POS/Webservices/get_dining_area?outlet_id=" + Outlet;
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
                    JSONObject floor_obj = json.getJSONObject("floor");
                    JSONArray floor_array = floor_obj.getJSONArray("floor");
                    for (int i = 0; i < floor_array.length(); i++) {
                        JSONObject c = floor_array.getJSONObject(i);
                        String da_id = c.getString("da_id");
                        String da_name = c.getString("da_name");
                        String outlet_id = c.getString("outlet_id");

                        floor_list.add(da_name);
                        ad_floor.notifyDataSetChanged();
                    }

                    if (response.equalsIgnoreCase("1")) {
                        dialog.dismiss();


                        Toast.makeText(DrawerActivity.this, "response", Toast.LENGTH_LONG).show();
                      /*  Intent intent = new Intent(DrawerActivity.this, WaiterListActivity.class);
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

    //----------------------------------------------------------------------------------------

    class GetTableAsynctask extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DrawerActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
           /* arComentChat = new ArrayList<>();*/
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://twors.in/POS/Webservices/get_table_list?outlet_id=" + URLEncoder.encode(Outlet, "UTF-8") + "&din_area=" + URLEncoder.encode(Selected_floor, "UTF-8");
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
                    JSONObject tabels_obj = json.getJSONObject("tabels");
                    JSONArray tables_array = tabels_obj.getJSONArray("tables");
                    table_list = new ArrayList<String>();
                    for (int i = 0; i < tables_array.length(); i++) {
                        JSONObject c = tables_array.getJSONObject(i);
                        String t_id = c.getString("t_id");
                        String t_name = c.getString("t_name");
                        din_area = c.getString("din_area");
                        String alias = c.getString("alias");
                        String outlet_id = c.getString("outlet_id");
                        sFloorName = din_area;
                        table_list.add(t_name);

                    }

                    Log.e("test_floor", table_list.toString());
                    //CustomAdapter customAdapter1 = new CustomAdapter(getApplicationContext(), table_list);
                    // grid_tables.setAdapter(customAdapter1);

                    grid_tables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            t_name = table_list.get(position);
                            Log.e("abcd>>>", t_name);
                            Intent intent = new Intent(DrawerActivity.this, WaiterListActivity.class);
                            startActivity(intent);

                        }
                    });


                    if (response.equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        AppPrefences.setFloor(DrawerActivity.this, din_area);

                        Toast.makeText(DrawerActivity.this, "response", Toast.LENGTH_LONG).show();
                        /*Intent intent = new Intent(DrawerActivity.this, WaiterListActivity.class);
                        startActivity(intent);
*/
                    } else {
                        //Toast.makeText(ScrollingActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error", e.toString());
                    dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }

    //---------------------------------------------------------------------------------------------

    class GetTAwayAsynctask extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DrawerActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
            /*arComentChat = new ArrayList<>();*/
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://twors.in/POS/Webservices/takeaway?outlet_id=" + Outlet + "&guest_name=" + Name + "&address=" + Adress + "&mobile=" + Mob;

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
                    String message = json.getString("message");
                    Log.e("json>>>>>>>>>", json.toString());


                    // Toast.makeText(ActivityForumshow.this, response, Toast.LENGTH_LONG).show();
                    if (response.equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        //// AppPrefences.setGuest(DrawerActivity.this , Name);

                        Toast.makeText(DrawerActivity.this, "Guest Added Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DrawerActivity.this, WaiterListActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(DrawerActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }

    }

    //-------------------------------------------------------------------------------------------

}


