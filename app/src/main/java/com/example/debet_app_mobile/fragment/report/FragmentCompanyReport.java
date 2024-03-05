package com.example.debet_app_mobile.fragment.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.debet_app_mobile.Api;
import com.example.debet_app_mobile.ApiResponse;
import com.example.debet_app_mobile.ApiSms;
import com.example.debet_app_mobile.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class FragmentCompanyReport extends Fragment {

    private View view;

    public FragmentCompanyReport() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.company_report_fragment,container, false);

        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUsInJvbGUiOiJ1c2VyIiwiZGF0YSI6eyJpZCI6NSwibmFtZSI6Ilx1MDQyN1x1MDQxZiBCZXN0IEludGVybmV0IFNvbHV0aW9uIiwiZW1haWwiOiJ0ZXN0QGVza2l6LnV6Iiwicm9sZSI6InVzZXIiLCJhcGlfdG9rZW4iOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpJVXpJMU5pSjkuZXlKemRXSWlPalVzSW5KdmJHVWlPaUoxYzJWeUlpd2laR0YwWVNJNmV5SnBaQ0k2TlN3aWJtRnRaU0k2SWx4MU1EUXlOMXgxTURReFppQkNaWE4wSUVsdWRHVnlibVYwSUZOdmJIVjBhVzl1SWl3aVpXMWhhV3dpT2lKMFpYTjBRR1Z6YTJsNkxuVjZJaXdpY205c1pTSTZJblZ6WlhJaUxDSmhjR2xmZEc5clpXNGlPaUpsZVVvd1pWaEJhVTlwU2t0V01WRnBURU5LYUdKSFkybFBhVXBKVlgiLCJzdGF0dXMiOiJhY3RpdmUiLCJzbXNfYXBpX2xvZ2luIjoiZXNraXoyIiwic21zX2FwaV9wYXNzd29yZCI6ImUkJGsheiIsInV6X3ByaWNlIjo1MCwidWNlbGxfcHJpY2UiOjExNSwiYmFsYW5jZSI6MzU1MCwiaXNfdmlwIjowLCJob3N0Ijoic2VydmVyMSIsImNyZWF0ZWRfYXQiOm51bGwsInVwZGF0ZWRfYXQiOiIyMDIyLTEwLTA3VDE1OjQ0OjI3LjAwMDAwMFoifSwiaWF0IjoxNjY1MTU3NDY5LCJleHAiOjE2Njc3NDk0Njl9.oM46YOHkzhUQX5LcWcmBvs8Er7jYtUXtE_XniSJB8-Y";


        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("mobile_phone", "998974748061")
                .addFormDataPart("message", "TEST")
                .addFormDataPart("from", "4546")
                .addFormDataPart("callback_url", "http://0000.uz/test.php")
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiSms.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApiSms apiSms = retrofit.create(ApiSms.class);

        apiSms.send(token, body).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Toast.makeText(getActivity(), response.code(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}
