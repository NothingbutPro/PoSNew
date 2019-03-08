package com.example.ics.restaurantapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ICS on 10/03/2018.
 */

public class DineList_Adapter extends RecyclerView.Adapter<DineList_Adapter.ViewHolder> {

    private static final String TAG = "DineList_Adapter";
    private ArrayList<HashMap<String,String>> dinelist;
    public Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView i_p;
        public TextView twt_name, twt_enq, twt_mob,twt_nxt,twt_date,phn_img;
        public Button btn_vw;
        CardView card_view;
        public LinearLayout linr;
        int pos;

        public ViewHolder(View view) {
            super(view);
           /* i_p = (ImageView)view.findViewById(R.id.i_p);
            twt_name = (TextView) view.findViewById(R.id.twt_name);
            twt_enq = (TextView) view.findViewById(R.id.twt_enq);
            twt_mob = (TextView) view.findViewById(R.id.twt_mob);
            twt_nxt = (TextView)view.findViewById(R.id.twt_nxt);
            twt_date = (TextView)view.findViewById(R.id.twt_date);
            phn_img = (TextView)view.findViewById(R.id.phn_img);
            btn_vw = (Button)view.findViewById(R.id.btn_vw);
            card_view = (CardView) view.findViewById(R.id.card_view);*/
        }
    }

    public static Context mContext;
    public DineList_Adapter(Context mContext, ArrayList<HashMap<String, String>> follow_list) {
        context = mContext;
        dinelist = follow_list;

    }

    @Override
    public DineList_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_all_item, parent, false);

        return new DineList_Adapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DineList_Adapter.ViewHolder viewHolder, int position) {
        final Map<String,String> mMap = dinelist.get(position);
        viewHolder.twt_name.setText(mMap.get("customerName"));
        viewHolder.twt_enq.setText(mMap.get("enqno"));
        viewHolder.twt_mob.setText(mMap.get("mnumber"));
        viewHolder.twt_nxt.setText(mMap.get("followup"));
        viewHolder.twt_date.setText(mMap.get("enqDate"));
        viewHolder.phn_img.setText(mMap.get("logo"));
        viewHolder.card_view.setTag(viewHolder);
        viewHolder.btn_vw.setTag(viewHolder);
        viewHolder.pos = position;

        viewHolder.btn_vw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent = new Intent(context,View_Enq.class);
                intent.putExtra("followlist",mMap.get("enqTime"));
                context.startActivity(intent);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return dinelist.size();
    }

}
