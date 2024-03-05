package com.example.debet_app_mobile;

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

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

     long expireTime = 1000 * 60 * 60 * 24 * 15;

    TextView username, password;
    Button button;
    ProgressDialog loadingBar;

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

        if (!isNetwork()){
            ProgressDialog loadingBar = new ProgressDialog(LoginActivity.this);
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

//        SharedPreferences preferences = getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);
        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);
SharedPreferences.Editor editor = preferences.edit();
        boolean b = preferences.getBoolean("auth", false);
        if (b){
            String usrname = preferences.getString("username","");
            String pssword = preferences.getString("password","");

//            username.setText(usrname);
//            password.setText(pssword);
//            editor.apply();
            Intent i = new Intent(LoginActivity.this, ExploreActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
            startActivity(i);
            finish();
        }
        Toast.makeText(LoginActivity.this, "Siz sistemadan paydalaniwin'iz sheklengen!!!", Toast.LENGTH_LONG).show();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.loginBtn);

        loadingBar = new ProgressDialog(LoginActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Ku'tin'222");
                loadingBar.setMessage("Login parol tekserilmekte22222");
//                loadingBar.setButton();
                loadingBar.show();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Api api = retrofit.create(Api.class);

                String usrname = username.getText().toString();
                String psword = password.getText().toString();

                LoginDto loginDto  = new LoginDto();
                loginDto.setUsername(usrname);
                loginDto.setPassword(psword);


//                String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWRtaW4iLCJpYXQiOjE2NjIzMTM3MjIsImV4cCI6MTY2NDA0MTcyMiwicm9sZSI6eyJpZCI6MSwiY3JlYXRlZEF0IjoxNjYyMzEzMjE2NDI1LCJ1cGRhdGVkQXQiOjE2NjIzMTMyMTY0MjUsImNyZWF0ZWRCeSI6bnVsbCwidXBkYXRlZEJ5IjpudWxsLCJyb2xlTmFtZSI6IlNVUEVSX0FETUlOIiwiYXV0aG9yaXR5IjoiU1VQRVJfQURNSU4ifX0.2n9JGF4ADIbipGqsJeMLzfB5cWlq4AIJatCMW_Dooxk0gLiYLXanMFue9ZAxupxelVTbSNdZk8pKOfvz_K0Viw";

//                api.login(loginDto).enqueue(new Callback<ApiResponse2>() {
//                    @Override
//                    public void onResponse(Call<ApiResponse2> call, Response<ApiResponse2> response) {
//                        if (response.code()==401){
//                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//                            Toast.makeText(LoginActivity.this, "Siz sistemadan paydalaniwin'iz sheklengen!!!", Toast.LENGTH_LONG).show();
//                        }else
//
//                        if (response.body().isSuccess()){
//                            Toast.makeText(LoginActivity.this, "AWMETLI!!!", Toast.LENGTH_LONG).show();
//
//                            loadingBar.dismiss();
//                            SharedPreferences.Editor editor = preferences.edit();
//
//                            Timestamp timeS = new Timestamp(System.currentTimeMillis());
//
//                            long t = timeS.getTime()+expireTime;
//
//                            Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
//
//
//                            editor.putString("token", response.body().getData());
//                            editor.putString("username", usrname);
//                            editor.putBoolean("auth", true);
//                            editor.putLong("expireTime", t);
//
//
//                            editor.putString("role", response.body().getData().toString());
//
//                            editor.apply();
////                            editor.commit();
//
//                            Intent intent = new Intent(LoginActivity.this, ExploreActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//
//                            Toast.makeText(LoginActivity.this, "Systemag'a kirdin'iz!", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(LoginActivity.this, "QATELIK", Toast.LENGTH_SHORT).show();
////                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiResponse2> call, Throwable t) {
//                        loadingBar.dismiss();
//                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });




                api.login(loginDto).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                        loadingBar.dismiss();
//                        Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();

                        if (response.code()==401){
                            Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "401 Siz sistemadan paydalaniwin'iz sheklengen!!!", Toast.LENGTH_LONG).show();
                        }else

                            if (response.body().isSuccess()){

                                loadingBar.dismiss();
                                SharedPreferences.Editor editor = preferences.edit();

                                Timestamp timeS = new Timestamp(System.currentTimeMillis());

                                long t = timeS.getTime()+expireTime;

//                                Toast.makeText(LoginActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();

                                editor.putString("token", response.body().getData().getToken());
                                editor.putString("username", usrname);
                                editor.putBoolean("auth", true);
                                editor.putLong("expireTime", t);


                                editor.putString("role", response.body().getData().getRole());
                                Toast.makeText(LoginActivity.this, "Loginga kirdi = "+response.body().getData().getToken(), Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginActivity.this, "-----------------------\n" + response.body().getData().getToken() + " - " + usrname + " - " + true + " - " + t, Toast.LENGTH_LONG).show();

                                editor.apply();
//                            editor.commit();

                                Toast.makeText(LoginActivity.this, "" + usrname +" "+t, Toast.LENGTH_LONG).show();


                                Toast.makeText(LoginActivity.this, "Systemag'a kirdin'iz!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, ExploreActivity.class);
//                                Intent intent = new Intent(getApplicationContext(), ExploreActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
//                                finish();

                        }else {
                            Toast.makeText(LoginActivity.this, "QATELIK", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        loadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "LOGIN FAIL", Toast.LENGTH_LONG).show();
                    }
                });
//                loadingBar.dismiss();


//                api.getToken(loginDto).enqueue(new Callback<ApiResponse>() {
//                    @Override
//                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
////                        Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_SHORT).show();
//                        if (response.body().isSuccess()){
//                            Intent intent = new Intent(LoginActivity.this, ExploreActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                            startActivity(intent);
//                            finish();
//
//                            Toast.makeText(LoginActivity.this, "Siz Systemg'a login boldin'iz!", Toast.LENGTH_SHORT).show();
//                        }else {
//                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                        ApiResponse body = response.body();
//                        ResDto data = response.body().getData();
//                        loadingBar.dismiss();
//                        Toast.makeText(LoginActivity.this, data.getToken(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiResponse> call, Throwable t) {
//                        username.setText(t.getMessage());
//                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

//                api.getToken(loginDto).enqueue(new Callback<ApiResponse>() {
//                    @Override
//                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                        Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        System.out.println(t.getMessage());
//                        username.setText(t.getMessage());
//                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });

            }
        });


    }


}