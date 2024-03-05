package com.example.debet_app_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.debet_app_mobile.adapter.ViewPagerAdapter;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyAdd;
import com.example.debet_app_mobile.fragment.company.FragmentCompanyList;
import com.example.debet_app_mobile.fragment.debet.FragmentDebetNoPayedList;
import com.example.debet_app_mobile.fragment.debet.FragmentDebetPayedList;
import com.example.debet_app_mobile.payload.BuyDtos;
import com.example.debet_app_mobile.payload.BuyDtoss;
import com.example.debet_app_mobile.payload.ContractDto;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DebetActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Button btn;

    public Long contractId=0L;

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
        setContentView(R.layout.activity_debet);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(DebetActivity.this);
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

        contractId = (Long) getIntent().getSerializableExtra("contractId");
        System.out.println(contractId);

//        TextView txt = findViewById(R.id.deb_txt);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        btn = findViewById(R.id.infoBtn);

        SharedPreferences preferences = getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        String token = preferences.getString("token","");
        String role = preferences.getString("role","");
        token = "Bearer " + token;

        String finalToken = token;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.getContractById(finalToken,contractId).enqueue(new Callback<ApiResponseContract2>() {
                    @Override
                    public void onResponse(Call<ApiResponseContract2> call, Response<ApiResponseContract2> response) {
                        BuyDtoss data = response.body().getData();
                        AlertDialog.Builder alert = new AlertDialog.Builder(DebetActivity.this);
                        String date = "";
                        if (data.isEnabled()){
                            date = data.getUpdatedAt().toString().substring(0,16);
                        }else {
                            date = "---";
                        }

//                        double ayliq = data.getPrice() / 100 * data.getPercent() + data.getPrice() / data.getPart();
//                        String ayliqTolem = String.format("%.2f", ayliq);

                        double ayliqTolem = data.getPrice() / 100 * data.getPercent() + data.getPrice() / data.getPart();
                        double ayliqPercentSumma = data.getPrice() / 100 * data.getPercent();
                        double toliqPercentSumma = data.getPrice() / 100 * data.getPercent() * data.getPart();
                        double toliqQaytarilganSumma = data.getPrice() + data.getPrice() / 100 * data.getPercent() * data.getPart();

                        alert.setTitle(data.isEnabled()?"To'lengen":"To'lenbegen");
                        alert.setMessage("User : " + data.getWorker().getFirstName() + "\n" +
                                "Klient : " + data.getClient().getFirstName() + " " + data.getClient().getLastName() +"\n" +
                                "Produkt : "+data.getProductName()+"\n" +
                                "Bo'lek : " +data.getPart() + "\n" +
                                "Summa : "+data.getPrice()+"\n" +
                                "Ayliq to'lem : " + ayliqTolem + "\n" +
                                "Ayliq protsent summa : "+ayliqPercentSumma+"\n" +
                                "Toliq protsent summa : "+toliqPercentSumma+"\n" +
                                "Toliq qaytarilatug'in summa : "+toliqQaytarilganSumma+"\n"+
                                "Protsent : "+data.getPercent()+"\n" +
                                "San'e : "+data.getCreatedAt().toString().substring(0,16) + "\n" +
                                "To'lep boling'an sane : " + date);
                        alert.create().show();

                    }

                    @Override
                    public void onFailure(Call<ApiResponseContract2> call, Throwable t) {

                    }
                });
            }
        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentDebetNoPayedList(), "To'lenbegenler");
        adapter.addFragment(new FragmentDebetPayedList(), "To'lengenler");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);




        ArrayList<ContractDto> contracts = new ArrayList<ContractDto>();



        api.getContractById(token, contractId).enqueue(new Callback<ApiResponseContract2>() {
            @Override
            public void onResponse(Call<ApiResponseContract2> call, Response<ApiResponseContract2> response) {
                BuyDtoss data = response.body().getData();

            }

            @Override
            public void onFailure(Call<ApiResponseContract2> call, Throwable t) {

            }
        });


    }
}