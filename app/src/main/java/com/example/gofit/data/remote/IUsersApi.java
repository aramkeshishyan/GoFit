package com.example.gofit.data.remote;

import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.responses.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface IUsersApi {

    @POST("/User/register")
    Call<UserRegister> createUser(@Body User user);

}
