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
import com.example.debet_app_mobile.payload.UserDto;
import com.example.debet_app_mobile.payload.UserDtos;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<UserDtos> {


    public UserAdapter(Activity context, ArrayList<UserDtos> userDtos) {
        super(context, 0, userDtos);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }

        UserDtos current = getItem(position);

        TextView firstName = listItemView.findViewById(R.id.uFirstname);
        firstName.setText(current.getFirstName());

        TextView phone = listItemView.findViewById(R.id.uPhone);
        phone.setText(current.getPhone());

        ImageView icon = listItemView.findViewById(R.id.uIcon);

        if (current.isAccountNonExpired()){
            icon.setImageResource(R.drawable.ic_unlock);
        }else {
            icon.setImageResource(R.drawable.ic_lock);
        }

        return listItemView;
    }
}
