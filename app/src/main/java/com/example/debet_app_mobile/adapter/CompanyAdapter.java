package com.example.debet_app_mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.TestDto;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CompanyAdapter extends android.widget.ArrayAdapter<TestDto> {



    public CompanyAdapter(Activity context, ArrayList<TestDto> testDtos) {
        super(context, 0, testDtos);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_company, parent, false);
        }
        TestDto current = getItem(position);

        TextView cName = listItemView.findViewById(R.id.cName);

        cName.setText(current.getName());

        ImageView icon = listItemView.findViewById(R.id.cIcon);

//        current.isActive() ? icon.setImageResource(R.drawable.ic_unlock) : icon.setImageResource(R.drawable.ic_lock);

        if (current.isActive()){
            icon.setImageResource(R.drawable.ic_unlock);
        }else {
            icon.setImageResource(R.drawable.ic_lock);
        }

        return listItemView;
    }



}
