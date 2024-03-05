package com.example.debet_app_mobile.fragment.pay;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.adapter.ContractAdapter2;
import com.example.debet_app_mobile.payload.ApiResponseContractResp;
import com.example.debet_app_mobile.payload.ContractDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentPayList extends Fragment {

    private View view;
    ListView listView;

    public FragmentPayList() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pay_list_fragment, container, false);

        listView = view.findViewById(R.id.pay_list);

        ArrayList<ContractDto> contracts = new ArrayList<ContractDto>();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        String token = preferences.getString("token","");
        String role = preferences.getString("role","");
        token = "Bearer " + token;

        contracts.clear();
        listView.setAdapter(null);

        api.getAllContractByClientAndUser(token).enqueue(new Callback<ApiResponseContractResp>() {
            @Override
            public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {
                List<ContractDto> data = response.body().getData();
                contracts.addAll(data);

                if (getActivity()!=null) {
                    ContractAdapter2 adapter = new ContractAdapter2(getActivity(), contracts);
                    listView.setAdapter(adapter);
                }
//                ContractAdapter2 adapter = new ContractAdapter2(getActivity(), contracts);
//                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContractDto data = (ContractDto) adapterView.getAdapter().getItem(i);

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                String date = "";
                if (data.isEnable()){
                    date = data.getUpdatedAt().toString().substring(0,16);
                }else {
                    date = "---";
                }

//                        double ayliq = data.getPrice() / 100 * data.getPercent() + data.getPrice() / data.getPart();
//                        String ayliqTolem = String.format("%.2f", ayliq);

                double ayliqTolem = data.getPrice() / 100 * data.getPercent() + data.getPrice() / data.getPart();
                double ayliqPercentSumma = data.getPrice() / 100 * data.getPercent();
                double toliqPercentSumma = data.getPrice() / 100 * data.getPercent() * data.getPart();
                double toliqQaytarilganSumma = data.getPrice() + data.getPrice() / 100 * data.getPercent() * data.getPart();

                DecimalFormat formatter = new DecimalFormat("#,###.00");
//                String get_value = formatter.format(1000000000);

                alert.setTitle(data.isEnable()?"To'lengen":"To'lenbegen");
                alert.setMessage("User : " + data.getWorker().getFirstName() + "\n" +
                        "Klient : " + data.getClient().getFirstName() + " " + data.getClient().getLastName() +"\n" +
                        "Produkt : "+data.getProductName()+"\n" +
                        "Bo'lek : " +data.getPart() + "\n" +
                        "Summa : "+formatter.format(data.getPrice())+"\n" +
                        "Ayliq to'lem : " + formatter.format(ayliqTolem) + "\n" +
                        "Ayliq protsent summa : "+formatter.format(ayliqPercentSumma)+"\n" +
                        "Toliq protsent summa : "+formatter.format(toliqPercentSumma)+"\n" +
                        "Toliq qaytarilatug'in summa : "+formatter.format(toliqQaytarilganSumma)+"\n"+
                        "Protsent : "+data.getPercent()+"\n" +
                        "San'e : "+data.getCreatedAt().toString().substring(0,16) + "\n" +
                        "To'lep boling'an sane : " + date);

                alert.setPositiveButtonIcon(getActivity().getResources().getDrawable(R.drawable.phone));
                alert.setPositiveButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getActivity(), data.getClient().getPhone(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getClient().getPhone()));
                        startActivity(intent);
                    }
                });

                alert.create().show();

            }
        });


        return view;
    }
}
