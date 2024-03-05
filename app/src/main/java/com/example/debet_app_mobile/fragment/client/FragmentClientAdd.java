package com.example.debet_app_mobile.fragment.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiRes;
import com.example.debet_app_mobile.ApiResponse;
import com.example.debet_app_mobile.Company;
import com.example.debet_app_mobile.CompanyDto;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.TestDto2;
import com.example.debet_app_mobile.payload.ClientDto;
import com.example.debet_app_mobile.payload.ClientDtos;
import com.example.debet_app_mobile.payload.ListCompany;
import com.example.debet_app_mobile.payload.ListRole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentClientAdd extends Fragment {

    private View view;

    public TextView etFamiliya, etAti, etPhone;
    Button addBtn;
    Spinner spinner;

    ArrayAdapter<String> adapterCompany;


    List<ListCompany> dataCompany;
    List<ListRole> dataRoles;

    List<String> data;


    public FragmentClientAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.client_add_fragment, container, false);

        etFamiliya = view.findViewById(R.id.etFamiliya);
        etAti = view.findViewById(R.id.etAti);
        etPhone = view.findViewById(R.id.etPhone);
        addBtn = view.findViewById(R.id.addBtn);
        spinner = view.findViewById(R.id.spinner);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        String token = preferences.getString("token","");
        String role = preferences.getString("role", "");


        token = "Bearer " + token;

        String finalToken = token;

        data = new ArrayList<>();
        dataCompany = new ArrayList<>();

        ClientDto clientDto = new ClientDto();

        if (role.equals("SUPER_ADMIN")){
            spinner.setVisibility(View.VISIBLE);
        }else {
            spinner.setVisibility(View.GONE);
        }

        if (role.equals("SUPER_ADMIN")) {
            api.getAllCompany(finalToken).enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    List<String> list = new ArrayList<>();
                List<TestDto> data = response.body().getData();
//                    for (TestDto2 testDto2 : response.body().getData()) {
//
//                        dataCompany.add(new ListCompany(testDto2.getId(), testDto2.getName()));
//                        data.add(testDto2.getName());
//
//                        adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
//                        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//                        spinner.setAdapter(adapterCompany);
//
//                    }

                    for (TestDto testDto : response.body().getData()) {

                        dataCompany.add(new ListCompany(testDto.getId(), testDto.getName()));
                        data.add(testDto);

//                        adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner.setAdapter(adapterCompany);

                        list.add(testDto.getName());

                        adapterCompany = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,list);

                    }


                }

                @Override
                public void onFailure(Call<Company> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        if (role.equals("SUPER_ADMIN")) {
//            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    CompanyDto companyDto = new CompanyDto();
//
//
//
//                    clientDto.setCompanyId(dataCompany.get(i).getId());
//
//                    ListCompany listCompany = dataCompany.get(i);
//                    clientDto.setCompanyId(dataCompany.get(i).getId().);
////                    Toast.makeText(getActivity(), dataCompany.get(i).getId().toString(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
////                    System.out.println("ffff");
////                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String familiya = etFamiliya.getText().toString();
                String ati = etAti.getText().toString();
                String phone = etPhone.getText().toString();

//                ClientDto clientDto = new ClientDto(familiya, ati, phone);
                clientDto.setFirstName(ati);
                clientDto.setLastName(familiya);
                clientDto.setPhone(phone);

                api.addClient(finalToken, clientDto).enqueue(new Callback<ApiRes>() {
                    @Override
                    public void onResponse(Call<ApiRes> call, Response<ApiRes> response) {
                        int code = response.code();
                        if (response.isSuccessful() == false || code==409){
//                            Toast.makeText(getActivity(), "Telefon nomer bazada bar!!!", Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(), response.code() + "-", Toast.LENGTH_LONG).show();
                        }else{
                            etFamiliya.setText("");
                            etAti.setText("");
                            etPhone.setText("");
                            Toast.makeText(getActivity(), "Klient qosildi!", Toast.LENGTH_LONG).show();
                            startActivity(getActivity().getIntent());
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiRes> call, Throwable t) {

                    }
                });

            }
        });


        return view;
    }



}
