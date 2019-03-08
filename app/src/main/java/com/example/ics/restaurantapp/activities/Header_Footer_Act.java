package com.example.ics.restaurantapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.SharedPreference.SessionManager;

public class Header_Footer_Act extends AppCompatActivity {
    Button Res_next;
    EditText Res_name,Res_type,Restype2;
    SessionManager resmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_footer);
        Res_next = findViewById(R.id.res_next);
        Res_name = findViewById(R.id.res_name);
        Res_type = findViewById(R.id.restype);
        Restype2 = findViewById(R.id.restype2);
        resmanager = new SessionManager(this);
        try {
            if(!resmanager.getKeyBillResName().isEmpty() && !resmanager.getKeyBillResName().isEmpty())
            {
                Res_name.setText(resmanager.getKeyBillResName());
                Res_type.setText(resmanager.getKeyBillResType());
            }
        }
        catch (Exception e)
        {

        }
        Res_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Res_name.getText().toString().isEmpty() && !Res_type.getText().toString().isEmpty())
                {
                    if(Res_name.getText().length() <2)
                    {
                        Res_name.setError("Name is too Short");
                    }
                    else
                        {
                        try{
                            if(!resmanager.getKeyBillResName().isEmpty())
                            {
                                startActivity(new Intent(Header_Footer_Act.this , DrawerActivity.class));
                                resmanager.setKeyBillResName(Res_name.getText().toString());
                                resmanager.setKeyBillResType(Res_type.getText().toString());
                                resmanager.setKeyBillResTyp2e(Restype2.getText().toString());
                                Toast.makeText(Header_Footer_Act.this, "name is"+resmanager.getKeyBillResName(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(Header_Footer_Act.this, "type is"+resmanager.getKeyBillResType(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }catch (Exception e)
                        {
                            startActivity(new Intent(Header_Footer_Act.this , SyncActivity.class));
                            resmanager.setKeyBillResName(Res_name.getText().toString());
                            resmanager.setKeyBillResType(Res_type.getText().toString());
                            Toast.makeText(Header_Footer_Act.this, "name is"+resmanager.getKeyBillResName(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Header_Footer_Act.this, "type is"+resmanager.getKeyBillResType(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(Header_Footer_Act.this, "Head is empty", Toast.LENGTH_SHORT).show();
                            finish();
                        }


//                        finish();
                    }

                }

            }
        });
    }
}
