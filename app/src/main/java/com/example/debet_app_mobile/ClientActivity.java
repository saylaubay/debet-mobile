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

import com.example.debet_app_mobile.adapter.ViewPagerAdapter;
import com.example.debet_app_mobile.fragment.client.FragmentClientAdd;
import com.example.debet_app_mobile.fragment.client.FragmentClientList;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyAdd;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyList;
import com.google.android.material.tabs.TabLayout;

import java.sql.Timestamp;

public class ClientActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

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
        setContentView(R.layout.activity_client);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(ClientActivity.this);
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
            Intent i = new Intent(ClientActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentClientList(), "Klientler dizimi");
        adapter.addFragment(new FragmentClientAdd(), "Klient qosiw");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);



    }
}