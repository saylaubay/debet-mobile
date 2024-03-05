package com.example.debet_app_mobile;

import androidx.appcompat.app.AlertDialog;
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
import com.example.debet_app_mobile.fragment.company.FragmentCompanyAdd;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyList;
import com.example.debet_app_mobile.fragment.user.FragmentUserAdd;
import com.example.debet_app_mobile.fragment.user.FragmentUserList;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {


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
        setContentView(R.layout.activity_user);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(UserActivity.this);
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

//            Toast.makeText(this, "Waqit tawsildi Pay", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();

            startActivity(getIntent());
            Intent i = new Intent(UserActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentUserList(), "Userler dizimi");
        adapter.addFragment(new FragmentUserAdd(), "User qosiw");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }
}