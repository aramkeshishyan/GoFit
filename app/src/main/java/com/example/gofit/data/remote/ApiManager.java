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
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiManager {

    private static IUsersApi service;
    private static ApiManager apiManager;



    private ApiManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5254/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(IUsersApi.class);

    }

    public static ApiManager getInstance() {

        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void createUser(User user, Callback<defaultResponse<Integer>> callback){
        Call<defaultResponse<Integer>> userCall = service.createUser(user);
        userCall.enqueue(callback);
    }

    public void loginUser(User user, Callback<tokenResponse> callback){
        Call<tokenResponse> userLog = service.loginUser(user);
        userLog.enqueue(callback);

    }

    public void getUserInfo(String token, Callback<defaultResponse<UserInfo>> callback){
        Call<defaultResponse<UserInfo>> userInfo = service.getUserInfo("Bearer " + token);
        userInfo.enqueue(callback);

    }

    public void postUserInfo(String token, UpdateSurvey update, Callback<defaultResponse<UserInfo>> callback){
        Call<defaultResponse<UserInfo>> userInfo = service.postUserInfo("Bearer " + token, update);
        userInfo.enqueue(callback);
    }


    public void getFriends(String token, Callback<defaultResponseList<Friend>> callback){
        Call <defaultResponseList<Friend>> userFriends = service.getFriends("Bearer " + token);
        userFriends.enqueue(callback);

    }

    public void addFriend(String token, UserFriended friended, Callback<defaultResponse<Boolean>> callback){
        Call<defaultResponse<Boolean>> addedFriend = service.addFriend("Bearer " + token, friended);
        addedFriend.enqueue(callback);
    }

    public void getFriendRequests(String token, Callback<defaultResponseList<RequestersInfo>> callback){
        Call<defaultResponseList<RequestersInfo>> requesterInfo = service.getFriendRequests("Bearer " + token);
        requesterInfo.enqueue(callback);
    }



}
