package com.example.ics.restaurantapp.fregment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Retrofit.BaseUrlClass;
import com.example.ics.restaurantapp.Utils.MyCustomDialog;
import com.example.ics.restaurantapp.adapter.AdapterFor_Dine;
import com.example.ics.restaurantapp.adapter.AdapterFor_MenuItem;
import com.example.ics.restaurantapp.adapter.AllListAdapterFor_Dine;
import com.example.ics.restaurantapp.adapter.AllListAdapterNew;
import com.example.ics.restaurantapp.adapter.CardAdapter;
import com.example.ics.restaurantapp.adapter.RecyclerTouchListener;
import com.example.ics.restaurantapp.handler.HttpHandler;
import com.example.ics.restaurantapp.model.MenuItem_Response;
import com.example.ics.restaurantapp.model.Menu_Detail;
import com.example.ics.restaurantapp.model.Menu_Items_For_Dine;
import com.example.ics.restaurantapp.model.Order_Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DineFragment extends Fragment {
    RecyclerView card_recyclerview;
    Context context;
    ArrayList<HashMap<String, String>> d_list;
    CardAdapter cardAdapter;

    OrderDatabseHelper orderDatabseHelper;
    DatabaseHelper helper;
    List<orderListItem> dineOrderList;
    ArrayList<Menu_Detail> message;
    ArrayList<Order_Item> order_items = new ArrayList<>();

    RecyclerView recyclerView;
    String guest_name, order_for, table_no;
    String floor_no, order_by, date_time;
    private int serial_no, reciept_no, amount;
    private FragmentManager fragmentManager = getFragmentManager();
    private boolean isViewShown = false;
    private Boolean isStarted = false;
    private Boolean isVisible = false;
    private String server_url;
    LinearLayoutManager linearLayoutManager;
    private AdapterFor_Dine adapterFor_dine;
    private Map<String, List<Menu_Items_For_Dine>> menuListMap = new HashMap<String, List<Menu_Items_For_Dine>>();
    private ArrayList<Menu_Items_For_Dine> menu_items_for_dines = new ArrayList<Menu_Items_For_Dine>();
    private MyCustomDialog myCustomDialog;
    private ProgressDialog dialog;

    public DineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dineOrderList = new ArrayList<>();
        dineOrderList.clear();

        orderDatabseHelper = new OrderDatabseHelper(getActivity());
        helper = new DatabaseHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dine, container, false);
        myCustomDialog = new MyCustomDialog(getActivity());
        message = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_all);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        getAllOrders();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                myCustomDialog.ShowProgressDialog(false);
                Order_Item order_item = order_items.get(position);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.fragment_menu_item, null);
                final RecyclerView recyclerViewItem = (RecyclerView) mView.findViewById(R.id.recyclerView);
                final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                recyclerViewItem.setLayoutManager(manager);

                Call<MenuItem_Response> responseCall = BaseUrlClass.getInterface().getMenuItemResponseCall(order_item.getId());
                responseCall.enqueue(new Callback<MenuItem_Response>() {

                    @Override
                    public void onResponse(Call<MenuItem_Response> call, Response<MenuItem_Response> response) {
                        MenuItem_Response item_response = response.body();
                        if (response.isSuccessful()) {
                            message = item_response.getMessage();
                            if (message.size() > 0) {
                                myCustomDialog.CancelProgressDialog();
                                AdapterFor_MenuItem adapterFor_menuItem = new AdapterFor_MenuItem(getActivity(), message);
                                recyclerViewItem.setAdapter(adapterFor_menuItem);
                            } else {
                                myCustomDialog.CancelProgressDialog();
                                Toast.makeText(getActivity(), "Item List Is Empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            myCustomDialog.CancelProgressDialog();
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MenuItem_Response> call, Throwable t) {
                        myCustomDialog.CancelProgressDialog();
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                Toast.makeText(getActivity(), order_item.getId() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    }

    private void callProgress() {

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Processing");
        dialog.setCancelable(true);
        dialog.show();
    }

    class GetConclusion extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            server_url = "http://twors.in/POS/Webservices/get_orders";
           /* try {

            } catch (Exception e) {
                e.printStackTrace();
            }*/
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
//                    JSONObject message_obj = json.getJSONObject("message");

                    JSONArray Data_array = json.getJSONArray("message");
                    for (int i = 0; i < Data_array.length(); i++) {
                        JSONObject c = Data_array.getJSONObject(i);
                        String id = c.getString("id");
                        String Bill_No = c.getString("Bill_No");
                        String Customer_name = c.getString("Customer_name");
                        String Dinenig_Area = c.getString("Dinenig_Area");
                        String Table_No = c.getString("Table_No");
                        String Waiter_Name = c.getString("Waiter_Name");
                        String Date_Time = c.getString("Date_Time");
                        String Beofer_tax_amount = c.getString("Beofer_tax_amount");
                        String Tax_amount = c.getString("Tax_amount");
                        String SGST = c.getString("SGST");
                        String CGST = c.getString("CGST");
                        String Total_Amount = c.getString("Total_Amount");
                        String Discount_amount = c.getString("Discount_amount");
                        String Round_off_amount = c.getString("Round_off_amount");
                        String Payment_method = c.getString("Payment_method");
                        boolean isVisited = false;

                        order_items.add(new Order_Item(id, Bill_No, Customer_name, Dinenig_Area, Table_No, Waiter_Name, Date_Time, Beofer_tax_amount,
                                Tax_amount, SGST, CGST, Total_Amount, Discount_amount, Round_off_amount, Payment_method));

                    }
                    adapterFor_dine = new AdapterFor_Dine(getActivity(), order_items);
                    recyclerView.setAdapter(adapterFor_dine);
                    recyclerView.setLayoutManager(linearLayoutManager);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //  dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        order_items.clear();
        if (visible) {
            new GetConclusion().execute();
        }
    }
}
