package com.example.debet_app_mobile;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class TEst extends AppCompatActivity {

    @Override
    protected void onStart() {
        SharedPreferences preferences = getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        super.onStart();
    }
}
