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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface IUsersApi {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/register")
    Call<defaultResponse<Integer>> createUser(@Body User user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/User/login")
    Call<tokenResponse> loginUser(@Body User user);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/User/updatePassword")
    Call<defaultResponse<String>> updatePassword(@Header("Authorization") String token, @Body UserUpdatePasswordDto updatePassword);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/User/updateSurvey")
    Call<defaultResponse<UserInfo>> updateUserSurvey(@Header("Authorization") String token, @Body UpdateSurvey update);

    //Updates name, email, and photo
    //Any blanks should be filled with current user data
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/User/updateUser")
    Call<defaultResponse<UserInfo>> updateUserInfo(@Header("Authorization") String token, @Body UpdateUserDto update);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/info")
    Call<defaultResponse<UserInfo>> getUserInfo(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/friends")
    Call<defaultResponseList<Friend>> getFriends(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/User/friendRequests")
    Call<defaultResponseList<RequestersInfo>> getFriendRequests(@Header("Authorization") String token);

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

    @HTTP(method = "DELETE", path = "/User/friend", hasBody = true)
    Call<defaultResponse<String>> deleteFriend(@Header("Authorization") String token, @Body UserFriendedDeleted friendToDelete);


    // ---------------- EXERCISES AND MEALS ----------------
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


    // ---------------- USER STATS ----------------
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/UserStats/create/")
    Call<defaultResponse<String>> createUserStats(@Header("Authorization") String token);

    //For testing purposes this allows you to manually change all stats of the user
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/UserStats/manualStatUpdate/")
    Call<defaultResponse<UserStats>> manualUserStatsChange(@Header("Authorization") String token, @Body UserStats statsToChange);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/UserStats/updateSteps/")
    Call<defaultResponse<String>> updateSteps(@Header("Authorization") String token, @Body Steps stepsToAdd);   //Steps will be added to total

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/UserStats/resetSteps/")
    Call<defaultResponse<String>> resetStepsAndDistTo0(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/UserStats/updateChallengeStats/")
    Call<defaultResponse<String>> updateChallengeStats(@Header("Authorization") String token);  //Ensures challenge count/points are up to date.

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/UserStats/")
    Call<defaultResponse<UserStats>> getUserStats(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/UserStats/Friend/")
    Call<defaultResponse<UserStats>> getFriendStats(@Header("Authorization") String token, @Body EmailDto friendEmail);



    ////////////////////////////////////CHALLENGES//////////////////////////////////
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/createChallenge")
    Call<defaultResponse<ChallengeRecordDto>> createChallenge(@Header("Authorization") String token, @Body CreateChallengeDto createChallenge);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Challenge/records")
    Call<defaultResponseList<ChallengeRecordDto>> getChallengeRecords(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Challenge/allUserChallenges")
    Call<defaultResponseList<ChallengeDto>> getAllUserChallenges(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/challengeById/")
    Call<defaultResponse<ChallengeDto>> getChallengeInfoById(@Header("Authorization") String token, @Body ObjectId challengeId);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/sendRequest")
    Call<defaultResponse<String>> sendChallengeRequest(@Header("Authorization") String token, @Body SendChallengeDto sendRequest);

    //This returns a challengeRequestDto but you can ignore this data.
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/acceptRequest/")
    Call<defaultResponse<ChallengeRecordDto>> acceptChallengeRequest(@Header("Authorization") String token, @Body ObjectId requestId);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/denyRequest/")
    Call<defaultResponse<String>> denyChallengeRequest(@Header("Authorization") String token, @Body ObjectId requestId);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("/Challenge/requests/")
    Call<defaultResponseList<ChallengeRequestDto>> getUserChallengeRequests(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("/Challenge/completeChallenge/")
    Call<defaultResponse<ChallengeRecordDto>> completeChallenge(@Header("Authorization") String token, @Body ObjectId challengeId);

    //Users can only update challenges that they created!
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("/Challenge/")
    Call<defaultResponse<ChallengeDto>> updateChallenge(@Header("Authorization") String token, @Body UpdateChallengeDto updatedInfo);

    //There is a delete challenge function for testing purposes only
    //Not for users to delete their challenges

    ////////////////////////////////////CHALLENGES//////////////////////////////////




}
