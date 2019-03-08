package com.example.ics.restaurantapp.fregment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.Local.variables;
import com.example.ics.restaurantapp.ModelDB.orderListItem;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.adapter.AllListAdapterNew;
import com.example.ics.restaurantapp.adapter.CardAdapter;

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

import javax.net.ssl.HttpsURLConnection;


public class AllFragment extends Fragment {

    RecyclerView card_recyclerview;
    Context context;
    ArrayList<HashMap<String,String>> d_list;
    CardAdapter cardAdapter;

    OrderDatabseHelper orderDatabseHelper;
    DatabaseHelper helper;
    List<orderListItem> orderList;

    RecyclerView recyclerView;
    String guest_name,   order_for, table_no;
    String floor_no, order_by,  date_time;
    private int serial_no,reciept_no,amount;


    public AllFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderList = new ArrayList<>();
        orderList.clear();

        orderDatabseHelper = new OrderDatabseHelper(getActivity());
        helper = new DatabaseHelper(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all, container,false);

        Cursor localCursor = orderDatabseHelper.getRunningOrderList();
        if(localCursor.equals("null")||localCursor.equals("")){
            Toast.makeText(getActivity(),"no database",Toast.LENGTH_SHORT).show();

        }
        orderList.clear();
        while (localCursor.moveToNext()){
            String Wname;
            try {


                Cursor cursor = helper.getWaiterDetails(localCursor.getString(14));
                if (cursor.equals("null") || cursor.equals("")) {

                }
                if (cursor.getCount() != 0) {
                    cursor.moveToNext();
                    Wname = cursor.getString(1);
                } else {
                    Wname = "NA";
                }
                guest_name = localCursor.getString(0);
                reciept_no = localCursor.getInt(1);
                order_for = localCursor.getString(2);
                floor_no = localCursor.getString(3);
                date_time = localCursor.getString(4);
                guest_name=localCursor.getString(5);
                amount = localCursor.getInt(6);
                order_by = localCursor.getString(9);
                order_by = Wname;
                table_no = localCursor.getString(10);
                orderList.add(new orderListItem(
                        localCursor.getInt(0),
                        variables.generateReceiptNo(localCursor.getInt(1)),
                        localCursor.getString(2),
                        localCursor.getString(3),
                        localCursor.getString(4),
                        localCursor.getString(5),
                        localCursor.getInt(6),
                        Wname
                ));
//                new PostData().execute();
            }catch (Exception e){

            }
        }

        recyclerView = view.findViewById(R.id.recycler_all);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        AllListAdapterNew adapter = new AllListAdapterNew(getActivity(),orderList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }
    class PostData extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = new ProgressDialog(getActivity());
            dialog.show();


        }

        protected String doInBackground(String... arg0) {

            try {


                URL url = new URL("http://twors.in/POS/Webservices/getOrder");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("guest_name",guest_name);
                postDataParams.put("serial_no",serial_no+"" );
                postDataParams.put("reciept_no", reciept_no+"");
                postDataParams.put("order_for", order_for);
                postDataParams.put("table_no", table_no);
                postDataParams.put("floor_no", floor_no);
                postDataParams.put("order_by", order_by);
                postDataParams.put("amount", amount+"");
                postDataParams.put("date_time", date_time+"");


                Log.e("PostConclusion>>>",postDataParams.toString());


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        StringBuffer Ss = sb.append(line);
                        Log.e("Ss", Ss.toString());
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                dialog.dismiss();

                JSONObject jsonObject = null;
                Log.e("PostConclusion", result.toString());
                try {

                    jsonObject = new JSONObject(result);
                    String response = jsonObject.getString("response");
                    String message = jsonObject.getString("message");
                    JSONObject data_obj = jsonObject.getJSONObject("data");
                    String guest_name = data_obj.getString("guest_name");
                    String serial_no = data_obj.getString("serial_no");
                    String reciept_no = data_obj.getString("reciept_no");
                    String order_for = data_obj.getString("order_for");
                    String table_no = data_obj.getString("table_no");
                    String floor_no = data_obj.getString("floor_no");
                    String order_by = data_obj.getString("order_by");
                    String amount = data_obj.getString("amount");
                    String date_time = data_obj.getString("date_time");
                    Log.e(">>>>",  response + " " + message);

                    if (response.equalsIgnoreCase("True")) {
                        Toast.makeText(getActivity(), "Data Post Successfully", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        public String getPostDataString(JSONObject params) throws Exception {

            StringBuilder result = new StringBuilder();
            boolean first = true;

            Iterator<String> itr = params.keys();

            while (itr.hasNext()) {

                String key = itr.next();
                Object value = params.get(key);

                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value.toString(), "UTF-8"));
            }

            return result.toString();
        }
    }

}
