package com.example.debet_app_mobile.fragment.contract;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiResponseClient;
import com.example.debet_app_mobile.ApiResponseClient2;
import com.example.debet_app_mobile.ApiResponseContract;
import com.example.debet_app_mobile.ApiResponseUserAdd;
import com.example.debet_app_mobile.BuyActivity;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.payload.BuyDto;
import com.example.debet_app_mobile.payload.ClientDto;
import com.example.debet_app_mobile.payload.ClientDtos;
import com.example.debet_app_mobile.payload.ListClient;
import com.example.debet_app_mobile.payload.UserDtos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentContractAdd extends Fragment {

    private View view;
    TextView product, summa, percent, part, tolengen,sane;
    Spinner spinner;
    Button btn;
    CheckBox aldingi;
    LinearLayout blok;

    List<String> data;

    ArrayAdapter<String> adapterClient;


    List<ListClient> dataClient;


    public FragmentContractAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.contract_add_fragment, container, false);

        product = view.findViewById(R.id.product);
        summa = view.findViewById(R.id.summa);
        percent = view.findViewById(R.id.percent);
        part = view.findViewById(R.id.part);

        spinner = view.findViewById(R.id.spinner);
        btn = view.findViewById(R.id.addBtn);
        aldingi = view.findViewById(R.id.aldingi);
        blok = view.findViewById(R.id.blok);
        tolengen = view.findViewById(R.id.tolengen);
        sane = view.findViewById(R.id.sane);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Api api = retrofit.create(Api.class);

        SharedPreferences preferences = this.getContext().getSharedPreferences("com.example.debet_app_mobile", Context.MODE_PRIVATE);

        String token = preferences.getString("token", "");
        String role = preferences.getString("role", "");
        String dbusername = preferences.getString("username", "");

        token = "Bearer " + token;

        String finalToken = token;

        data = new ArrayList<>();
        dataClient = new ArrayList<>();

        ArrayList<ClientDto> clients = new ArrayList<ClientDto>();

        BuyDto buyDto = new BuyDto();

//        System.out.println(role);

        aldingi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                aldingi.setChecked(!aldingi.isChecked());
                if (aldingi.isChecked()){
                    blok.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Cheked", Toast.LENGTH_SHORT).show();
                }else {
                    blok.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No Cheked", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i1=i1+1;
                        sane.setText(i2+"/"+i1+"/"+i);
//                        startContract = danContract.getText().toString();

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        if (role.equals("USER") || role.equals("ADMIN")) {

//            sane.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Calendar calendar = Calendar.getInstance();
//                    int year = calendar.get(Calendar.YEAR);
//                    int month = calendar.get(Calendar.MONTH);
//                    int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                    DatePickerDialog datePickerDialog = new DatePickerDialog(
//                            getContext(), new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                            i1=i1+1;
//                            sane.setText(i2+"/"+i1+"/"+i);
////                            startContract = danContract.getText().toString();
//
//                        }
//                    },year,month,day);
//                }
//            });

            api.getMyClients(finalToken).enqueue(new Callback<ApiResponseClient2>() {
                @Override
                public void onResponse(Call<ApiResponseClient2> call, Response<ApiResponseClient2> response) {
//                    System.out.println(123);
                    for (ClientDtos datum : response.body().getData()) {
                        dataClient.add(new ListClient(datum.getId(), datum.getFirstName(), datum.getPhone()));
                        data.add(datum.getFirstName() + " " + datum.getPhone());

                        adapterClient = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                        adapterClient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapterClient);
                    }


//                ClientAdapter clientAdapter = new ClientAdapter(BuyActivity.this, clients);
//                listView.setAdapter(clientAdapter);
                }

                @Override
                public void onFailure(Call<ApiResponseClient2> call, Throwable t) {

                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int id = dataClient.get(i).getId();
                    buyDto.setClientId(id);
//                    Toast.makeText(getActivity(), dataClient.get(i).getFirstName() + " admin ushin", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

//            System.out.println(dbusername);
            api.getUserByUsername(finalToken, dbusername).enqueue(new Callback<ApiResponseUserAdd>() {
                @Override
                public void onResponse(Call<ApiResponseUserAdd> call, Response<ApiResponseUserAdd> response) {
                    UserDtos data = response.body().getData();
                    buyDto.setWorkerId(data.getId());
//                    Toast.makeText(getActivity(), "isledi", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ApiResponseUserAdd> call, Throwable t) {

                }
            });

        }else if (role.equals("SUPER_ADMIN")){
            api.getAllClient(finalToken).enqueue(new Callback<ApiResponseClient>() {
                @Override
                public void onResponse(Call<ApiResponseClient> call, Response<ApiResponseClient> response) {
                    for (ClientDtos datum : response.body().getData()) {
                        dataClient.add(new ListClient(datum.getId(), datum.getFirstName(), datum.getPhone()));
                        data.add(datum.getFirstName() + " " + datum.getPhone());

                        adapterClient = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
                        adapterClient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapterClient);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponseClient> call, Throwable t) {

                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int id = dataClient.get(i).getId();
                    buyDto.setClientId(id);
//                    Toast.makeText(getActivity(), dataClient.get(i).getFirstName() + " basildi", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sProduct = product.getText().toString();
                String s = summa.getText().toString();
                double sSumma = Double.parseDouble(s);
                String s1 = percent.getText().toString();
                int sPercent = Integer.parseInt(s1);
                String s2 = part.getText().toString();
                int sPart = Integer.parseInt(s2);


                if (sProduct.equals("") || s.equals("") || s1.equals("") || s2.equals("")) {
                    Toast.makeText(getContext(), "Mag'liwmatlardi toliq toltirin'!!!", Toast.LENGTH_SHORT).show();
                } else {

                    if (aldingi.isChecked()){

                        buyDto.setProductName(sProduct);
                        buyDto.setPrice(sSumma);
                        buyDto.setPercent(sPercent);
                        buyDto.setPart(sPart);
//                        buyDto.setOld(oldPay);
//                        buyDto.setOldSane(sane.getText().toString());
                        String oldDate = sane.getText().toString();

                        String s3 = tolengen.getText().toString();
                        int oldPay = Integer.parseInt(s3);


                        api.addContractOld(finalToken, buyDto, oldDate, oldPay).enqueue(new Callback<ApiResponseContract>() {
                            @Override
                            public void onResponse(Call<ApiResponseContract> call, Response<ApiResponseContract> response) {
                                product.setText("");
                                summa.setText("");
                                percent.setText("");
                                part.setText("");
                                Toast.makeText(getActivity(), "Kontrakt qosildi!", Toast.LENGTH_SHORT).show();
                                startActivity(getActivity().getIntent());
                            }

                            @Override
                            public void onFailure(Call<ApiResponseContract> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else {
                        buyDto.setProductName(sProduct);
                        buyDto.setPrice(sSumma);
                        buyDto.setPercent(sPercent);
                        buyDto.setPart(sPart);

                        api.addContract(finalToken, buyDto).enqueue(new Callback<ApiResponseContract>() {
                            @Override
                            public void onResponse(Call<ApiResponseContract> call, Response<ApiResponseContract> response) {
                                ApiResponseContract body = response.body();
                                product.setText("");
                                summa.setText("");
                                percent.setText("");
                                part.setText("");
                                Toast.makeText(getActivity(), "Kontrakt qosildi!", Toast.LENGTH_SHORT).show();
                                startActivity(getActivity().getIntent());
                            }

                            @Override
                            public void onFailure(Call<ApiResponseContract> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });


        return view;
    }
}
