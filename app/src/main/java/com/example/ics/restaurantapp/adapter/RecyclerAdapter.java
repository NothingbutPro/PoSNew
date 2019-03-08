package com.example.ics.restaurantapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ics.restaurantapp.DbHelper.DatabaseHelper;
import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.activities.NewActivity;
import com.example.ics.restaurantapp.handler.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ICS on 20/03/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private static final String TAG = "RecyclerAdapter";
    private ArrayList<String> menulist;
    public Context context;
    String server_url;
    ArrayList<String> category_list;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String Outlet;
    private String categoryItem;

    DatabaseHelper helper;
    ArrayList<String> localCategoryList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView idProductName;
        CardView card_view;
        int pos;

        public ViewHolder(View view) {
            super(view);

            idProductName = (TextView) view.findViewById(R.id.idProductName);
            card_view = (CardView) view.findViewById(R.id.card_view);

            Outlet = AppPrefences.getOutlet(context);
        }

    }

    public static Context mContext;

    public RecyclerAdapter(Context mContext, ArrayList<String> menu_list) {
        context = mContext;
        menulist = menu_list;
        helper = new DatabaseHelper(mContext);
        localCategoryList = new ArrayList<>();
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.re_item, parent, false);

        return new RecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder viewHolder, int position) {
        //final Map<String, String> mMap = menulist.get(position);
        viewHolder.idProductName.setText(menulist.get(position));
        viewHolder.card_view.setTag(viewHolder);
        viewHolder.pos = position;

        viewHolder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryItem = viewHolder.idProductName.getText().toString();
                Log.d(TAG, "onClick: cardview click"+categoryItem);
                Log.e("categoryItem", categoryItem);
                //new GetMenuItemAsynctask().execute();
                Cursor localCursor = helper.getAllDataOfCategory(categoryItem);
                Log.d(TAG, "onClick: cursor count "+ localCursor.getCount());
                localCategoryList.clear();
                while (localCursor.moveToNext()) {
                    localCategoryList.add(localCursor.getString(1));
                    Log.d(TAG, "onClick: list Item "+localCursor.getString(1));
                }
                //localCategoryList.add("hellow");

                ((NewActivity) context).getValues(localCategoryList);
            }
        });


    }

    //---------------------------------------------------------

    class GetMenuItemAsynctask extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
            category_list = new ArrayList<>();
           /* arComentChat = new ArrayList<>();*/
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                server_url = "http://twors.in/POS/Webservices/get_group_list?outlet_id=" + Outlet + "&category=" + categoryItem;
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
                    JSONObject group_obj = json.getJSONObject("group");
                    JSONArray group_array = group_obj.getJSONArray("group");
                    for (int i = 0; i < group_array.length(); i++) {
                        JSONObject c = group_array.getJSONObject(i);
                        String g_id = c.getString("g_id");
                        String g_name = c.getString("g_name");
                        String category = c.getString("category");
                        String outlet_id = c.getString("outlet_id");
                        category_list.add(g_name);
                    }
                    Log.e("category_list?", category_list.toString());

                    if (response.equalsIgnoreCase("1")) {
                        dialog.dismiss();

                        /*Toast.makeText(context, "response", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, NewActivity.class);
                        context.startActivity(intent);*/
                        //((NewActivity)context).getValues(category_list);


                    } else {
                        //((NewActivity)context).getValues(category_list);
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

    //----------------------------------------------------------

    @Override
    public int getItemCount() {
        return menulist.size();
    }

}
