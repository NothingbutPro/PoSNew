package com.example.ics.restaurantapp.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.handler.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class TAwayActivity extends AppCompatActivity {
    EditText mob,name,adress;
    Button btn_submit;
    Toolbar toolbar_ta;
    String  Mob,Adress,Name="";
    String Outlet;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taway);

      /*  sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Outlet = sharedPreferences.getString("Outlet", "");
*/
        Outlet = AppPrefences.getOutlet(TAwayActivity.this);


        mob = (EditText) findViewById(R.id.mob);
        name = (EditText)findViewById(R.id.name);
        adress = (EditText)findViewById(R.id.adress);
        btn_submit = (Button)findViewById(R.id.btn_submit);

        toolbar_ta = (Toolbar) findViewById(R.id.toolbar_ta);

        toolbar_ta.setNavigationIcon(R.drawable.ic_arrow);

        toolbar_ta.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mob = mob.getText().toString();
                Name = name.getText().toString();
                Adress  = adress.getText().toString();

            }
        });
    }

    //-----------------------------------------------------------------------------

    class GetTAwayAsynctask extends AsyncTask<String, String, String> {
        String output = "";
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(TAwayActivity.this);
            dialog.setMessage("Processing");
            dialog.setCancelable(true);
            dialog.show();
            super.onPreExecute();
            /*arComentChat = new ArrayList<>();*/
        }

        @Override
        protected String doInBackground(String... params) {

         /*   String urlParameters = "";
            try {
                urlParameters = "f_id=" + URLEncoder.encode(s_id, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            String sever_url="http://twors.in/POS/Webservices/takeaway?outlet_id="+Outlet+"&guest_name="+Name+"&address="+Adress+"&mobile="+Mob;
            Log.e("sever_url>>>>>>>>>", sever_url);
            output = HttpHandler.makeServiceCall(sever_url);
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
                    Log.e("json>>>>>>>>>",json.toString());


                    // Toast.makeText(ActivityForumshow.this, response, Toast.LENGTH_LONG).show();
                    if (response.equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        AppPrefences.setGuest(TAwayActivity.this , Name);

                        Toast.makeText(TAwayActivity.this,"Guest Added Successfully", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(TAwayActivity.this,WaiterListActivity.class);
                        startActivity(intent);
                        finish();

                    }

                    else
                    {
                        Toast.makeText(TAwayActivity.this,message,Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    dialog.dismiss();
                }
                super.onPostExecute(output);
            }
        }

    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void checkConnection(){
        if(isOnline()){
            Toast.makeText(TAwayActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
            new GetTAwayAsynctask().execute();
        }else{
            Toast.makeText(TAwayActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }


    //-----------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
