package com.example.debet_app_mobile.fragment.client;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiResponseClient;
import com.example.debet_app_mobile.ApiResponseClient2;
import com.example.debet_app_mobile.ApiResponseUser;
import com.example.debet_app_mobile.ApiResponseUserAdd;
import com.example.debet_app_mobile.BuyActivity;
import com.example.debet_app_mobile.Company;
import com.example.debet_app_mobile.ExploreActivity;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.adapter.ClientAdapter;
import com.example.debet_app_mobile.adapter.ClientAdapter2;
import com.example.debet_app_mobile.adapter.ContractAdapter2;
import com.example.debet_app_mobile.payload.ClientDto;
import com.example.debet_app_mobile.payload.ClientDtos;
import com.example.debet_app_mobile.payload.ListClient;
import com.example.debet_app_mobile.payload.ListCompany;
import com.example.debet_app_mobile.payload.ListRole;
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

public class FragmentClientList extends Fragment {

    private View view;
    public ListView listView;

    ArrayAdapter<String> adapterCompany;
    ArrayAdapter<String> adapterUser;


    List<ListCompany> dataCompany;

    ArrayAdapter<String> adapterClient;
    List<ListClient> dataClient;
    List<ListUser> dataUser;


    List<String> data;
    List<String> dataC;
    List<String> dataU;

    Spinner spinner_list_company, spinner_list_user;

    public FragmentClientList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.client_list_fragment, container, false);

        listView = view.findViewById(R.id.client_list);
        spinner_list_company = view.findViewById(R.id.spinner_list_company);
        spinner_list_user = view.findViewById(R.id.spinner_list_user);


        ArrayList<ClientDtos> clients = new ArrayList<ClientDtos>();

        SharedPreferences preferences = this.getActivity().getSharedPreferences("authinfo", Context.MODE_PRIVATE);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);
        String token = preferences.getString("token", "");
        String role = preferences.getString("role", "");
        String dbUsername = preferences.getString("username", "");
        token = "Bearer " + token;

        data = new ArrayList<String>();
        dataC = new ArrayList<String>();
        dataU = new ArrayList<String>();
        dataCompany = new ArrayList<ListCompany>();
        dataClient = new ArrayList<ListClient>();
        dataUser = new ArrayList<>();



        if (role.equals("SUPER_ADMIN")) {

            spinner_list_company.setVisibility(View.VISIBLE);
            spinner_list_user.setVisibility(View.VISIBLE);

            String n = "0";
//            long nol = Long.parseLong(n);
            int nol = Integer.parseInt(n);
            dataCompany.add(new ListCompany(nol, "Kompaniya"));
            dataC.add("Kompaniya");

//            String nU = "0";
//            long nolU = Long.parseLong(nU);
//            dataUser.add(new ListUser(nolU, "Userler",""));
//            dataC.add("Paydalaniwshilar");

//            Toast.makeText(getActivity(), "Companylar alindi = \n", Toast.LENGTH_SHORT).show();

            api.getAllCompany(token).enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    List<TestDto> companyList = response.body().getData();

//                    List<String> list = new ArrayList<>();
//                    for (int i = 0; i < companyList.size(); i++) {
//                        list.add(i + " - "+companyList.get(i).getName()+"\n");
//                    }

//                    Toast.makeText(getActivity(), "Companylar alindi = \n", Toast.LENGTH_LONG).show();

                    for (TestDto testDto : response.body().getData()) {

                        dataCompany.add(new ListCompany(testDto.getId(), testDto.getName()));
                        dataC.add(testDto.getName());
                        adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataC);
                        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner_list_company.setAdapter(adapterCompany);
//                        Toast.makeText(ExploreActivity.this, "Login paroldi kiritin'", Toast.LENGTH_SHORT).show();


                    }
//                    Toast.makeText(getActivity(), "Companylar alindi = \n"+dataCompany.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Company> call, Throwable t) {

                }
            });

            dataUser = new ArrayList<ListUser>();
//            adapterUser = new ArrayAdapter<String>();

            String finalToken = token;
            spinner_list_company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                    clientDto.setCompanyId(dataCompany.get(i).getId());
                    if (i == 0) {
//                        Toast.makeText(getActivity(), "ISHKE kirildi", Toast.LENGTH_SHORT).show();
                        spinner_list_user.setAdapter(null);
                        dataUser.clear();

                        String nU = "0";
//                        long nolU = Long.parseLong(nU);
                        int nolU = Integer.parseInt(nU);
                        dataUser.add(new ListUser(nolU, "Userler",""));
                        dataU.add("Paydalaniwshilar");

                        api.getAllUser(finalToken).enqueue(new Callback<ApiResponseUser>() {
                            @Override
                            public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
                                List<String> list = new ArrayList<>();
                                for (int i1 = 0; i1 < response.body().getData().size(); i1++) {
                                    list.add(i1 +"-"+response.body().getData().get(i1).getUsername()+"\n");
                                }
                                Toast.makeText(getActivity(), list.toString(), Toast.LENGTH_LONG).show();

                                List<UserDtos> data = response.body().getData();
                                for (UserDtos datum : data) {
                                    ListUser listUser = new ListUser();
                                    listUser.setId(datum.getId());
                                    listUser.setFirstName(datum.getFirstName());
                                    listUser.setPhone(datum.getPhone());
                                    dataUser.add(listUser);
                                    String str = datum.getFirstName() + " " + datum.getPhone();
                                    dataU.add(str);

                                    adapterUser = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataU);
                                    adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_list_user.setAdapter(adapterUser);
                                }


                            }

                            @Override
                            public void onFailure(Call<ApiResponseUser> call, Throwable t) {

                            }
                        });

                        dataClient.clear();
                        data.clear();
                        listView.setAdapter(null);
                        clients.clear();
                        api.getAllClient(finalToken).enqueue(new Callback<ApiResponseClient>() {
                            @Override
                            public void onResponse(Call<ApiResponseClient> call, Response<ApiResponseClient> response) {

                                List<ClientDtos> list = response.body().getData();
                                clients.addAll(list);

                                if (getActivity()!=null){
                                    ClientAdapter2 clientAdapter = new ClientAdapter2(getActivity(), clients);
                                    listView.setAdapter(clientAdapter);
                                }
                            }

                            @Override
                            public void onFailure(Call<ApiResponseClient> call, Throwable t) {

                            }
                        });




                    } else {
//                        Toast.makeText(getActivity(), dataUser.get(i).getPhone(), Toast.LENGTH_SHORT).show();
                        int id = dataCompany.get(i).getId();
                        api.getAllUserByCompanyId(finalToken, id).enqueue(new Callback<ApiResponseUser>() {
                            @Override
                            public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
                                dataU.clear();
                                dataUser.clear();
                                spinner_list_user.setAdapter(null);
                                for (UserDtos datum : response.body().getData()) {
                                    dataUser.add(new ListUser(datum.getId(), datum.getFirstName(), datum.getPhone()));
                                    dataU.add(datum.getFirstName() + " " + datum.getPhone());

                                    adapterUser = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataU);
                                    adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinner_list_user.setAdapter(adapterUser);
                                }

                            }

                            @Override
                            public void onFailure(Call<ApiResponseUser> call, Throwable t) {

                            }
                        });

                        spinner_list_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                int idUser = dataUser.get(i).getId();

                                dataClient.clear();
                                data.clear();
                                listView.setAdapter(null);
                                clients.clear();

                                api.getClientsByUserId(finalToken, idUser).enqueue(new Callback<ApiResponseClient2>() {
                                    @Override
                                    public void onResponse(Call<ApiResponseClient2> call, Response<ApiResponseClient2> response) {

                                        List<ClientDtos> list = response.body().getData();
                                        clients.addAll(list);

                                        ClientAdapter2 clientAdapter = new ClientAdapter2(getActivity(), clients);
                                        listView.setAdapter(clientAdapter);

                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponseClient2> call, Throwable t) {
                                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            spinner_list_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int idUser = dataUser.get(i).getId();

                    dataClient.clear();
                    data.clear();
                    listView.setAdapter(null);
                    clients.clear();

                    api.getClientsByUserId(finalToken, idUser).enqueue(new Callback<ApiResponseClient2>() {
                        @Override
                        public void onResponse(Call<ApiResponseClient2> call, Response<ApiResponseClient2> response) {

                            List<ClientDtos> list = response.body().getData();
                            clients.addAll(list);

                            ClientAdapter2 clientAdapter = new ClientAdapter2(getActivity(), clients);
                            listView.setAdapter(clientAdapter);


                        }

                        @Override
                        public void onFailure(Call<ApiResponseClient2> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }


        if (role.equals("ADMIN")){
            spinner_list_user.setVisibility(View.VISIBLE);

            UserDtos userDtos = new UserDtos();

            api.getUser(token).enqueue(new Callback<ApiResponseUserAdd>() {
                @Override
                public void onResponse(Call<ApiResponseUserAdd> call, Response<ApiResponseUserAdd> response) {
                    UserDtos data = response.body().getData();
                    userDtos.setCompany(data.getCompany());
                }

                @Override
                public void onFailure(Call<ApiResponseUserAdd> call, Throwable t) {

                }
            });

//            data = new ArrayList<String>();
//            dataC = new ArrayList<String>();
//            dataU = new ArrayList<String>();
//            dataCompany = new ArrayList<ListCompany>();
//            dataClient = new ArrayList<ListClient>();

            api.getAllByMyCompany(token).enqueue(new Callback<ApiResponseUser>() {
                @Override
                public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
                    dataU.clear();
                    dataUser.clear();
                    spinner_list_user.setAdapter(null);
                    for (UserDtos datum : response.body().getData()) {
                        dataUser.add(new ListUser(datum.getId(), datum.getFirstName(), datum.getPhone()));
                        dataU.add(datum.getFirstName() + " " + datum.getPhone());

                        adapterUser = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataU);
                        adapterUser.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_list_user.setAdapter(adapterUser);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseUser> call, Throwable t) {

                }
            });

            String finalToken1 = token;
            spinner_list_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int idUser = dataUser.get(i).getId();

                    dataClient.clear();
                    data.clear();
                    listView.setAdapter(null);
                    clients.clear();

                    api.getClientsByUserId(finalToken1, idUser).enqueue(new Callback<ApiResponseClient2>() {
                        @Override
                        public void onResponse(Call<ApiResponseClient2> call, Response<ApiResponseClient2> response) {

                            List<ClientDtos> list = response.body().getData();
                            clients.addAll(list);

                            ClientAdapter2 clientAdapter = new ClientAdapter2(getActivity(), clients);
                            listView.setAdapter(clientAdapter);


                        }

                        @Override
                        public void onFailure(Call<ApiResponseClient2> call, Throwable t) {
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }


        if (role.equals("USER")) {
            clients.clear();
            listView.setAdapter(null);
            api.getMyClients(token).enqueue(new Callback<ApiResponseClient2>() {
                @Override
                public void onResponse(Call<ApiResponseClient2> call, Response<ApiResponseClient2> response) {
                    List<ClientDtos> list = response.body().getData();
                    clients.addAll(list);

                    ClientAdapter2 clientAdapter = new ClientAdapter2(getActivity(), clients);
                    listView.setAdapter(clientAdapter);

                }

                @Override
                public void onFailure(Call<ApiResponseClient2> call, Throwable t) {

                }
            });
        }


        return view;
    }


}
