package com.example.debet_app_mobile.fragment.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiResponse;
import com.example.debet_app_mobile.ApiResponseUser;
import com.example.debet_app_mobile.Company;
import com.example.debet_app_mobile.Company2;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.TestDto2;
import com.example.debet_app_mobile.adapter.CompanyAdapter;
import com.example.debet_app_mobile.adapter.UserAdapter;
import com.example.debet_app_mobile.payload.ListClient;
import com.example.debet_app_mobile.payload.ListCompany;
import com.example.debet_app_mobile.payload.ListUser;
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

public class FragmentUserList extends Fragment {

    private View view;
    public ListView listView;
    Spinner spinCompany;

    List<String> data;

    List<ListCompany> dataCompany;
    List<ListUser> dataUser;

    ArrayAdapter<String> adapterCompany;
    ArrayAdapter<String> adapterUser;


    public FragmentUserList() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_list_fragment, container, false);

        listView = view.findViewById(R.id.user_list);
        spinCompany = view.findViewById(R.id.spinCompany);


        ArrayList<UserDtos> users = new ArrayList<UserDtos>();
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
        String role = preferences.getString("role","");
        token = "Bearer " + token;


        data = new ArrayList<>();
        dataCompany = new ArrayList<>();
        dataUser = new ArrayList<>();

        final long[] id = {0};

        String finalToken = token;

        if (role.equals("SUPER_ADMIN")) {
            data.clear();
            dataCompany.clear();
            spinCompany.setAdapter(null);

            String n = "0";
//            long nol = Long.parseLong(n);
            int nol = Integer.parseInt(n);
            dataCompany.add(new ListCompany(nol, "Kompaniyalar"));
            data.add("Kompaniyalar");

            /**
             * TODO
             */
            Toast.makeText(getActivity(), "USER listke kirildi", Toast.LENGTH_LONG).show();

//            api.getAllCompany2(finalToken).enqueue(new Callback<Company2>() {
//                @Override
//                public void onResponse(Call<Company2> call, Response<Company2> response) {
//                    Toast.makeText(getActivity(), "KOMPANIAYGA kirildi", Toast.LENGTH_LONG).show();
//
//                    List<String> nameList = new ArrayList<>();
//                    for (TestDto2 datum : response.body().getData()) {
//                        nameList.add(datum.getName()+"\n");
//                    }
//
//                    Toast.makeText(getActivity(), nameList.toString(), Toast.LENGTH_SHORT).show();
//
////                    List<String> list = new ArrayList<>();
//
////                    for (TestDto2 testDto : response.body().getData()) {
//////                        list.add(testDto.getName()+"\n");
////                        dataCompany.add(new ListCompany(testDto.getId(),testDto.getName()));
////                        data.add(testDto.getName());
////                        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////                        adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
////
////                        spinCompany.setAdapter(adapterCompany);
////
////                    }
//                }
//
//                @Override
//                public void onFailure(Call<Company2> call, Throwable t) {
//                    Toast.makeText(getActivity(), "Company qatelik", Toast.LENGTH_SHORT).show();
//
//                }
//            });


            api.getAllCompany(finalToken).enqueue(new Callback<Company>() {

                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    Toast.makeText(getActivity(), "getAllCompany", Toast.LENGTH_SHORT).show();
                    List<String> nameList = new ArrayList<>();
//                    response.body().getData()
                    for (TestDto datum : response.body().getData()) {
                        nameList.add(datum.getName()+"\n");
                    }

                    Toast.makeText(getActivity(), nameList.toString(), Toast.LENGTH_SHORT).show();

//                    List<String> list = new ArrayList<>();

                    for (TestDto testDto : response.body().getData()) {
//                        list.add(testDto.getName()+"\n");
                        dataCompany.add(new ListCompany(testDto.getId(),testDto.getName()));
                        data.add(testDto.getName());
                        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);

                        spinCompany.setAdapter(adapterCompany);

                    }
//                    Toast.makeText(getActivity(), "COMPANYLIST:\n"+list.toString(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<Company> call, Throwable t) {
                    Toast.makeText(getActivity(), "getAllCompany ERROR", Toast.LENGTH_SHORT).show();
                }
            });

            Toast.makeText(getActivity(), "OTTI", Toast.LENGTH_SHORT).show();




            String finalToken1 = token;
//            spinCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                    userDto.setCompanyId(dataCompany.get(i).getId());
//                    int idCompany = dataCompany.get(i).getId();
//
//                    if (i==0){
//                        Toast.makeText(getActivity(), "SPINCOMPANY : ", Toast.LENGTH_SHORT).show();
//                        users.clear();
//                        listView.setAdapter(null);
//                        api.getAllUser(finalToken1).enqueue(new Callback<ApiResponseUser>() {
//                            @Override
//                            public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
//                                List<UserDtos> data = response.body().getData();
//                                users.addAll(data);
//
//                                if (getActivity()!=null) {
//                                    UserAdapter userAdapter = new UserAdapter(getActivity(), users);
//                                    listView.setAdapter(userAdapter);
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ApiResponseUser> call, Throwable t) {
//
//                            }
//                        });
//
//
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                UserDtos userDtos = (UserDtos) adapterView.getAdapter().getItem(i);
//                                ImageView icon = view.findViewById(R.id.uIcon);
//
//                                if (userDtos.isActive()) {
////                                    if (userDtos.isAccountNonExpired()) {
////                                        userDtos.setAccountNonExpired(true);
//                                        userDtos.setActive(true);
//                                        icon.setImageResource(R.drawable.ic_lock);//ic_unlock
//                                        Toast.makeText(getActivity(), "" + userDtos.getFirstName() + " bloktan ashildi", Toast.LENGTH_SHORT).show();
//
////                                    } else {
////                                        userDtos.setAccountNonExpired(true);
////                                        icon.setImageResource(R.drawable.ic_lock);
////                                        Toast.makeText(getActivity(), "" + userDtos.getFirstName() + " bloktan ashildi", Toast.LENGTH_SHORT).show();
//
////                                    }
//
//                                }else {
////                                    userDtos.setAccountNonExpired(true);
//                                    userDtos.setActive(false);
//                                    icon.setImageResource(R.drawable.ic_unlock);//ic_lock
//                                    Toast.makeText(getActivity(), "" + userDtos.getFirstName() + " bloklandi!!!", Toast.LENGTH_SHORT).show();
//
//                                }
//
//
//                                //for Schedule method
//                                api.blockAllUser(finalToken1, userDtos, userDtos.getId()).enqueue(new Callback<ApiResponse>() {
//                                    @Override
//                                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                                        ApiResponse body = response.body();
//
////                                        Toast.makeText(getActivity(), "OK", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<ApiResponse> call, Throwable t) {
//                                        Toast.makeText(getActivity(), "NO", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                                api.updateUser(finalToken, userDtos, userDtos.getId()).enqueue(new Callback<Void>() {
//                                    @Override
//                                    public void onResponse(Call<Void> call, Response<Void> response) {
////                                        Void body = response.body();
//                                        startActivity(getActivity().getIntent());
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<Void> call, Throwable t) {
//                                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//
//                            }
//                        });
//                    }else {
//                        users.clear();
//                        listView.setAdapter(null);
//                        api.getAllUserByCompanyId(finalToken1, idCompany).enqueue(new Callback<ApiResponseUser>() {
//                            @Override
//                            public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
//                                List<UserDtos> data = response.body().getData();
//                                users.addAll(data);
//
//                                if (getActivity()!=null) {
//                                    UserAdapter userAdapter = new UserAdapter(getActivity(), users);
//                                    listView.setAdapter(userAdapter);
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ApiResponseUser> call, Throwable t) {
//                                Toast.makeText(getActivity(), "Internet qatelik", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
////                    Toast.makeText(getActivity(), dataCompany.get(i).getId().toString(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
////                    System.out.println("ffff");
//
//                }
//            });




        }

        return view;
    }



}
