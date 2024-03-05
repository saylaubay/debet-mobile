package com.example.debet_app_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.debet_app_mobile.adapter.ViewPagerAdapter;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyAdd;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyList;
import com.example.debet_app_mobile.fragment.report.FragmentAllReport;
import com.example.debet_app_mobile.fragment.report.FragmentCompanyReport;
import com.example.debet_app_mobile.fragment.report.FragmentMyReport;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompanyActivity extends AppCompatActivity {

    EditText etCompany;
    Button btnAdd;
    Dialog dialog;
    AlertDialog.Builder alert;

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
        setContentView(R.layout.activity_company);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(CompanyActivity.this);
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
            Intent i = new Intent(CompanyActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);

        etCompany = findViewById(R.id.etCompany);
        btnAdd = findViewById(R.id.addBtn);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentCompanyList(), "Kompaniyalar dizimi");
        adapter.addFragment(new FragmentCompanyAdd(), "Kompaniya qosiw");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

//        SharedPreferences preferences = getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);

        String token = preferences.getString("token", "");

        token = "Bearer " + token;

        alert = new AlertDialog.Builder(this);

        String finalToken = token;
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String cName = etCompany.getText().toString();
//
//                if (cName.equals("")){
//                    Toast.makeText(CompanyActivity.this, "Kompaniya atin kiritin'!!!", Toast.LENGTH_LONG).show();
//                }else {
//                    CompDto compDto = new CompDto(cName);
//
//                    api.addCompany(finalToken,compDto).enqueue(new Callback<Void>() {
//                        @Override
//                        public void onResponse(Call<Void> call, Response<Void> response) {
//                            alert.setTitle("Qosiw");
//                            alert.setMessage("Kompaniy bazag'a qosildi!!!");
//                            alert.show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<Void> call, Throwable t) {
//                            Toast.makeText(CompanyActivity.this, "Qa'telik!!!", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                }
//            }
//        });



    }



}