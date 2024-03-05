package com.example.debet_app_mobile.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.payload.DebetDto;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DebetAdapter extends ArrayAdapter<DebetDto> {



    public DebetAdapter(Activity context, ArrayList<DebetDto> debetDtos) {
        super(context, 0, debetDtos);
    }


    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_debet, parent, false);
        }
        DebetDto current = getItem(position);

        TextView deb_date = listItemView.findViewById(R.id.deb_date);
        deb_date.setText(current.getMonthName());

        TextView deb_summa = listItemView.findViewById(R.id.deb_summa);
        double s = current.getSumma();
//        String suma = String.format("%.4f", s);

        DecimalFormat formatter = new DecimalFormat("#,###.00");
//        String get_value = formatter.format(1000000000);

        deb_summa.setText(formatter.format(s));

//        CheckBox checkBox = listItemView.findViewById(R.id.check_item);

        ImageView icon = listItemView.findViewById(R.id.dIcon);

        if (current.isPaid()){
//            checkBox.setChecked(true);
            icon.setImageResource(R.drawable.ic_lock);
        }else {
//            checkBox.setChecked(false);
            icon.setImageResource(R.drawable.ic_unlock);
        }


        return listItemView;
    }
}
