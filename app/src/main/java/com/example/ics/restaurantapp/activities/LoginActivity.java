package com.example.ics.restaurantapp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ics.restaurantapp.SharedPreference.SessionManager;
import com.example.ics.restaurantapp.Utils.AppPrefences;
import com.example.ics.restaurantapp.handler.HttpHandler;
import com.example.ics.restaurantapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText input_name,input_password,input_outlet;
    Button btn_login;
    String Name , Password , Outlet="";
    TextView tx_ip;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    String user_name;
    CheckBox token_check;
    ProgressDialog dialog;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);

        if (!AppPrefences.getLogout(this).equalsIgnoreCase("")){
            startActivity(new Intent(this,DrawerActivity.class));
            finish();
        }
        token_check = findViewById(R.id.token_check);
        tx_ip = (TextView)findViewById(R.id.tx_ip);
        input_name = (EditText)findViewById(R.id.input_name);
        input_password = (EditText)findViewById(R.id.input_password);
        input_outlet = (EditText)findViewById(R.id.input_outlet);
        btn_login = (Button)findViewById(R.id.btn_login);

       /* input_name.setText("sagarview");
        input_password.setText("sagar@19");
        input_outlet.setText("RESPOS-ID40739");*/

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name = input_name.getText().toString();
                Password = input_password.getText().toString();
                Outlet = input_outlet.getText().toString();
                checkConnection();
            }
        });

        Intent intent = getIntent();
        String Outlet = intent.getStringExtra("id1");


        // add button listener
        tx_ip.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.ip_alert);

                dialog.setTitle("Title...");

                TextView text = (TextView) dialog.findViewById(R.id.text);

                Button btn_update = (Button)dialog.findViewById(R.id.btn_update);
                // if button is clicked, close the custom dialog
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this,getApplication().getClass());
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra("id1","id");
                       // startActivity(intent);
                        dialog.dismiss();
                    }
                });

                Button btn_cancel = (Button)dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

 //---------------------------------------------------------------------

    class GetLoginAsynctask extends AsyncTask<String, String, String> {
        String output = "";
      //  ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this);
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
            String sever_url="http://twors.in/POS/Webservices/admin_login?username="+Name+"&password="+Password+"&outlet_id="+Outlet;
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

                   /* SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Outlet", Outlet);
                    editor.commit();*/


                    // Toast.makeText(ActivityForumshow.this, response, Toast.LENGTH_LONG).show();
                    if (response.equalsIgnoreCase("1")) {
                        dialog.dismiss();
                        AppPrefences.setLogout(LoginActivity.this , Outlet);

                        Paper.book().write("total_pending",0);
                        Paper.book().write("net_sale",0);
                        Toast.makeText(LoginActivity.this, "things is "+token_check.isChecked(), Toast.LENGTH_SHORT).show();
                            sessionManager = new SessionManager(LoginActivity.this);
                          sessionManager.setKey_Bill_Token(token_check.isChecked());
                        Boolean aBoolean = sessionManager.getKeyBillToken();

                                Toast.makeText(LoginActivity.this,"Login Successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this , Header_Footer_Act.class));
//                        Intent intent = new Intent(LoginActivity.this,SyncActivity.class);
                     //   AppPrefences.setOutlet(LoginActivity.this , "RESPOS-ID40739");
                        AppPrefences.setOutlet(LoginActivity.this , Outlet);
                        Log.e("Outlet>>>>>",Outlet);
//                        startActivity(intent);

                    }

                    else
                    {
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
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
            Toast.makeText(LoginActivity.this, "You are connected to Internet", Toast.LENGTH_SHORT).show();
            new GetLoginAsynctask().execute();
        }else{
            Toast.makeText(LoginActivity.this, "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        try{
            dialog.dismiss();
        }catch (NullPointerException e){
            System.out.println(""+e);
        }
        super.onDestroy();
    }
}
