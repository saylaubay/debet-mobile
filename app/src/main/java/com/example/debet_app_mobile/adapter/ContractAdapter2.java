package com.example.debet_app_mobile.adapter;

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
import com.example.debet_app_mobile.payload.ContractDto;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ContractAdapter2 extends ArrayAdapter<ContractDto> {


    public ContractAdapter2(Activity activity, ArrayList<ContractDto> contractDtos) {
        super(activity, 0, contractDtos);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.pay_item, parent, false);
        }
        ContractDto current = getItem(position);

        TextView clientName = listItemView.findViewById(R.id.contr_client_name);
        clientName.setText(current.getClient().getFirstName());

        TextView productName = listItemView.findViewById(R.id.contr_prod_name);
        productName.setText(current.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###.00");
//        String get_value = formatter.format(1000000000);

        TextView summa = listItemView.findViewById(R.id.contr_summa);
        summa.setText(formatter.format(current.getPrice()));

        int year = current.getCreatedAt().getYear() + 1900;
        int month = current.getCreatedAt().getMonth() + 1;
        int day = current.getCreatedAt().getDate();
        String sane = day + "." + month + "." + year;

        TextView date = listItemView.findViewById(R.id.contr_date);
        date.setText(sane);

        ImageView icon = listItemView.findViewById(R.id.contr_icon);

        if (current.isEnable()){
            icon.setImageResource(R.drawable.ic_tolengen);
        }else {
            icon.setImageResource(R.drawable.ic_tolew_kerek);
        }

        return listItemView;
    }
}
