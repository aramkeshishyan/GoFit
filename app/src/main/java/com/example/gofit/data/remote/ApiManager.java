package com.example.gofit.data.remote;

import com.example.gofit.Exercise_Item;
import com.example.gofit.Friend;
import com.example.gofit.Nutrition_Item;
import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.Challenges.CreateChallengeDto;
import com.example.gofit.data.model.requests.ExerciseOrMealType;
import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.requests.UpdateSurvey;
import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.requests.UserAcceptedDenied;
import com.example.gofit.data.model.requests.UserFriendedDeleted;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.requests.UserStats;
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
                //.baseUrl("http://10.0.2.2:5254/")
                .baseUrl("http://137.184.190.228/")
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

    public void acceptFriend(String token, UserAcceptedDenied accepted, Callback<defaultResponseList<Friend>> callback){
        Call<defaultResponseList<Friend>> userFriends = service.acceptFriend("Bearer " + token, accepted);
        userFriends.enqueue(callback);
    }

    public void denyFriend(String token, UserAcceptedDenied denied, Callback<defaultResponse<String>> callback){
        Call<defaultResponse<String>> denyStatus = service.denyFriend("Bearer " + token, denied);
        denyStatus.enqueue(callback);
    }

    public void deleteFriend(String token, UserFriendedDeleted friendToDelete, Callback<defaultResponse<String>> callback){
        Call<defaultResponse<String>> deleteStatus = service.deleteFriend("Bearer " + token, friendToDelete);
        deleteStatus.enqueue(callback);
    }

    public void addFriend(String token, UserFriendedDeleted friended, Callback<defaultResponse<Boolean>> callback){
        Call<defaultResponse<Boolean>> addedFriend = service.addFriend("Bearer " + token, friended);
        addedFriend.enqueue(callback);
    }

    public void getFriendRequests(String token, Callback<defaultResponseList<RequestersInfo>> callback){
        Call<defaultResponseList<RequestersInfo>> requesterInfo = service.getFriendRequests("Bearer " + token);
        requesterInfo.enqueue(callback);
    }

    public void getExercises(Callback<defaultResponseList<Exercise_Item>> callback){
        Call<defaultResponseList<Exercise_Item>> exercisesList = service.getExercises();
        exercisesList.enqueue(callback);
    }

    public void getExercisesByType(ExerciseOrMealType type, Callback<defaultResponseList<Exercise_Item>> callback){
        Call<defaultResponseList<Exercise_Item>> exercisesList = service.getExercisesByType(type);
        exercisesList.enqueue(callback);
    }

    public void getMeals(Callback<defaultResponseList<Nutrition_Item>> callback){
        Call<defaultResponseList<Nutrition_Item>> mealList = service.getMeals();
        mealList.enqueue(callback);
    }

    public void getMealsByType(ExerciseOrMealType type, Callback<defaultResponseList<Nutrition_Item>> callback){
        Call<defaultResponseList<Nutrition_Item>> mealsList = service.getMealsByType(type);
        mealsList.enqueue(callback);
    }

    //User Stats
    public void getUserStats(String token, Callback<defaultResponse<UserStats>> callback) {
        Call<defaultResponse<UserStats>> userStats = service.getUserStats("Bearer " + token);
        userStats.enqueue(callback);
    }

    ///////////////////////CHALLENGES//////////////////

    public void createChallenge(String token, CreateChallengeDto createChallenge, Callback<defaultResponse<ChallengeRecordDto>> callback){
        Call<defaultResponse<ChallengeRecordDto>> challengeRecords = service.createChallenge("Bearer " + token, createChallenge);
        challengeRecords.enqueue(callback);
    }

    public void getChallengeRecords(String token, Callback<defaultResponse<ChallengeRecordDto>> callback){
        Call<defaultResponse<ChallengeRecordDto>> challengeRecords = service.getChallengeRecords("Bearer " + token);
        challengeRecords.enqueue(callback);
    }



}
