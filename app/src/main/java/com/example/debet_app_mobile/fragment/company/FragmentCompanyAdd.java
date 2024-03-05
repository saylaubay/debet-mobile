package com.example.debet_app_mobile.fragment.company;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.CompDto;
import com.example.debet_app_mobile.CompanyActivity;
import com.example.debet_app_mobile.R;
import com.example.debet_app_mobile.ReportActivity;
import com.example.debet_app_mobile.TokenProvider;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentCompanyAdd extends Fragment {

    private View view;
    Button addBtn;
    EditText etCompany;
    TextView tema;

    public FragmentCompanyAdd() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.company_add_fragment, container, false);
        addBtn = view.findViewById(R.id.addBtn);
        etCompany = view.findViewById(R.id.etCompany);
//        tema = view.findViewById(R.id.tema);

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

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cName = etCompany.getText().toString();
                CompDto compDto = new CompDto(cName);

                String token = preferences.getString("token","");
                token = "Bearer " + token;
                api.addCompany(token, compDto).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getActivity(), "Kompaniya Qosildi", Toast.LENGTH_SHORT).show();
                        etCompany.setText("");
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


}
