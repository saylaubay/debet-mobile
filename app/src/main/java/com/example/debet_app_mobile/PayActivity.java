package com.example.debet_app_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.adapter.ViewPagerAdapter;
import com.example.debet_app_mobile.fragment.pay.FragmentNoPayed;
import com.example.debet_app_mobile.fragment.pay.FragmentPayList;
import com.example.debet_app_mobile.fragment.pay.FragmentPayed;
import com.example.debet_app_mobile.fragment.pay.PaySearchFragment;
import com.example.debet_app_mobile.fragment.report.FragmentAllReport;
import com.example.debet_app_mobile.fragment.report.FragmentCompanyReport;
import com.example.debet_app_mobile.fragment.report.FragmentMyReport;
import com.google.android.material.tabs.TabLayout;

import java.sql.Timestamp;


public class PayActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    public boolean isNetwork(){
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null) {
                networkInfo = manager.getActiveNetworkInfo();
            }
            return networkInfo != null && networkInfo.isConnected();
        }catch (NullPointerException e){
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(PayActivity.this);
            loadingBar.setMessage("Internet qosilmag'an!!!");
            loadingBar.setCancelable(false);
            loadingBar.setButton(DialogInterface.BUTTON_POSITIVE, "Jan'alaw", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    loadingBar.dismiss();//dismiss dialog
                    startActivity(getIntent());
                }
            });
            loadingBar.show();
        }

        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        long expireTime = preferences.getLong("expireTime", 0);
        Timestamp t = new Timestamp(System.currentTimeMillis());


        if (expireTime <= t.getTime() && expireTime != 0){

            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();

            startActivity(getIntent());
            Intent i = new Intent(PayActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new PaySearchFragment(),"");
        adapter.addFragment(new FragmentNoPayed(), "");
        adapter.addFragment(new FragmentPayed(), "");
        adapter.addFragment(new FragmentPayList(), "");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_search);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tolew_kerek);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tolengen);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_list);


    }
}