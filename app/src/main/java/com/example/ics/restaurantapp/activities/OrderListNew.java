package com.example.ics.restaurantapp.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.ics.restaurantapp.R;
import com.example.ics.restaurantapp.fregment.AllFragment;
import com.example.ics.restaurantapp.fregment.DineFragment;
import com.example.ics.restaurantapp.fregment.TakeAwayFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderListNew extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list_new);

        viewPager = (ViewPager) findViewById(R.id.viewpager1);
        createViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Running");
       // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.notifi, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Completed");
       // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_input_black_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

      /*  TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Take Away");
      //  tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.completed, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);*/

    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AllFragment(), "");
        adapter.addFrag(new DineFragment(), "");
       // adapter.addFrag(new TakeAwayFragment(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
