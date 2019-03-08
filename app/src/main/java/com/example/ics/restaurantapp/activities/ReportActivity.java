package com.example.ics.restaurantapp.activities;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ics.restaurantapp.DbHelper.OrderDatabseHelper;
import com.example.ics.restaurantapp.R;

import io.paperdb.Paper;

public class ReportActivity extends AppCompatActivity {
    Toolbar toolbar_report;
    TextView totalPendingCollection,netSale,totalNetPendingcollection,saleCash,saleCard,salePaytm,saleOther,totalCash,totalCard,totalPaytm,totalOther;

    OrderDatabseHelper orderDatabseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        orderDatabseHelper = new OrderDatabseHelper(this);

        totalPendingCollection = (TextView)findViewById(R.id.totalPendingCollection);
        netSale = (TextView)findViewById(R.id.netSale);
        totalNetPendingcollection = (TextView)findViewById(R.id.totalNetPendingcollection);

        saleCash = (TextView)findViewById(R.id.saleCash);
        totalCash = (TextView)findViewById(R.id.totalCash);

        saleCard = (TextView)findViewById(R.id.saleCard);
        totalCard = (TextView)findViewById(R.id.totalCard);

        salePaytm = (TextView)findViewById(R.id.salePaytm);
        totalPaytm = (TextView)findViewById(R.id.totalPaytm);

        saleOther = (TextView)findViewById(R.id.saleOther);
        totalOther = (TextView)findViewById(R.id.totalOther);

        toolbar_report = (Toolbar) findViewById(R.id.toolbar_report);

        toolbar_report.setNavigationIcon(R.drawable.ic_arrow);

        toolbar_report.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        totalPendingCollection.setText(String.valueOf(Paper.book().read("total_pending")));
        netSale.setText(String .valueOf(Paper.book().read("net_sale")));
        int x = Paper.book().read("net_sale");
        int y = Paper.book().read("total_pending");
        totalNetPendingcollection.setText(String.valueOf(x+y));

        Cursor paymentCursor = orderDatabseHelper.getPaymentOptions();
        if(paymentCursor.getCount()!=0){
            paymentCursor.moveToNext();
            float precash = paymentCursor.getFloat(1);
            float precard = paymentCursor.getFloat(2);
            float prepaytm = paymentCursor.getFloat(3);
            float preother = paymentCursor.getFloat(4);

            saleCash.setText(String.valueOf(precash));
            saleCard.setText(String.valueOf(precard));
            salePaytm.setText(String.valueOf(prepaytm));
            saleOther.setText(String.valueOf(preother));

            totalCash.setText(String.valueOf(precash));
            totalCard.setText(String.valueOf(precard));
            totalPaytm.setText(String.valueOf(prepaytm));
            totalOther.setText(String.valueOf(preother));
        }else
        {
            saleCash.setText(String.valueOf(0));
            saleCard.setText(String.valueOf(0));
            salePaytm.setText(String.valueOf(0));
            saleOther.setText(String.valueOf(0));

            totalCash.setText(String.valueOf(0));
            totalCard.setText(String.valueOf(0));
            totalPaytm.setText(String.valueOf(0));
            totalOther.setText(String.valueOf(0));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
