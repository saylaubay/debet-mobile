package com.example.debet_app_mobile.adapter;

import android.app.Activity;
import android.content.Context;
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

public class ContractAdapter extends ArrayAdapter<ContractDto> {


    public ContractAdapter(Activity context, ArrayList<ContractDto> contractDtos) {
        super(context, 0, contractDtos);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_contract, parent, false);
        }
        ContractDto current = getItem(position);

        TextView clientName = listItemView.findViewById(R.id.con_client_name);
        clientName.setText(current.getClient().getFirstName());

        TextView productName = listItemView.findViewById(R.id.con_prod_name);
        productName.setText(current.getProductName());

        DecimalFormat formatter = new DecimalFormat("#,###.00");
//        String get_value = formatter.format(1000000000);

        TextView summa = listItemView.findViewById(R.id.con_summa);
        summa.setText(formatter.format(current.getPrice()));

//        ImageView icon = listItemView.findViewById(R.id.con_icon);

        return listItemView;
    }
}
