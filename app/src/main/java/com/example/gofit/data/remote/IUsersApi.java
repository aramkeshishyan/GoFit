package com.example.gofit.data.remote;

import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.tokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
public interface IUsersApi {

    @POST("/User/register")
    Call<defaultResponse> createUser(@Body User user);

    @POST("/User/login")
    Call<tokenResponse> loginUser(@Body User user);

}
