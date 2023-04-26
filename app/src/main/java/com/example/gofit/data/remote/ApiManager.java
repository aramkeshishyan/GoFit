package com.example.gofit.data.remote;

import com.example.gofit.Exercise_Item;
import com.example.gofit.Friend;
import com.example.gofit.Nutrition_Item;
import com.example.gofit.data.model.requests.Challenges.ChallengeRecordDto;
import com.example.gofit.data.model.requests.Challenges.ChallengeRequestDto;
import com.example.gofit.data.model.requests.Challenges.CreateChallengeDto;
import com.example.gofit.data.model.requests.Challenges.ChallengeDto;
import com.example.gofit.data.model.requests.Challenges.SendChallengeDto;
import com.example.gofit.data.model.requests.Challenges.UpdateChallengeDto;
import com.example.gofit.data.model.requests.EmailDto;
import com.example.gofit.data.model.requests.ExerciseOrMealType;
import com.example.gofit.data.model.requests.ObjectId;
import com.example.gofit.data.model.requests.RequestersInfo;
import com.example.gofit.data.model.requests.Steps;
import com.example.gofit.data.model.requests.UpdateSurvey;
import com.example.gofit.data.model.requests.UpdateUserDto;
import com.example.gofit.data.model.requests.User;
import com.example.gofit.data.model.requests.UserAcceptedDenied;
import com.example.gofit.data.model.requests.UserFriendedDeleted;
import com.example.gofit.data.model.requests.UserInfo;
import com.example.gofit.data.model.requests.UserStats;
import com.example.gofit.data.model.requests.UserUpdatePasswordDto;
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

    public void updateUserSurvey(String token, UpdateSurvey update, Callback<defaultResponse<UserInfo>> callback){
        Call<defaultResponse<UserInfo>> userInfo = service.updateUserSurvey("Bearer " + token, update);
        userInfo.enqueue(callback);
    }

    public void updateUserInfo(String token, UpdateUserDto update, Callback<defaultResponse<UserInfo>> callback) {
        Call<defaultResponse<UserInfo>> userInfo = service.updateUserInfo("Bearer " + token, update);
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

    public void updatePassword(String token, UserUpdatePasswordDto updatePassword, Callback<defaultResponse<String>> callback){
        Call<defaultResponse<String>> updatePasswordStatus = service.updatePassword("Bearer " + token, updatePassword);
        updatePasswordStatus.enqueue(callback);
    }

    public void getMeals(Callback<defaultResponseList<Nutrition_Item>> callback){
        Call<defaultResponseList<Nutrition_Item>> mealList = service.getMeals();
        mealList.enqueue(callback);
    }

    public void getMealsByType(ExerciseOrMealType type, Callback<defaultResponseList<Nutrition_Item>> callback){
        Call<defaultResponseList<Nutrition_Item>> mealsList = service.getMealsByType(type);
        mealsList.enqueue(callback);
    }

    // ---------------- USER STATS ----------------
    public void createUserStats(String token, Callback<defaultResponse<String>> callback) {
        Call<defaultResponse<String>> emptyDataResponse = service.createUserStats("Bearer " + token);
        emptyDataResponse.enqueue(callback);
    }

    public void manualUserStatsChange(String token, UserStats statsToChange, Callback<defaultResponse<UserStats>> callback) {
        Call<defaultResponse<UserStats>> newUserStats = service.manualUserStatsChange("Bearer " + token, statsToChange);
        newUserStats.enqueue(callback);
    }

    public void updateSteps(String token, Steps steps, Callback<defaultResponse<String>> callback) {
        Call<defaultResponse<String>> emptyDataResponse = service.updateSteps("Bearer " + token, steps);
        emptyDataResponse.enqueue(callback);
    }

    public void resetStepsAndDist(String token, Callback<defaultResponse<String>> callback) {
        Call<defaultResponse<String>> emptyDataResponse = service.resetStepsAndDistTo0("Bearer " + token);
        emptyDataResponse.enqueue(callback);
    }

    public void updateChallengeStats(String token, Callback<defaultResponse<String>> callback) {
        Call<defaultResponse<String>> emptyDataResponse = service.updateChallengeStats("Bearer " + token);
        emptyDataResponse.enqueue(callback);
    }

    public void getUserStats(String token, Callback<defaultResponse<UserStats>> callback) {
        Call<defaultResponse<UserStats>> userStats = service.getUserStats("Bearer " + token);
        userStats.enqueue(callback);
    }

    public void getFriendStats(String token, EmailDto friendEmail, Callback<defaultResponse<UserStats>> callback) {
        Call<defaultResponse<UserStats>> friendStats = service.getFriendStats("Bearer " + token, friendEmail);
        friendStats.enqueue(callback);
    }




    ///////////////////////CHALLENGES//////////////////

    public void createChallenge(String token, CreateChallengeDto createChallenge, Callback<defaultResponse<ChallengeRecordDto>> callback){
        Call<defaultResponse<ChallengeRecordDto>> challengeRecords = service.createChallenge("Bearer " + token, createChallenge);
        challengeRecords.enqueue(callback);
    }

    public void getChallengeRecords(String token, Callback<defaultResponseList<ChallengeRecordDto>> callback){
        Call<defaultResponseList<ChallengeRecordDto>> challengeRecords = service.getChallengeRecords("Bearer " + token);
        challengeRecords.enqueue(callback);
    }

    public void getChallenges(String token, Callback<defaultResponseList<ChallengeDto>> callback) {
        Call<defaultResponseList<ChallengeDto>> challenges_list = service.getAllUserChallenges("Bearer " + token);
        challenges_list.enqueue(callback);
    }

    public void getChallengeInfoById(String token, ObjectId challengeId, Callback<defaultResponse<ChallengeDto>> callback) {
        Call<defaultResponse<ChallengeDto>> challenge = service.getChallengeInfoById("Bearer " + token, challengeId);
        challenge.enqueue(callback);
    }
    public void sendChallengeRequest(String token, SendChallengeDto sendRequest, Callback<defaultResponse<String>> callback) {
        Call<defaultResponse<String>> emptyDataResponse = service.sendChallengeRequest("Bearer " + token, sendRequest);
        emptyDataResponse.enqueue(callback);
    }

    public void acceptChallengeRequest(String token, ObjectId requestId, Callback<defaultResponse<ChallengeRequestDto>> callback) {
        Call<defaultResponse<ChallengeRequestDto>> challengeRequestInfo = service.acceptChallengeRequest("Bearer " + token, requestId);
        challengeRequestInfo.enqueue(callback);
    }

    public void denyChallengeRequest(String token, ObjectId requestId, Callback<defaultResponse<String>> callback) {
        Call<defaultResponse<String>> emptyDataResponse = service.denyChallengeRequest("Bearer " + token, requestId);
        emptyDataResponse.enqueue(callback);
    }

    public void getUserChallengeRequests(String token, Callback<defaultResponseList<ChallengeRequestDto>> callback) {
        Call<defaultResponseList<ChallengeRequestDto>> challengeRequests = service.getUserChallengeRequests("Bearer " + token);
        challengeRequests.enqueue(callback);
    }

    public void completeChallenge(String token, ObjectId challengeId, Callback<defaultResponse<ChallengeRecordDto>> callback) {
        Call<defaultResponse<ChallengeRecordDto>>  updatedChallengeRecord = service.completeChallenge("Bearer " + token, challengeId);
        updatedChallengeRecord.enqueue(callback);
    }

    public void updateChallenge(String token, UpdateChallengeDto updatedInfo, Callback<defaultResponse<ChallengeDto>> callback) {
        Call<defaultResponse<ChallengeDto>> updatedChallenge = service.updateChallenge("Bearer " + token, updatedInfo);
        updatedChallenge.enqueue(callback);
    }

    //////////////////CHALLENGES //////////////////////////////////////////////



}
