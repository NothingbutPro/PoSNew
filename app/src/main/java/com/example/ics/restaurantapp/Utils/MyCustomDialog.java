package com.example.ics.restaurantapp.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

import com.example.ics.restaurantapp.R;

/**
 * Created by BUSYAPK on 11/8/2017.
 */

public class MyCustomDialog {

    private ProgressDialog dialog;
    private View view;
    Context context;

    public MyCustomDialog(Context context) {
        this.context = context;
        dialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        view = new View(context);
    }

    public void ShowProgressDialog(Boolean cancellable) {
        try {
            /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                Drawable drawable = new ProgressBar(context).getIndeterminateDrawable().mutate();
                drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent),
                        PorterDuff.Mode.SRC_IN);
                dialog.setIndeterminateDrawable(drawable);
            }*/
            dialog.show();
//            dialog.setContentView(R.layout.custom_progress_view);
            dialog.setMessage("Please Wait...");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            /*Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            mThread.start();*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CancelProgressDialog() {
        /*if (dialog != null) {
            if (dialog.isShowing())
            {
                dialog.dismiss();
            }
        }*/
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                dialog.dismiss();
            }
        }
    }
}
