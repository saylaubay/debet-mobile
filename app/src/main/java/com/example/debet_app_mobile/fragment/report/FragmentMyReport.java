package com.example.debet_app_mobile.fragment.report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiResponseDebet;
import com.example.debet_app_mobile.CalcActivity;
import com.example.debet_app_mobile.CompanyDto;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.payload.ApiResponseContractResp;
import com.example.debet_app_mobile.payload.ContractDto;
import com.example.debet_app_mobile.payload.DebetDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentMyReport extends Fragment {

    public String startContract = "", endContract = "";
    public String startDebet = "", endDebet = "";

    private View view;
    public Button myBtn;
    public TextView danContract, deyinContract,rep_contr_count,rep_contr_summa, danDebet,deyinDebet,rep_deb_count,rep_deb_summa;
//    ApiService apiService;
    SharedPreferences sharedPreferences;

    public FragmentMyReport() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_report_fragment, container, false);
        danContract = view.findViewById(R.id.danContract);
        deyinContract = view.findViewById(R.id.deyinContract);
        rep_contr_count = view.findViewById(R.id.rep_contr_count);
        rep_contr_summa = view.findViewById(R.id.rep_contr_summa);
        danDebet = view.findViewById(R.id.danDebet);
        deyinDebet = view.findViewById(R.id.deyinDebet);
        rep_deb_count = view.findViewById(R.id.rep_deb_count);
        rep_deb_summa = view.findViewById(R.id.rep_deb_summa);


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

        if (danContract.getText().toString().equals("dan") ||
            deyinContract.getText().toString().equals("deyin")){

            api.getMyAllContractToNow(token).enqueue(new Callback<ApiResponseContractResp>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {
                    List<ContractDto> list = response.body().getData();
//                    long count = list.stream().count();
                    int count = 0;
                    double s = 0;
                    for (ContractDto contractDto : list) {
                        count++;
                        s=s+contractDto.getPrice();
                    }
                    String summa = String.valueOf(s);
                    String c = String.valueOf(count);

                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    String get_summa = formatter.format(s);
//                    String get_c = formatter.format(count);

                    rep_contr_count.setText(c);
                    rep_contr_summa.setText(get_summa);
                }

                @Override
                public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        if (danDebet.getText().toString().equals("dan") ||
        deyinDebet.getText().toString().equals("deyin")){

            api.getMyAllDebetToNow(token).enqueue(new Callback<ApiResponseDebet>() {
                @Override
                public void onResponse(Call<ApiResponseDebet> call, Response<ApiResponseDebet> response) {
                    List<DebetDto> list = response.body().getData();
                    int count = 0;
                    double s = 0;
                    for (DebetDto debetDto : list) {
                        count++;
                        s=s+debetDto.getSumma();
                    }
                    String summa = String.valueOf(s);
                    String c = String.valueOf(count);

                    DecimalFormat formatter = new DecimalFormat("#,###.00");
                    String get_summa = formatter.format(s);
//                    String get_c = formatter.format(count);

                    rep_deb_count.setText(c);
                    rep_deb_summa.setText(get_summa);
                }

                @Override
                public void onFailure(Call<ApiResponseDebet> call, Throwable t) {
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

//        alert = new AlertDialog.Builder(this);

        danContract.setOnClickListener(new View.OnClickListener() {
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
                        danContract.setText(i2+"/"+i1+"/"+i);
                        startContract = danContract.getText().toString();

                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });


        String finalToken = token;
        deyinContract.setOnClickListener(new View.OnClickListener() {
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
                        deyinContract.setText(i2+"/"+i1+"/"+i);
                        endContract = deyinContract.getText().toString();

                        api.getMyAllContractBeetwen(finalToken,startContract, endContract).enqueue(new Callback<ApiResponseContractResp>() {
                            @Override
                            public void onResponse(Call<ApiResponseContractResp> call, Response<ApiResponseContractResp> response) {
                                int count = 0;
                                double s = 0;
                                for (ContractDto datum : response.body().getData()) {
                                    count++;
                                    s = s + datum.getPrice();
                                }
                                String summa = String.valueOf(s);
                                String c = String.valueOf(count);

                                DecimalFormat formatter = new DecimalFormat("#,###.00");
                                String get_summa = formatter.format(s);
//                                String get_c = formatter.format(count);

                                rep_contr_count.setText(c);
                                rep_contr_summa.setText(get_summa);
                            }

                            @Override
                            public void onFailure(Call<ApiResponseContractResp> call, Throwable t) {
                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });



        danDebet.setOnClickListener(new View.OnClickListener() {
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
                        danDebet.setText(i2+"/"+i1+"/"+i);
                        startDebet = danDebet.getText().toString();

                    }
                },year,month,day);
                datePickerDialog.show();


            }
        });

        deyinDebet.setOnClickListener(new View.OnClickListener() {
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
                        deyinDebet.setText(i2+"/"+i1+"/"+i);
                        endDebet = deyinDebet.getText().toString();

                        api.getMyAllDebetBeetwen(finalToken,startDebet, endDebet).enqueue(new Callback<ApiResponseDebet>() {
                            @Override
                            public void onResponse(Call<ApiResponseDebet> call, Response<ApiResponseDebet> response) {
                                int count = 0;
                                double s = 0;
                                for (DebetDto datum : response.body().getData()) {
                                    count++;
                                    s = s + datum.getSumma();
                                }
                                String summa = String.valueOf(s);
                                String c = String.valueOf(count);

                                DecimalFormat formatter = new DecimalFormat("#,###.00");
                                String get_summa = formatter.format(s);
//                                String get_c = formatter.format(count);

                                rep_deb_count.setText(c);
                                rep_deb_summa.setText(get_summa);
                            }

                            @Override
                            public void onFailure(Call<ApiResponseDebet> call, Throwable t) {

                            }
                        });

                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });


        return view;
    }






}
