package com.example.gofit.data.remote;

import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.responses.UserRegister;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiManager {

    private static IUsersApi service;
    private static ApiManager apiManager;

    private ApiManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5254/")
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

    public void createUser(User user, Callback<UserRegister> callback){
        Call<UserRegister> userCall = service.createUser(user);
        userCall.enqueue(callback);
    }

}
