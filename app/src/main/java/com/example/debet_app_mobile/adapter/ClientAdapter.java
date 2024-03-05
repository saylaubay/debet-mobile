package com.example.debet_app_mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.payload.ClientDto;

import java.util.ArrayList;

public class ClientAdapter extends ArrayAdapter<ClientDto> {



    public ClientAdapter(Activity context, ArrayList<ClientDto> clientDtos) {
        super(context, 0, clientDtos);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_client, parent, false);
        }

        ClientDto current = getItem(position);

        TextView firstName = listItemView.findViewById(R.id.clFirstname);
        firstName.setText(current.getFirstName());

        TextView lastName = listItemView.findViewById(R.id.clLastname);
        lastName.setText(current.getLastName());

        TextView phone = listItemView.findViewById(R.id.clPhone);
        phone.setText(current.getPhone());

        return listItemView;
    }
}
