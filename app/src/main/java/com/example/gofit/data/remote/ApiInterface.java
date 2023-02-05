package com.example.gofit.data.remote;

import com.example.gofit.data.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/User/register")
    Call<User> getUserInformation(@Field("fullName") String fullName, @Field("email") String email, @Field("password") String password);

}
