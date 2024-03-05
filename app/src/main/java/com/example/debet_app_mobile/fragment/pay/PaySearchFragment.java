package com.example.debet_app_mobile.fragment.pay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.DebetActivity;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.adapter.CompanyAdapter;
import com.example.debet_app_mobile.adapter.ContractAdapter;
import com.example.debet_app_mobile.adapter.ContractAdapter2;
import com.example.debet_app_mobile.payload.ApiResponseContractResp;
import com.example.debet_app_mobile.payload.ContractDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaySearchFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    public ListView listView;
    EditText etSearch;
    Button btn;


    public PaySearchFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pay_search_fragment, container, false);

        listView = view.findViewById(R.id.search_contr_list);
        etSearch = view.findViewById(R.id.etSearch);
        btn = view.findViewById(R.id.searchBtn);

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



        String finalToken = token;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contracts.clear();
                listView.setAdapter(null);
                api.getContractByClientNumber(finalToken,etSearch.getText().toString()).enqueue(new Callback<ApiResponseContractResp>() {
                    @Override
                    public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {
                        List<ContractDto> data = response.body().getData();
                        contracts.addAll(data);


                        if (getActivity()!=null) {
                            ContractAdapter2 adapter = new ContractAdapter2(getActivity(), contracts);
                            listView.setAdapter(adapter);
                        }
//                        startActivity(getActivity().getIntent());
//                        ContractAdapter2 adapter = new ContractAdapter2(getActivity(), contracts);
//                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {
                        contracts.clear();
                        listView.setAdapter(null);
                    }
                });
            }
        });

//        etSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                contracts.clear();
//                listView.setAdapter(null);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                /** Code* **/
//                contracts.clear();
//                listView.setAdapter(null);
//                api.getContractByClientNumber(finalToken,charSequence.toString()).enqueue(new Callback<ApiResponseContractResp>() {
//                    @Override
//                    public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {
//                        List<ContractDto> data = response.body().getData();
//                        contracts.addAll(data);
//
//                        ContractAdapter2 adapter = new ContractAdapter2(getActivity(), contracts);
//                        listView.setAdapter(adapter);
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {
//                        contracts.clear();
//                        listView.setAdapter(null);
//                    }
//                });
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContractDto contractDto = (ContractDto)adapterView.getAdapter().getItem(i);

                Intent intent = new Intent(getContext(), DebetActivity.class);
                intent.putExtra("contractId", contractDto.getId());
                startActivity(intent);

            }
        });



        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
