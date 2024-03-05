package com.example.debet_app_mobile;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.debet_app_mobile.adapter.ViewPagerAdapter;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyAdd;
import com.example.debet_app_mobile.fragment.report.FragmentAllReport;
import com.example.debet_app_mobile.fragment.report.FragmentCompanyReport;
import com.example.debet_app_mobile.fragment.report.FragmentMyReport;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    Button btn;

    public String token;

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
        setContentView(R.layout.activity_report);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(ReportActivity.this);
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

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);



        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        long expireTime = preferences.getLong("expireTime", 0);
        Timestamp t = new Timestamp(System.currentTimeMillis());


        if (expireTime <= t.getTime() && expireTime != 0){

            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();

            startActivity(getIntent());
            Intent i = new Intent(ReportActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        String token = preferences.getString("token", "");
        String role = preferences.getString("role", "");

        token = "Bearer " + token;

        String finalToken = token;



//        btn = findViewById(R.id.myBtn);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        FragmentMyReport fragmentMyReport = new FragmentMyReport();
        FragmentCompanyReport fragmentCompanyReport = new FragmentCompanyReport();
        FragmentAllReport fragmentAllReport = new FragmentAllReport();

        adapter.addFragment(new FragmentMyReport(), "Menin' esabatim");
        if (role.equals("ADMIN") || role.equals("SUPER_ADMIN")){
            adapter.addFragment(new FragmentCompanyReport(), "Company report");
        }
        if (role.equals("SUPER_ADMIN")) {
            adapter.addFragment(new FragmentAllReport(), "All report");
        }


        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);





//        FragmentMyReport fragmentMyReport = new FragmentMyReport();
//        fragmentMyReport.btnklik();
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(ReportActivity.this, "OK", Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}