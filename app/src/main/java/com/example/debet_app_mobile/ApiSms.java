package com.example.debet_app_mobile;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiSms {

    String BASE_URL = "https://notify.eskiz.uz/api/message/sms/";

    @POST("send")
    Call<ApiResponse> send(@Header("Authorization") String token, @Body RequestBody body);



}
