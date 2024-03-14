package com.example.myapplication.repository.api;

import com.example.myapplication.repository.api.models.CanselRequestt;
import com.example.myapplication.repository.api.models.GetNumberRequest;
import com.example.myapplication.repository.api.models.LoginRequest;
import com.example.myapplication.repository.api.models.RegistrRequest;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyApiService {
    @POST("client/login")
    Call<ResponseBody> loginUser(@Body LoginRequest loginRequest);

    @GET("client/profile")
    Call<ResponseBody> isligin();
    @POST("client/register")
    Call<ResponseBody> registrUser(@Body RegistrRequest registrRequest);
    @GET("client/organizations")
    Call<ResponseBody> getListHotel();
    @GET("client/organizations/{id}/rooms")
    Call<ResponseBody> getNamberByHotel(@Path("id") String id);
    @POST("client/check-in")
    Call<ResponseBody> getNumber(@Body GetNumberRequest getNumberRequest);
    @GET("client/services")
    Call<ResponseBody> getServices();
    @GET("client/profile")
    Call<ResponseBody> getProfilExequte();
    @GET("client/check-out")
    Call<ResponseBody> getExit();
    @GET("client/orders")
    Call<ResponseBody> getOrders();
    @POST("client/orders/{id}/cancel")
    Call<ResponseBody> canselORder(@Path("id")String orderId,@Body CanselRequestt canselRequest);



}
