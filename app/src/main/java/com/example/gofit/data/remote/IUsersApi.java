package com.example.gofit.data.remote;

import com.example.gofit.Friend;
import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.requests.UpdateSurvey;
import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.requests.UserFriended;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.data.model.responses.tokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
public interface IUsersApi {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/register")
    Call<defaultResponse<Integer>> createUser(@Body User user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/login")
    Call<tokenResponse> loginUser(@Body User user);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/updateSurvey")
    Call<defaultResponse<UserInfo>> postUserInfo(@Header("Authorization") String token, @Body UpdateSurvey update);
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/info")
    Call<defaultResponse<UserInfo>> getUserInfo(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/friends")
    Call<defaultResponseList<Friend>> getFriends(@Header("Authorization") String token);

    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    //@FormUrlEncoded
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/addFriend")
    Call<defaultResponse<Boolean>> addFriend(@Header("Authorization") String token, @Body UserFriended friended);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/friendRequests")
    Call<defaultResponseList<RequestersInfo>> getFriendRequests(@Header("Authorization") String token);


}
