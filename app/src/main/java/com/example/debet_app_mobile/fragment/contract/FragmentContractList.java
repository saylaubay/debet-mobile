package com.example.debet_app_mobile.fragment.contract;

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
import com.example.debet_app_mobile.ApiResponseContract;
import com.example.debet_app_mobile.ApiResponseUser;
import com.example.debet_app_mobile.ApiResponseUserAdd;
import com.example.debet_app_mobile.Company;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TestDto;
import com.example.debet_app_mobile.adapter.ClientAdapter2;
import com.example.debet_app_mobile.adapter.ContractAdapter;
import com.example.debet_app_mobile.adapter.ContractAdapter2;
import com.example.debet_app_mobile.adapter.UserAdapter;
import com.example.debet_app_mobile.payload.ApiResponseContractResp;
import com.example.debet_app_mobile.payload.BuyDtos;
import com.example.debet_app_mobile.payload.ClientDtos;
import com.example.debet_app_mobile.payload.ContractDto;
import com.example.debet_app_mobile.payload.ListClient;
import com.example.debet_app_mobile.payload.ListCompany;
import com.example.debet_app_mobile.payload.ListContract;
import com.example.debet_app_mobile.payload.ListUser;
import com.example.debet_app_mobile.payload.UserDtos;
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

public class FragmentContractList extends Fragment {

    private View view;
    public ListView listView;

    Spinner spinner_list_company, spinner_list_user;

    List<ListCompany> dataCompany;



    List<String> data;
    List<String> dataC;
    List<String> dataU;


    ArrayAdapter<String> adapterCompany;
    ArrayAdapter<String> adapterUser;
    ArrayAdapter<String> adapterContract;

    ArrayAdapter<String> adapterClient;
//    List<ListClient> dataClient;
    List<ListUser> dataUser;
    List<ListContract> dataContract;


    public FragmentContractList() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contract_list_fragment, container, false);

        listView = view.findViewById(R.id.no_payed_list);
        spinner_list_company= view.findViewById(R.id.spinner_company);
        spinner_list_user= view.findViewById(R.id.spinner_user);

        ArrayList<ContractDto> contracts = new ArrayList<ContractDto>();
        ArrayList<UserDtos> users = new ArrayList<UserDtos>();

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


        data = new ArrayList<String>();
        dataC = new ArrayList<String>();
        dataU = new ArrayList<String>();
        dataCompany = new ArrayList<ListCompany>();
        dataContract = new ArrayList<ListContract>();
        dataUser = new ArrayList<ListUser>();
//        dataClient = new ArrayList<>();



        if (role.equals("SUPER_ADMIN")) {

            spinner_list_user.setVisibility(View.VISIBLE);
            spinner_list_company.setVisibility(View.VISIBLE);

//            Toast.makeText(getContext(), "SUPER ADMIN ROLE", Toast.LENGTH_SHORT).show();

            String n = "0";
//            long nol = Long.parseLong(n);
            int nol = Integer.parseInt(n);
            dataCompany.add(new ListCompany(nol, "Kompaniya"));
            dataC.add("Kompaniya");


            api.getAllCompany(token).enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    List<TestDto> companyList = response.body().getData();
                    for (TestDto testDto : response.body().getData()) {

                        dataCompany.add(new ListCompany(testDto.getId(), testDto.getName()));
                        dataC.add(testDto.getName());
                        adapterCompany = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataC);
                        adapterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        spinner_list_company.setAdapter(adapterCompany);

                    }
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
                        spinner_list_user.setAdapter(null);
                        dataUser.clear();
                        dataU.clear();
                        listView.setAdapter(null);
                        api.getAllUser(finalToken).enqueue(new Callback<ApiResponseUser>() {
                            @Override
                            public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {
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

                        dataContract.clear();
                        data.clear();
                        listView.setAdapter(null);

                        api.getAllContract(finalToken).enqueue(new Callback<ApiResponseContractResp>() {
                            @Override
                            public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {

                                List<ContractDto> data = response.body().getData();
                                contracts.addAll(data);

                                if (getActivity()!=null) {
                                    ContractAdapter adapter = new ContractAdapter(getActivity(), contracts);
                                    listView.setAdapter(adapter);
                                }

                            }

                            @Override
                            public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {

                            }
                        });

                    } else {
                        int id = dataCompany.get(i).getId();

                        dataU.clear();
                        dataUser.clear();
                        spinner_list_user.setAdapter(null);

                        api.getAllUserByCompanyId(finalToken, id).enqueue(new Callback<ApiResponseUser>() {
                            @Override
                            public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {

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

                                dataContract.clear();
                                data.clear();
                                listView.setAdapter(null);
                                contracts.clear();

                                api.getAllContractByUserId(finalToken, idUser).enqueue(new Callback<ApiResponseContractResp>() {
                                    @Override
                                    public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {

                                        List<ContractDto> data = response.body().getData();
                                        contracts.addAll(data);

                                        if (getActivity()!=null) {
                                            ContractAdapter adapter = new ContractAdapter(getActivity(), contracts);
                                            listView.setAdapter(adapter);
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {

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

            dataU.clear();
            dataUser.clear();
            spinner_list_user.setAdapter(null);


            api.getAllByMyCompany(token).enqueue(new Callback<ApiResponseUser>() {
                @Override
                public void onResponse(Call<ApiResponseUser> call, Response<ApiResponseUser> response) {

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

                    dataContract.clear();
                    data.clear();
                    listView.setAdapter(null);

                    api.getAllContractByUserId(finalToken1, idUser).enqueue(new Callback<ApiResponseContractResp>() {
                        @Override
                        public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {

                            List<ContractDto> data = response.body().getData();
                            contracts.addAll(data);

                            if (getActivity()!=null) {
                                ContractAdapter adapter = new ContractAdapter(getActivity(), contracts);
                                listView.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {

                        }
                    });

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


        }






        if (role.equals("USER")){

            api.getMyContract(token).enqueue(new Callback<ApiResponseContractResp>() {
                @Override
                public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {
                    List<ContractDto> data = response.body().getData();
                    contracts.addAll(data);

                    if (getActivity()!=null) {
                        ContractAdapter2 adapter = new ContractAdapter2(getActivity(), contracts);
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {

                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    ContractDto data = (ContractDto)adapterView.getAdapter().getItem(i);

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
                            Toast.makeText(getActivity(), data.getClient().getPhone(), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getClient().getPhone()));
                            startActivity(intent);
                        }
                    });

                    alert.create().show();
                }
            });

        }

        return view;
    }
}
