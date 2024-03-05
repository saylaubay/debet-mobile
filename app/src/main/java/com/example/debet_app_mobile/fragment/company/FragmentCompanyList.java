package com.example.debet_app_mobile.fragment.company;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.debet_app_mobile.ApiResponseCompany;
import com.example.debet_app_mobile.ApiResponseHammesi;
import com.example.debet_app_mobile.CompDto;
import com.example.debet_app_mobile.Company;
import com.example.debet_app_mobile.Company2;
import com.example.debet_app_mobile.Hammesi;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.TestDto2;
import com.example.debet_app_mobile.adapter.CompanyAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCompanyList extends Fragment implements AdapterView.OnItemClickListener {
    private View view;
    public ListView listView;

    public FragmentCompanyList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.company_list_fragment, container, false);

        listView = view.findViewById(R.id.company_list);

        ArrayList<TestDto> companies = new ArrayList<TestDto>();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("authinfo", Context.MODE_PRIVATE);
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

        Toast.makeText(getActivity(),  "Jiberiletin token :  "+token, Toast.LENGTH_LONG).show();

//        api.getAllCompany(token).enqueue(new Callback<ApiResponseCompany>() {
//            @Override
//            public void onResponse(Call<ApiResponseCompany> call, Response<ApiResponseCompany> response) {
//                List<TestDto> compList = response.body().getData();
////                List<TestDto> compList = response.body().getData();
//                companies.addAll(compList);
//                Toast.makeText(getActivity(),  "LIST : " + compList.toString(), Toast.LENGTH_LONG).show();
//
//                if (getActivity()!=null) {
//                    CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);
//                    listView.setAdapter(companyAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseCompany> call, Throwable t) {
//                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });

//        api.hammesi(token).enqueue(new Callback<List<Hammesi>>() {
//            @Override
//            public void onResponse(Call<List<Hammesi>> call, Response<List<Hammesi>> response) {
//                Toast.makeText(getActivity(),  "LIST : ", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<Hammesi>> call, Throwable t) {
//                Toast.makeText(getActivity(),  "ERROR"+t.getMessage().toString(), Toast.LENGTH_LONG).show();
//            }
//        });

//        api.hammesi(token).enqueue(new Callback<Company>() {
//            @Override
//            public void onResponse(Call<Company> call, Response<Company> response) {
//
//                Company body = response.body();
////                List<TestDto> compList = response.body().getData();
//                companies.addAll(body.getData());
//
//                if (getActivity()!=null) {
//                    CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);
//                    listView.setAdapter(companyAdapter);
//                    Toast.makeText(getActivity(),  "JAZILDI : ", Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Company> call, Throwable t) {
//
//            }
//        });

//        api.hammesi(token).enqueue(new Callback<ApiResponseHammesi>() {
//            @Override
//            public void onResponse(Call<ApiResponseHammesi> call, Response<ApiResponseHammesi> response) {
//                Toast.makeText(getActivity(),  "LIST : "+response.body().getData().toString(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponseHammesi> call, Throwable t) {
//                Toast.makeText(getActivity(),  t.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });

//        api.getAllCompany2(token).enqueue(new Callback<Company2>() {
//            @Override
//            public void onResponse(Call<Company2> call, Response<Company2> response) {
//                Toast.makeText(getActivity(),  "GetAllCompany222 : " + response.body().getData().toString(), Toast.LENGTH_LONG).show();
//                Company2 body = response.body();
//                List<TestDto> compList = new ArrayList<TestDto>();
//
//                for (int i = 0; i < response.body().getData().size(); i++) {
//                    if (response.body().getData().get(i).isActive() == 1){
////                        compList.get(i).setActive(true);
//                        compList.add(new TestDto(
//                                response.body().getData().get(i).getId(),
//                                response.body().getData().get(i).getName(),
//                                true
//                        ));
//                    }else {
//                        compList.add(new TestDto(
//                                response.body().getData().get(i).getId(),
//                                response.body().getData().get(i).getName(),
//                                false
//                        ));
//                    }
//                }
//
//                companies.addAll(compList);
////
//                if (getActivity()!=null) {
//                    CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);
//                    listView.setAdapter(companyAdapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Company2> call, Throwable t) {
//
//            }
//        });

//        api.getAllCompany(token).enqueue(new Callback<Company>() {
//            @Override
//            public void onResponse(Call<Company> call, Response<Company> response) {
//                Toast.makeText(getActivity(),  "LIST : " + response.body().getData().toString(), Toast.LENGTH_LONG).show();
//                Company body = response.body();
//                List<TestDto> compList = new ArrayList<TestDto>();
//
//                for (int i = 0; i < response.body().getData().size(); i++) {
//                    if (response.body().getData().get(i).isActive() == 1){
////                        compList.get(i).setActive(true);
//                        compList.add(new TestDto(
//                                response.body().getData().get(i).getId(),
//                                response.body().getData().get(i).getName(),
//                                true
//                        ));
//                    }
//                    compList.add(new TestDto(
//                            response.body().getData().get(i).getId(),
//                            response.body().getData().get(i).getName(),
//                            false
//                    ));
//                }
//
//                companies.addAll(compList);
////
//                if (getActivity()!=null) {
//                    CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);
//                    listView.setAdapter(companyAdapter);
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Company> call, Throwable t) {
//
//            }
//        });

        api.getAllCompany(token).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                Toast.makeText(getActivity(),  "LIST : ", Toast.LENGTH_LONG).show();
//                Toast.makeText(getActivity(),  "LIST : " + response.body().getData().toString(), Toast.LENGTH_LONG).show();

                Company body = response.body();
//                List<TestDto> compList = response.body().getData();
//                for (int i = 0; i < response.body().getData().size(); i++) {
//                    if (response.body().getData().get(i).isActive() == 1){
//
//                    }
//                }

                companies.addAll(body.getData());

                if (getActivity()!=null) {
                    CompanyAdapter companyAdapter = new CompanyAdapter(getActivity(), companies);
                    listView.setAdapter(companyAdapter);
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
//                Log.println(1,"",t.getMessage().toString());
                Toast.makeText(getActivity(), "qate", Toast.LENGTH_LONG).show();
            }
        });

        String finalToken = token;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TestDto company = (TestDto) adapterView.getAdapter().getItem(i);
                ImageView icon = view.findViewById(R.id.cIcon);
                if (company.isActive()){
                    company.setActive(false);
                    icon.setImageResource(R.drawable.ic_unlock);
                    Toast.makeText(getActivity(), "" + company.getName() + " bloklandi!!!", Toast.LENGTH_SHORT).show();

                }else {
                    company.setActive(true);
                    icon.setImageResource(R.drawable.ic_lock);
                    Toast.makeText(getActivity(), "" + company.getName() + " bloktan ashildi", Toast.LENGTH_SHORT).show();

                }

                api.updateCompany(finalToken,new CompDto(
                        company.getId(),
                        company.getName(),
                        company.isActive()
                ), company.getId()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        startActivity(getActivity().getIntent());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }
}
