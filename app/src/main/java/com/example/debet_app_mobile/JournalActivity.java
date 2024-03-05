package com.example.debet_app_mobile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.debet_app_mobile.adapter.CompanyAdapter;
import com.example.debet_app_mobile.adapter.DebetAdapter;
import com.example.debet_app_mobile.adapter.DebetAdapter2;
import com.example.debet_app_mobile.adapter.DebetAdapterColor;
import com.example.debet_app_mobile.payload.DebetDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JournalActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder alert;

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
        setContentView(R.layout.activity_journal);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(JournalActivity.this);
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

        listView = findViewById(R.id.listview_journal);

        ArrayList<DebetDto> debets = new ArrayList<DebetDto>();


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

            Toast.makeText(this, "Waqit tawsildi Pay", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();

            startActivity(getIntent());
            Intent i = new Intent(JournalActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        String token = preferences.getString("token", "");
        String role = preferences.getString("role", "");
        String dbusername = preferences.getString("username", "");

        token = "Bearer " + token;

        listView.setAdapter(null);
        debets.clear();
        api.getJournal(token).enqueue(new Callback<ApiResponseDebet>() {
            @Override
            public void onResponse(Call<ApiResponseDebet> call, Response<ApiResponseDebet> response) {
                Toast.makeText(JournalActivity.this, "Jurnal ga kirdi", Toast.LENGTH_SHORT).show();

                List<DebetDto> data = response.body().getData();
//                int i=1;
//                for (DebetDto datum : data) {
//                    datum.setNomer(i);
//                    i++;
//                }

                debets.addAll(data);

                DebetAdapter2 adapter = new DebetAdapter2(JournalActivity.this, debets);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ApiResponseDebet> call, Throwable t) {
                Toast.makeText(JournalActivity.this, " jurnal qate  "+t.toString(), Toast.LENGTH_LONG).show();
            }
        });

        alert = new AlertDialog.Builder(this);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DebetDto debetDto = (DebetDto)adapterView.getAdapter().getItem(i);

//                listView.getRootView().setBackgroundColor(Color.BLUE);

                DecimalFormat formatter = new DecimalFormat("#,###.00");
//                String get_value = formatter.format(1000000000);

                final TextView t_view = new TextView(getApplicationContext());
                final TextView t_phone = new TextView(getApplicationContext());

//                "Klient : " + debetDto.getContract().getClient().getFirstName() + " " + debetDto.getContract().getClient().getPhone() +"\n" +
//                        "Produkt : "+debetDto.getContract().getProductName()+"\n" +
//                        "Ayliq to'lem : "+formatter.format(debetDto.getSumma())+"\n" +
//                        "To'lew waqti : " +debetDto.getPayDate().toString().substring(0,16) + "\n"
//
//                final TextView t_view = new TextView(getApplicationContext());
//                final TextView t_title = new TextView(getApplicationContext());

                t_view.setText("Klient : " + debetDto.getContract().getClient().getFirstName() + " " + debetDto.getContract().getClient().getLastName() +"\n" +
                        "Produkt : "+debetDto.getContract().getProductName()+"\n" +
                        "Ayliq to'lem : "+formatter.format(debetDto.getSumma())+"\n" +
                        "To'lew waqti : " +debetDto.getPayDate().toString().substring(0,16));

                t_view.setTextSize(20);
                t_view.setPadding(20,40,20,40);
                alert.setPositiveButtonIcon(getDrawable(R.drawable.phone));

                t_phone.setTextSize(20);

                t_phone.setText(" \t" + debetDto.getContract().getClient().getPhone());

                alert.setPositiveButton(t_phone.getText().toString(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + debetDto.getContract().getClient().getPhone()));
                        startActivity(intent);
                    }
                });

                alert.setView(t_view);
                alert.show();


            }
        });
        


    }
}