package com.example.debet_app_mobile.fragment.debet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiResponseDebet;
import com.example.debet_app_mobile.ApiResponseDebets;
import com.example.debet_app_mobile.DebetActivity;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.adapter.CompanyAdapter;
import com.example.debet_app_mobile.adapter.DebetAdapter;
import com.example.debet_app_mobile.payload.DebetDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentDebetNoPayedList extends Fragment {

    private View view;
    public ListView listView;

    public FragmentDebetNoPayedList() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.debet_no_payed_list_fragment, container, false);

        listView = view.findViewById(R.id.deb_no_payed_listview);

        ArrayList<DebetDto> debets = new ArrayList<DebetDto>();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);
        ReportActivity reportActivity = new ReportActivity();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        String token = preferences.getString("token","");
        token = "Bearer " + token;

        DebetActivity debetActivity = (DebetActivity) getActivity();



        Long contractId = debetActivity.contractId;
        debets.clear();
        listView.setAdapter(null);
        api.getDebetByContractIdNoPayed(token, contractId).enqueue(new Callback<ApiResponseDebet>() {
            @Override
            public void onResponse(Call<ApiResponseDebet> call, Response<ApiResponseDebet> response) {
                List<DebetDto> debetDtoList = response.body().getData();
                debets.addAll(debetDtoList);

                DebetAdapter adapter = new DebetAdapter(getActivity(), debets);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ApiResponseDebet> call, Throwable t) {

            }
        });


        String finalToken = token;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DebetDto debetDto = (DebetDto)adapterView.getAdapter().getItem(i);

                ImageView icon = view.findViewById(R.id.dIcon);
                if (debetDto.isPaid()){
                    debetDto.setPaid(false);
                    icon.setImageResource(R.drawable.ic_unlock);
                }else {
                    debetDto.setPaid(true);
                    icon.setImageResource(R.drawable.ic_lock);
                }

                api.editDebet(finalToken,debetDto.getId(), debetDto.isPaid()).enqueue(new Callback<ApiResponseDebets>() {
                    @Override
                    public void onResponse(Call<ApiResponseDebets> call, Response<ApiResponseDebets> response) {
//                        Toast.makeText(getActivity(), "isledi", Toast.LENGTH_LONG).show();
                        startActivity(getActivity().getIntent());
                    }

                    @Override
                    public void onFailure(Call<ApiResponseDebets> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

//                api.updateDebet(finalToken, debetDto, debetDto.getId()).enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        System.out.println("ppppppp");
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//
//                    }
//                });

            }
        });


        return view;
    }
}
