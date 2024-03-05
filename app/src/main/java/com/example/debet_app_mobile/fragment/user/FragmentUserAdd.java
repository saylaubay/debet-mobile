package com.example.debet_app_mobile.fragment.user;

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
import com.example.debet_app_mobile.ApiResponse;
import com.example.debet_app_mobile.ApiResponseUser;
import com.example.debet_app_mobile.Company;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.payload.ListCompany;
import com.example.debet_app_mobile.payload.ListRole;
import com.example.debet_app_mobile.payload.Role;
import com.example.debet_app_mobile.payload.RoleDto;
import com.example.debet_app_mobile.payload.UserDto;
import com.example.debet_app_mobile.payload.UserDtos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentUserAdd extends Fragment {

    private View view;

    TextView etFamiliya, etAti, etPhone, etPassword, etUsername;
    Button addBtn;
    Spinner spinner, spinnerRole;

    ArrayAdapter<String> adapterCompany;
    ArrayAdapter<String> adapterRole;

    List<String> dataRole;
    List<String> data;

    List<ListCompany> dataCompany;
    List<ListRole> dataRoles;

    public FragmentUserAdd() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_add_fragment, container, false);

        addBtn = view.findViewById(R.id.addBtn);

        etFamiliya = view.findViewById(R.id.etFamiliya);
        etAti = view.findViewById(R.id.etAti);
        etPhone = view.findViewById(R.id.etPhone);
        etPassword = view.findViewById(R.id.etPassword);
        etUsername = view.findViewById(R.id.etUsername);

          spinner = view.findViewById(R.id.spinner);
          spinnerRole = view.findViewById(R.id.spinnerRole);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        String token = preferences.getString("token", "");

        token = "Bearer " + token;

        String finalToken = token;

        data = new ArrayList<>();
        dataCompany = new ArrayList<>();

        UserDto userDto = new UserDto();
/**
 * TODO
 */
        api.getAllCompany(finalToken).enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                for (TestDto testDto : response.body().getData()) {
                    dataCompany.add(new ListCompany(testDto.getId(),testDto.getName()));
                    data.add(testDto.getName());
                    adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                    adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapterCompany);

                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userDto.setCompanyId(dataCompany.get(i).getId());
//                userDto.setCompanyId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });

        dataRole = new ArrayList<>();
        dataRoles = new ArrayList<>();
        api.getAllRole(finalToken).enqueue(new Callback<RoleDto>() {
            @Override
            public void onResponse(Call<RoleDto> call, Response<RoleDto> response) {
                for (Role datum : response.body().getData()) {
                    dataRole.add(datum.getAuthority());
                 dataRoles.add(new ListRole(datum.getId(), datum.getRoleName().toString()));
                    adapterRole = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataRole);
                    adapterRole.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinnerRole.setAdapter(adapterRole);
                }

            }

            @Override
            public void onFailure(Call<RoleDto> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                userDto.setRoleId(dataRoles.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userDto.setFirstName(etAti.getText().toString());
                userDto.setLastName(etFamiliya.getText().toString());
                userDto.setUsername(etUsername.getText().toString());
                userDto.setPhone(etPhone.getText().toString());
                userDto.setPassword(etPassword.getText().toString());



//                api.addUsr(finalToken, userDto).enqueue(new Callback<ApiResponseUser>() {
//                    @Override
//                    public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
//                        ApiResponseUser body = response.body();
//                        etFamiliya.setText("");
//                        etAti.setText("");
//                        etUsername.setText("");
//                        etPassword.setText("");
//                        etPhone.setText("");
//                        Toast.makeText(getContext(), "User bazag'a qosildi", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<ApiResponseUser> call, Throwable t) {
//                        addBtn.setText(t.getMessage());
//                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });

                api.addUser(finalToken, userDto).enqueue(new Callback<ApiResponseUser>() {
                    @Override
                    public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
                        ApiResponseUser body = response.body();
                        etFamiliya.setText("");
                        etAti.setText("");
                        etUsername.setText("");
                        etPassword.setText("");
                        etPhone.setText("");
                        Toast.makeText(getActivity(), "User bazag'a qosildi", Toast.LENGTH_SHORT).show();
                        startActivity(getActivity().getIntent());
                    }

                    @Override
                    public void onFailure(Call<ApiResponseUser> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

//                api.addUser(finalToken, userDto).enqueue(new Callback<Void>() {
//                    @Override
//                    public void onResponse(Call<Void> call, Response<Void> response) {
//                        Void body = response.body();
//                        etFamiliya.setText("");
//                        etAti.setText("");
//                        etUsername.setText("");
//                        etPassword.setText("");
//                        etPhone.setText("");
//                        Toast.makeText(getContext(), "User bazag'a qosildi", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onFailure(Call<Void> call, Throwable t) {
//                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                });
            }
        });





        return view;
    }



    }

