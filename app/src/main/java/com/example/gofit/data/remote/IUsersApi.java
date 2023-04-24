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
import com.example.gofit.data.model.responses.defaultResponse;
import com.example.gofit.data.model.responses.defaultResponseList;
import com.example.gofit.data.model.responses.tokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/addFriend")
    Call<defaultResponse<Boolean>> addFriend(@Header("Authorization") String token, @Body UserFriendedDeleted friended);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/acceptFriendRequest")
    Call<defaultResponseList<Friend>> acceptFriend(@Header("Authorization") String token, @Body UserAcceptedDenied accepted);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/denyFriendRequest")
    Call<defaultResponse<String>> denyFriend(@Header("Authorization") String token, @Body UserAcceptedDenied denied);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("/User/friend")
    Call<defaultResponse<String>> deleteFriend(@Header("Authorization") String token, @Body UserFriendedDeleted friendToDelete);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/friendRequests")
    Call<defaultResponseList<RequestersInfo>> getFriendRequests(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Exercise/all")
    Call<defaultResponseList<Exercise_Item>> getExercises();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Exercise/byType")
    Call<defaultResponseList<Exercise_Item>> getExercisesByType(@Body ExerciseOrMealType type);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Meal/all")
    Call<defaultResponseList<Nutrition_Item>> getMeals();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Meal/byType")
    Call<defaultResponseList<Nutrition_Item>> getMealsByType(@Body ExerciseOrMealType type);


    ////////////////////////////////////CHALLENGES//////////////////////////////////

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/createChallenge")
    Call<defaultResponse<ChallengeRecordDto>> createChallenge(@Header("Authorization") String token, @Body CreateChallengeDto createChallenge);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Challenge/records")
    Call<defaultResponse<ChallengeRecordDto>> getChallengeRecords(@Header("Authorization") String token);










}
