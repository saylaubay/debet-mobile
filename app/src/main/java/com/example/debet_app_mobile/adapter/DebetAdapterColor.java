package com.example.debet_app_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.payload.DebetDto;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DebetAdapterColor extends ArrayAdapter<DebetDto> {



    public DebetAdapterColor(Activity context, ArrayList<DebetDto> debetDtos) {
        super(context, 0, debetDtos);
    }


    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.pay_item, parent, false);
        }
        DebetDto current = getItem(position);

//        Timestamp start = new Timestamp(System.currentTimeMillis());
//        start.setHours(0);
//        start.setMinutes(0);
//        start.setSeconds(1);
//
//        Timestamp end = new Timestamp(System.currentTimeMillis());
//        start.setHours(23);
//        start.setMinutes(59);
//        start.setSeconds(59);
        int checkYear = current.getPayDate().getYear()+1900;
        int checkMonth = current.getPayDate().getMonth()+1;
        int checkDay = current.getPayDate().getDate();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//        if (){
//
//        }


        TextView clientName = listItemView.findViewById(R.id.contr_client_name);
        clientName.setText(current.getContract().getClient().getFirstName());

        TextView productName = listItemView.findViewById(R.id.contr_prod_name);
        productName.setText(current.getContract().getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###.00");
//        String get_value = formatter.format(1000000000);

        TextView summa = listItemView.findViewById(R.id.contr_summa);
        summa.setText(formatter.format(current.getSumma()));

        int year = current.getCreatedAt().getYear() + 1900;
        int month = current.getCreatedAt().getMonth() + 1;
        int day = current.getCreatedAt().getDate();
        String sane = day + "." + month + "." + year;

        TextView date = listItemView.findViewById(R.id.contr_date);
        date.setText(current.getPayDate().toString().substring(0,16));

        ImageView icon = listItemView.findViewById(R.id.contr_icon);

        if (current.isPaid()){
            icon.setImageResource(R.drawable.ic_tolengen);
        }else {
            icon.setImageResource(R.drawable.ic_tolew_kerek);
        }


        return listItemView;
    }
}
