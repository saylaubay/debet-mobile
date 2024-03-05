package com.example.debet_app_mobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.debet_app_mobile.payload.UserDtos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExploreActivity extends AppCompatActivity {

    TextView txtAdmin, txtUser, mFirstname, mBalance;
    LinearLayout calc, buy, pay, report, company, client, user, journal;

    CardView sec_company, sec_user;

    ImageView exit;

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
        setContentView(R.layout.activity_explore);


        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(ExploreActivity.this);
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

//        txtAdmin = findViewById(R.id.txtAdmin);
//        txtUser = findViewById(R.id.txtUser);
//
//        SharedPreferences preferences = getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);
//
//        String string = preferences.getString("token", "test");
//
//        JWT jwt = new JWT(string);
//
//        Map<String, Claim> claims = jwt.getClaims();
//        Claim role = jwt.getClaim("role");
//        String s = role.asString();
//
//        txtUser.setText(s);

        calc = findViewById(R.id.calc);
        buy = findViewById(R.id.buy);
        pay = findViewById(R.id.pay);
        report = findViewById(R.id.report);
        company = findViewById(R.id.company);
        client = findViewById(R.id.client);
        user = findViewById(R.id.user);
        journal = findViewById(R.id.journal);

        mFirstname = findViewById(R.id.mFirstname);
        mBalance = findViewById(R.id.mBalance);

        exit = findViewById(R.id.exit);

        sec_company = findViewById(R.id.sec_company);
        sec_user = findViewById(R.id.sec_user);

//        SharedPreferences preferences = getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        String token = preferences.getString("token", "");
        String role = preferences.getString("role", "");

        token = "Bearer " + token;

//        Toast.makeText(ExploreActivity.this, " token = " + token+"\n role = "+role, Toast.LENGTH_LONG).show();


        if (role.equals("SUPER_ADMIN")) {
            sec_company.setVisibility(View.VISIBLE);
            sec_user.setVisibility(View.VISIBLE);
        }

        DecimalFormat formatter = new DecimalFormat("#,###.00");
//        String get_value = formatter.format(1000000000);

        api.getUser(token).enqueue(new Callback<ApiResponseUserAdd>() {
            @Override
            public void onResponse(Call<ApiResponseUserAdd> call, Response<ApiResponseUserAdd> response) {

                if (response.code()==401){
                    Intent intent = new Intent(ExploreActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    Toast.makeText(ExploreActivity.this, "Login paroldi kiritin'", Toast.LENGTH_SHORT).show();
                }

                UserDtos data = response.body().getData();
                mFirstname.setText(data.getFirstName());
                mBalance.setText(formatter.format(data.getBalance()));
//                Toast.makeText(ExploreActivity.this, "OK", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ApiResponseUserAdd> call, Throwable t) {
                Toast.makeText(ExploreActivity.this, "Internetti tekserin'!!!", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.clear();
                editor.apply();

                Intent i = new Intent(ExploreActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
                finish();

            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, CalcActivity.class);
                startActivity(intent);

            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, BuyActivity.class);
                startActivity(intent);
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, PayActivity.class);
                startActivity(intent);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        if (role.equals("SUPER_ADMIN")) {

            company.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ExploreActivity.this, CompanyActivity.class);
                    startActivity(intent);
                }
            });
        }
        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, ClientActivity.class);
                startActivity(intent);
            }
        });

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExploreActivity.this, JournalActivity.class);
                startActivity(intent);
            }
        });


        if (role.equals("SUPER_ADMIN")) {
            user.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ExploreActivity.this, UserActivity.class);
                    startActivity(intent);
                }
            });
        }


    }
}