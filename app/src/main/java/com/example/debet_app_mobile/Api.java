package com.example.debet_app_mobile;

import com.example.debet_app_mobile.payload.ApiResponseContractResp;
import com.example.debet_app_mobile.payload.BuyDto;
import com.example.debet_app_mobile.payload.CalcDto;
import com.example.debet_app_mobile.payload.CalcRes;
import com.example.debet_app_mobile.payload.ClientDto;
import com.example.debet_app_mobile.payload.ClientDtos;
import com.example.debet_app_mobile.payload.DebetDto;
import com.example.debet_app_mobile.payload.RoleDto;
import com.example.debet_app_mobile.payload.UserDto;
import com.example.debet_app_mobile.payload.UserDtos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

//    String BASE_URL = "https://jsonplaceholder.typicode.com/";
//    String BASE_URL = "https://debet-app.herokuapp.com/";

//    String BASE_URL = "http://176.96.241.113:8088/";

    String BASE_URL = "https://b671-84-54-71-186.ngrok-free.app/public/api/";

//    String BASE_URL = "https://nukuslab.uz/debet/";

    @POST("auth/login")
    Call<ApiResponse> login(@Body LoginDto loginDto);

//    @POST("auth/login")
//    Call<ApiResponse2> login(@Body LoginDto loginDto);

//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("/user")
//    Call<List<Resp>> getNames(@Header("Authorization") String token);


//    @GET("test")
//    Call<String> getText();

    @POST("contracts/calc")
    Call<ApiResponseCalc> calc(@Body CalcDto calcDto, @Header("Authorization") String token);
//    Call<CalcRes> calc(@Body CalcDto calcDto, @Header("Authorization") String token);

    @POST("/contracts/addContract")
    Call<ApiResponseContract> addContract(@Header("Authorization") String token, @Body BuyDto buyDto);



    @POST("/contracts/addContractOld")
    Call<ApiResponseContract> addContractOld(@Header("Authorization") String token,
                                             @Body BuyDto buyDto,
                                             @Query("olDate") String olDate,
                                             @Query("payedPart") Integer payedPart);

    @GET("contracts/getMyContract")
    Call<ApiResponseContractResp> getMyContract(@Header("Authorization") String token);

    @POST("contracts/getContractByCompanyId")
    Call<ApiResponseContractResp> getContractByCompanyId(@Header("Authorization") String token, @Query("id") Long id);

    @POST("companies")
    Call<CompanyDto> getCompany(@Header("Authorization") String token);

    @POST("companies")
    Call<Void> addCompany(@Header("Authorization") String token, @Body CompDto compDto);

//    @PUT("companies/{id}")
//    Call<Void> updateCompany(@Header("Authorization") String token, @Body CompDto compDto, @Path("id") Long id);
//
    @PUT("companies/{id}")
    Call<Void> updateCompany(@Header("Authorization") String token, @Body CompDto compDto, @Path("id") int id);

//    @PUT("users/{id}")
//    Call<Void> updateUser(@Header("Authorization") String token,@Body UserDtos userDtos, @Path("id") Long id);

    @PUT("users/{id}")
    Call<Void> updateUser(@Header("Authorization") String token,@Body UserDtos userDtos, @Path("id") int id);

//    @PUT("users/blockAllUser/{id}")
//    Call<ApiResponse> blockAllUser(@Header("Authorization") String token, @Body UserDtos userDtos, @Path("id") Long id);

    @PUT("users/blockAllUser/{id}")
    Call<ApiResponse> blockAllUser(@Header("Authorization") String token, @Body UserDtos userDtos, @Path("id") int id);

    @POST("/test")
    Call<String> gettest();

//    @POST("/client")
//    Call<ClientDto> addClient(@Header("Authorization") String token, @Body ClientDto clientDto);

//    @POST("/client/addClient")
//    Call<ClientDtos> addClient(@Header("Authorization") String token, @Body ClientDto clientDto);


//    @POST("/client/addClient")
    @POST("/clients/add")
    Call<ApiRes> addClient(@Header("Authorization") String token, @Body ClientDto clientDto);

//    @POST("company/getAllCompany")
    @POST("companies/getAllCompany")
//    Call<ApiResponseCompany> getAllCompany(@Header("Authorization") String token);
    Call<Company> getAllCompany(@Header("Authorization") String token);

    @GET("companies/getAllCompany")
//    Call<ApiResponseCompany> getAllCompany(@Header("Authorization") String token);
    Call<Company2> getAllCompany2(@Header("Authorization") String token);

//    @GET("companies/getAllCompany")
//    Call<Company> hammesi(@Header("Authorization") String token);

//    @GET("roles/getAllRole")
    @GET("roles")
    Call<RoleDto> getAllRole(@Header("Authorization") String token);

//    @POST("user/addUser")
//    Call<Void> addUser(@Header("Authorization") String token, @Body UserDto userDto);

    @POST("users")
    Call<ApiResponseUser> addUser(@Header("Authorization") String token, @Body UserDto userDto);

    @GET("users/getUser")
    Call<ApiResponseUserAdd> getUser(@Header("Authorization") String token);

//    @POST("users/getUserByUsername")
    @POST("users/findByUsername")
    Call<ApiResponseUserAdd> getUserByUsername(@Header("Authorization") String token, @Query("username") String username);

//    @GET("users")
    @GET("users/getAllUsers")
    Call<ApiResponseUser> getAllUser(@Header("Authorization") String token);

    @POST("user")
    Call<ApiResponseUser> addUsr(@Header("Authorization") String token, @Body UserDto userDto);

//    @GET("/clients/getAllClient")
    @GET("/clients")
    Call<ApiResponseClient> getAllClient(@Header("Authorization") String token);

//    @POST("client/getAllMyClient")
//    Call<ApiResponseClient> getAllMyClient(@Header("Authorization") String token);

    @GET("clients/getAllMyClient")
    Call<ApiResponseClient2> getMyClients(@Header("Authorization") String token);

    @GET("contracts")
    Call<ApiResponseContractResp> getAllContract(@Header("Authorization") String token);

//    @POST("contracts/getAllContractByUserId")
//    Call<ApiResponseContractResp> getAllContractByUserId(@Header("Authorization") String token, @Query("id") Long id);
//
    @POST("contracts/getAllContractByUserId")
    Call<ApiResponseContractResp> getAllContractByUserId(@Header("Authorization") String token, @Query("id") int id);

    @GET("users/getAllByCompanyId")
    Call<ApiResponseUser> getAllUserByCompanyId(@Header("Authorization") String token, @Query("id") int id);

//    @GET("/clients/getClientsByUserId")
//    Call<ApiResponseClient2> getClientsByUserId(@Header("Authorization") String token, @Query("id") Long id);

    @GET("/clients/getClientsByUserId")
    Call<ApiResponseClient2> getClientsByUserId(@Header("Authorization") String token, @Query("id") int id);

    @GET("users/getAllByMyCompany")
    Call<ApiResponseUser> getAllByMyCompany(@Header("Authorization") String token);

    @POST("contracts/byNumber")
    Call<ApiResponseContractResp> getContractByClientNumber(@Header("Authorization") String token, @Query("number") String number);

    @GET("contracts/getAllContractByNoPayed")
    Call<ApiResponseContractResp> getAllContractByNoPayed(@Header("Authorization") String token);

    @GET("contracts/getAllContractByPayed")
    Call<ApiResponseContractResp> getAllContractByPayed(@Header("Authorization") String token);

    @GET("contracts/getAllContractByClientAndUser")
    Call<ApiResponseContractResp> getAllContractByClientAndUser(@Header("Authorization") String token);

//    @POST("/contract/getById")
    @GET("/contracts/{id}")
//    Call<ApiResponseContract2> getContractById(@Header("Authorization") String token, @Query("id") Long id);
    Call<ApiResponseContract2> getContractById(@Header("Authorization") String token, @Path("id") Long id);

    @POST("debets/getDebetByContractIdNoPayed")
    Call<ApiResponseDebet> getDebetByContractIdNoPayed(@Header("Authorization") String token,@Query("contractId") Long contractId);

    @POST("debets/getDebetByContractIdPayed")
    Call<ApiResponseDebet> getDebetByContractIdPayed(@Header("Authorization") String token,@Query("contractId") Long contractId);

//    getDebetByContractIdPayed


    @PUT("debets/{id}")
    Call<Void> updateDebet(@Header("Authorization") String token, @Body DebetDto debetDto, @Path("id") Long id);

    @PUT("debets/updateDebet")
    Call<ApiResponseDebets> editDebet(@Header("Authorization") String token, @Query("id") int id, @Query("pay") boolean pay);

    @GET("contracts/getMyAllContractToNow")
    Call<ApiResponseContractResp> getMyAllContractToNow(@Header("Authorization") String token);


    @GET("debets/getMyAllDebetToNow")
    Call<ApiResponseDebet> getMyAllDebetToNow(@Header("Authorization") String token);

    @POST("contracts/getMyAllContractBeetwen")
    Call<ApiResponseContractResp> getMyAllContractBeetwen(@Header("Authorization") String token, @Query("start") String start, @Query("end") String end);

    @POST("debets/getMyAllDebetBeetwen")
    Call<ApiResponseDebet> getMyAllDebetBeetwen(@Header("Authorization") String token, @Query("start") String start, @Query("end") String end);

    @GET("debets/getJournal")
    Call<ApiResponseDebet> getJournal(@Header("Authorization") String token);


}
