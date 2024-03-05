package com.example.debet_app_mobile;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.debet_app_mobile.payload.CalcDto;
import com.example.debet_app_mobile.payload.CalcRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalcActivity extends AppCompatActivity {

    TextView percent, part, summa;
    Button btnCalc;
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
        setContentView(R.layout.activity_calc);

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(CalcActivity.this);
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

        percent = findViewById(R.id.percent);
        part = findViewById(R.id.part);
        summa = findViewById(R.id.summa);
        btnCalc = findViewById(R.id.calcBtn);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);
        System.out.println(Api.BASE_URL);
        long expireTime = preferences.getLong("expireTime", 0);
        Timestamp t = new Timestamp(System.currentTimeMillis());


        if (expireTime <= t.getTime() && expireTime != 0){

            MainActivity mainActivity=new MainActivity();
            mainActivity.a = true;

//            Toast.makeText(this, "Waqit tawsildi Calc", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            finish();

            startActivity(getIntent());
            Intent i = new Intent(CalcActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }


        String token = preferences.getString("token", "");

        token = "Bearer " + token;

        alert = new AlertDialog.Builder(this);


        String finalToken = token;
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sSumma = summa.getText().toString();
                String sPercent = percent.getText().toString();
                String sPart = part.getText().toString();

                if (sSumma.equals("") || sPercent.equals("") || sPart.equals("")) {
                    Toast.makeText(CalcActivity.this, "Mag'liwmatlardi toliq toltirin'!!!", Toast.LENGTH_SHORT).show();

                }else {
                    double reqSumma = Double.parseDouble(sSumma);
                    double reqPercent = Double.parseDouble(sPercent);
                    int reqPart = Integer.parseInt(sPart);

                    CalcDto calcDto = new CalcDto(reqSumma, reqPart, reqPercent);

                    api.calc(calcDto, finalToken).enqueue(new Callback<ApiResponseCalc>() {
                        @Override
                        public void onResponse(Call<ApiResponseCalc> call, Response<ApiResponseCalc> response) {
                            ApiResponseCalc body = response.body();
//
                            Toast.makeText(CalcActivity.this, "" +response.body().getData().toString(), Toast.LENGTH_SHORT).show();

                            final TextView t_view = new TextView(getApplicationContext());
                            final TextView t_title = new TextView(getApplicationContext());

                            DecimalFormat formatter = new DecimalFormat("#,###.00");
//                            String get_summa = formatter.format(s);
//                            String get_c = formatter.format(count);

                            t_view.setText("Bo'lek : \t" + String.valueOf(body.getData().getPart()) + "\n" +
                                    "Ayliq to'lem : \t" + formatter.format(body.getData().getMonthSumma()) + "\n" +
                                    "Ja'mi protsent : \t" + formatter.format(body.getData().getAllPercentSumma()) + "\n" +
                                    "Toliq summa : \t" + formatter.format(body.getData().getAllSumma()) + "\n" +
                                    "Ayliq protsent : \t" + formatter.format(body.getData().getMonthPercentSumma()) + "\n\n");

                            t_view.setTextSize(25);
                            t_view.setPadding(20,40,20,40);
//                            alert.setPositiveButtonIcon(getDrawable(R.drawable.phone));
//                            alert.setPositiveButton("", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(CalcActivity.this, "888", Toast.LENGTH_SHORT).show();
//                                }
//                            });

                            alert.setView(t_view);
                            alert.show();

//                            Toast.makeText(CalcActivity.this, "OK", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ApiResponseCalc> call, Throwable t) {

                        }
                    });

//                    api.calc(calcDto, finalToken).enqueue(new Callback<CalcRes>() {
//                        @Override
//                        public void onResponse(Call<CalcRes> call, Response<CalcRes> response) {
//                            CalcRes body = response.body();
//
//                            Toast.makeText(CalcActivity.this, "" +response.body().toString(), Toast.LENGTH_SHORT).show();
//
//                            final TextView t_view = new TextView(getApplicationContext());
//                            final TextView t_title = new TextView(getApplicationContext());
//
//                            DecimalFormat formatter = new DecimalFormat("#,###.00");
////                            String get_summa = formatter.format(s);
////                            String get_c = formatter.format(count);
//
//                            t_view.setText("Bo'lek : \t" + String.valueOf(body.getPart()) + "\n" +
//                                    "Ayliq to'lem : \t" + formatter.format(body.getMonthSumma()) + "\n" +
//                                    "Ja'mi protsent : \t" + formatter.format(body.getAllPercentSumma()) + "\n" +
//                                    "Toliq summa : \t" + formatter.format(body.getAllSumma()) + "\n" +
//                                    "Ayliq protsent : \t" + formatter.format(body.getMonthPercentSumma()) + "\n\n");
//
//                            t_view.setTextSize(25);
//                            t_view.setPadding(20,40,20,40);
////                            alert.setPositiveButtonIcon(getDrawable(R.drawable.phone));
////                            alert.setPositiveButton("", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialogInterface, int i) {
////                                    Toast.makeText(CalcActivity.this, "888", Toast.LENGTH_SHORT).show();
////                                }
////                            });
//
//                            alert.setView(t_view);
//                            alert.show();
//
////                            Toast.makeText(CalcActivity.this, "OK", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onFailure(Call<CalcRes> call, Throwable t) {
//                            btnCalc.setText(t.getMessage());
//                            Toast.makeText(CalcActivity.this, "QATE CALC", Toast.LENGTH_LONG).show();
//                        }
//                    });






                }

            }
        });


//        api.getCompany(token).enqueue(new Callback<CompanyDto>() {
//            @Override
//            public void onResponse(Call<CompanyDto> call, Response<CompanyDto> response) {
//                Toast.makeText(CalcActivity.this, "OKKKKKKk", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<CompanyDto> call, Throwable t) {
//                Toast.makeText(CalcActivity.this, "FAILLL", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

}