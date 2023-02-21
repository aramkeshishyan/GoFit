package com.example.gofit.data.remote;

import com.example.gofit.data.model.requests.Friends;
import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.data.model.responses.tokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface IUsersApi {


    @POST("/User/register")
    Call<defaultResponse<Integer>> createUser(@Body User user);

    @POST("/User/login")
    Call<tokenResponse> loginUser(@Body User user);

    @GET("/User/userInfo")
    Call<defaultResponse<UserInfo>> getUserInfo(@Header("Authorization") String token);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/friends")
    Call<defaultResponseList<Friends>> getFriends(@Header("Authorization") String token);

}
